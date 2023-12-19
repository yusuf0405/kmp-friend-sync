package org.joseph.friendsync.search.impl

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import io.github.aakira.napier.Napier
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import org.joseph.friendsync.common.util.Result
import org.joseph.friendsync.common.util.coroutines.launchSafe
import org.joseph.friendsync.common.util.coroutines.onError
import org.joseph.friendsync.core.ui.common.communication.NavigationScreenStateFlowCommunication
import org.joseph.friendsync.core.ui.common.extensions.createMutableSharedFlowAsLiveData
import org.joseph.friendsync.core.ui.snackbar.FriendSyncSnackbar
import org.joseph.friendsync.core.ui.snackbar.SnackbarDisplay
import org.joseph.friendsync.core.ui.strings.MainResStrings
import org.joseph.friendsync.domain.usecases.categories.FetchAllCategoriesUseCase
import org.joseph.friendsync.domain.usecases.post.SearchPostsByQueryUseCase
import org.joseph.friendsync.domain.usecases.user.SearchUsersByQueryUseCase
import org.joseph.friendsync.post.api.PostScreenProvider
import org.joseph.friendsync.profile.api.ProfileScreenProvider
import org.joseph.friendsync.search.impl.category.CategoryType
import org.joseph.friendsync.search.impl.category.CategoryType.Companion.findCategoryTypeByValue
import org.joseph.friendsync.search.impl.category.CategoryTypeCommunication
import org.joseph.friendsync.search.impl.post.PostUiState
import org.joseph.friendsync.search.impl.post.PostUiStateCommunication
import org.joseph.friendsync.search.impl.user.UserUiStateCommunication
import org.joseph.friendsync.search.impl.user.UsersUiState
import org.joseph.friendsync.ui.components.mappers.CategoryDomainToCategoryMapper
import org.joseph.friendsync.ui.components.mappers.PostDomainToPostMapper
import org.joseph.friendsync.ui.components.mappers.UserInfoDomainToUserInfoMapper
import org.joseph.friendsync.ui.components.models.Category
import org.joseph.friendsync.ui.components.models.Post
import org.joseph.friendsync.ui.components.models.filterPhotoPosts
import org.joseph.friendsync.ui.components.models.filterTextPosts
import org.joseph.friendsync.ui.components.models.user.UserInfo
import org.koin.core.component.KoinComponent

private const val SEARCH_DEBOUNCE = 300L
private const val PAGE = 1
private const val PAGE_SIZE = 100

class SearchViewModel(
    private val searchPostsByQueryUseCase: SearchPostsByQueryUseCase,
    private val searchUsersByQueryUseCase: SearchUsersByQueryUseCase,
    private val fetchAllCategoriesUseCase: FetchAllCategoriesUseCase,
    private val categoryDomainToCategoryMapper: CategoryDomainToCategoryMapper,
    private val postDomainToPostMapper: PostDomainToPostMapper,
    private val userInfoMapper: UserInfoDomainToUserInfoMapper,
    private val postUiStateCommunication: PostUiStateCommunication,
    private val userUiStateCommunication: UserUiStateCommunication,
    private val categoryTypeCommunication: CategoryTypeCommunication,
    private val navigationScreenCommunication: NavigationScreenStateFlowCommunication,
    private val snackbarDisplay: SnackbarDisplay,
    private val postScreenProvider: PostScreenProvider,
    private val profileScreenProvider: ProfileScreenProvider,
) : StateScreenModel<SearchUiState>(SearchUiState.Initial), KoinComponent {

    val postUiStateFlow = postUiStateCommunication.observe()
    val userUiStateFlow = userUiStateCommunication.observe()
    val categoryTypeFlow = categoryTypeCommunication.observe()
    val navigationToFlow = navigationScreenCommunication.observe()

    private val postsFlow = createMutableSharedFlowAsLiveData<List<Post>>()
    private val usersFlow = createMutableSharedFlowAsLiveData<List<UserInfo>>()
    private val loadedUiStateFlow = mutableState.filterIsInstance<SearchUiState.Loaded>()

    private val searchQueryFlow = loadedUiStateFlow
        .map { it.searchQueryValue }
        .stateIn(screenModelScope, SharingStarted.Lazily, String())

    private val selectedCategoryFlow = loadedUiStateFlow
        .map { it.selectedCategory }
        .stateIn(screenModelScope, SharingStarted.Lazily, Category.unknown)

    private var allDataJob: Job? = null
    private var searchDataJob: Job? = null
    private val defaultErrorMessage = MainResStrings.default_error_message

    init {
        allDataJob = loadAllData()

        postsFlow.combine(categoryTypeFlow, ::Pair)
            .onEach(::handlePostsState)
            .onError(::handleError)
            .launchIn(screenModelScope)

        usersFlow.onEach(::handleUsersState)
            .onError(::handleError)
            .launchIn(screenModelScope)

        searchQueryFlow.debounce(SEARCH_DEBOUNCE)
            .onEach(::startSearchItems)
            .onError(::handleError)
            .launchIn(screenModelScope)

        selectedCategoryFlow
            .onEach(::handleSelectedCategory)
            .onError(::handleError)
            .launchIn(screenModelScope)
    }

    private fun handlePostsState(params: Pair<List<Post>, CategoryType>) {
        val (posts, selectedCategory) = params
        val filteredPosts = when (selectedCategory) {
            CategoryType.TEXT -> posts.filterTextPosts()
            CategoryType.PHOTOS -> posts.filterPhotoPosts()
            else -> posts
        }
        val state = if (filteredPosts.isEmpty()) PostUiState.Empty
        else PostUiState.Loaded(filteredPosts)
        postUiStateCommunication.emit(state)
    }

    fun onEvent(event: SearchScreenEvent) {
        when (event) {
            is SearchScreenEvent.RefreshAllData -> refreshAllData()
            is SearchScreenEvent.OnSearchValueChange -> setSearchQuery(event.value)
            is SearchScreenEvent.OnCategoryClick -> doCategoryClick(event.category)
            is SearchScreenEvent.OnPostClick -> navigatePostScreen(event.postId)
            is SearchScreenEvent.OnCommentClick -> navigatePostScreen(event.postId, true)
            is SearchScreenEvent.OnProfileClick -> navigateProfileScreen(event.userId)
        }
    }

    private fun loadAllData(): Job = screenModelScope.launchSafe(
        onError = { setUiStateToError(MainResStrings.default_error_message) }
    ) {
        mutableState.tryEmit(SearchUiState.Loading)
        fetchAllCategories()
    }

    private fun refreshAllData() {
        allDataJob = null
        allDataJob = loadAllData()
    }

    private fun startSearchItems(query: String) {
        cancelSearchJobs()
        if (query.isNotEmpty()) {
            searchDataJob = screenModelScope.launchSafe {
                val usersDeferred = async { searchUsersByQuery(query) }
                val postsDeferred = async { searchPostsByQuery(query) }
                usersDeferred.await()
                postsDeferred.await()
            }
        }
    }

    private fun cancelSearchJobs() {
        searchDataJob?.cancel()
        searchDataJob = null
        postsFlow.tryEmit(emptyList())
        usersFlow.tryEmit(emptyList())
    }

    private suspend fun searchPostsByQuery(query: String) {
        postUiStateCommunication.emit(PostUiState.Loading)
        when (val response = searchPostsByQueryUseCase(query, PAGE, PAGE_SIZE)) {
            is Result.Success -> {
                val posts = response.data?.map(postDomainToPostMapper::map) ?: emptyList()
                postsFlow.tryEmit(posts)
            }

            is Result.Error -> {
                val message = response.message ?: defaultErrorMessage
                postUiStateCommunication.emit(PostUiState.Error(message))
            }
        }
    }

    private suspend fun searchUsersByQuery(query: String) {
        userUiStateCommunication.emit(UsersUiState.Loading)
        when (val response = searchUsersByQueryUseCase(query, PAGE, PAGE_SIZE)) {
            is Result.Success -> {
                val users = response.data?.map(userInfoMapper::map) ?: emptyList()
                usersFlow.tryEmit(users)
            }

            is Result.Error -> {
                val message = response.message ?: defaultErrorMessage
                userUiStateCommunication.emit(UsersUiState.Error(message))
            }
        }
    }

    private suspend fun fetchAllCategories() {
        when (val response = fetchAllCategoriesUseCase()) {
            is Result.Success -> (response.data ?: emptyList())
                .map(categoryDomainToCategoryMapper::map)
                .also(::setUiStateByReceivedCategories)

            is Result.Error -> {
                val message = response.message
                setUiStateToError(message)
                showErrorSnackbar(message)
            }
        }
    }

    private fun doCategoryClick(category: Category) {
        val uiState = mutableState.value as? SearchUiState.Loaded ?: return
        if (uiState.selectedCategory == category) return
        mutableState.tryEmit(uiState.copy(selectedCategory = category))
    }

    private fun navigatePostScreen(postId: Int, shouldShowAddCommentDialog: Boolean = false) {
        navigationScreenCommunication.emit(
            postScreenProvider.postDetailsScreen(postId, shouldShowAddCommentDialog)
        )
    }

    private fun navigateProfileScreen(userId: Int) {
        navigationScreenCommunication.emit(profileScreenProvider.profileScreen(userId))
    }

    private fun setSearchQuery(value: String) {
        val uiState = mutableState.value as? SearchUiState.Loaded ?: return
        val searchQueryValue = if (value.trim().isEmpty()) value.trim() else value
        mutableState.tryEmit(uiState.copy(searchQueryValue = searchQueryValue))
    }

    private fun showErrorSnackbar(message: String?) {
        val errorMessage = message ?: MainResStrings.default_error_message
        snackbarDisplay.showSnackbar(FriendSyncSnackbar.Error(errorMessage))
    }

    private fun setUiStateByReceivedCategories(categories: List<Category>) {
        val selectedCategory = categories.firstOrNull() ?: Category.unknown
        (mutableState.value as? SearchUiState.Loaded)?.copy(
            categories = categories,
            selectedCategory = selectedCategory
        ) ?: SearchUiState.Loaded(
            categories = categories,
            selectedCategory = selectedCategory
        ).also(mutableState::tryEmit)
    }

    private fun handleUsersState(users: List<UserInfo>) {
        val state = if (users.isEmpty()) UsersUiState.Empty
        else UsersUiState.Loaded(users)
        userUiStateCommunication.emit(state)
    }

    private fun handleError(error: Throwable) {
        Napier.e(error.stackTraceToString())
    }

    private fun handleSelectedCategory(category: Category) {
        val categoryType = findCategoryTypeByValue(category.name)
        categoryTypeCommunication.emit(categoryType)
    }

    private fun setUiStateToError(message: String?) {
        val errorMessage = message ?: MainResStrings.default_error_message
        mutableState.tryEmit(SearchUiState.Error(errorMessage))
    }

    override fun onDispose() {
        allDataJob = null
        searchDataJob = null
        super.onDispose()
    }
}
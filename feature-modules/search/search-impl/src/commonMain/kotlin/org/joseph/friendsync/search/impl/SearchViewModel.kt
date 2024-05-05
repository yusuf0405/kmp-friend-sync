package org.joseph.friendsync.search.impl

import androidx.lifecycle.viewModelScope
import io.github.aakira.napier.Napier
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import org.joseph.friendsync.core.FriendSyncViewModel
import org.joseph.friendsync.core.Result
import org.joseph.friendsync.core.extensions.launchSafe
import org.joseph.friendsync.core.extensions.onError
import org.joseph.friendsync.core.ui.common.communication.NavigationRouteFlowCommunication
import org.joseph.friendsync.core.ui.common.communication.UsersStateFlowCommunication
import org.joseph.friendsync.core.ui.common.communication.navigationParams
import org.joseph.friendsync.core.ui.common.extensions.createMutableSharedFlowAsLiveData
import org.joseph.friendsync.core.ui.snackbar.FriendSyncSnackbar
import org.joseph.friendsync.core.ui.snackbar.SnackbarDisplayer
import org.joseph.friendsync.core.ui.strings.MainResStrings
import org.joseph.friendsync.domain.UserDataStore
import org.joseph.friendsync.domain.markers.post.PostMarksManager
import org.joseph.friendsync.domain.markers.users.UserMarksManager
import org.joseph.friendsync.domain.models.UserPreferences
import org.joseph.friendsync.domain.post.PostsObserveType
import org.joseph.friendsync.domain.usecases.categories.FetchAllCategoriesUseCase
import org.joseph.friendsync.domain.usecases.post.PostLikeOrDislikeInteractor
import org.joseph.friendsync.domain.usecases.post.SearchPostsByQueryUseCase
import org.joseph.friendsync.domain.usecases.subscriptions.SubscriptionsInteractor
import org.joseph.friendsync.domain.usecases.user.SearchUsersByQueryUseCase
import org.joseph.friendsync.search.impl.category.CategoryType
import org.joseph.friendsync.search.impl.category.CategoryType.Companion.findCategoryTypeByValue
import org.joseph.friendsync.search.impl.category.CategoryTypeCommunication
import org.joseph.friendsync.search.impl.di.SearchFeatureDependencies
import org.joseph.friendsync.search.impl.post.PostUiState
import org.joseph.friendsync.search.impl.post.PostUiStateCommunication
import org.joseph.friendsync.search.impl.user.UserUiStateCommunication
import org.joseph.friendsync.search.impl.user.UsersUiState
import org.joseph.friendsync.ui.components.mappers.CategoryDomainToCategoryMapper
import org.joseph.friendsync.ui.components.mappers.PostMarkDomainToUiMapper
import org.joseph.friendsync.ui.components.mappers.UserInfoDomainToUserInfoMapper
import org.joseph.friendsync.ui.components.mappers.UserInfoToDomainMapper
import org.joseph.friendsync.ui.components.mappers.UserMarkDomainToUiMapper
import org.joseph.friendsync.ui.components.models.Category
import org.joseph.friendsync.ui.components.models.Post
import org.joseph.friendsync.ui.components.models.PostMark
import org.joseph.friendsync.ui.components.models.UserInfoMark
import org.joseph.friendsync.ui.components.models.filterPhotoPosts
import org.joseph.friendsync.ui.components.models.filterTextPosts
import org.koin.core.component.KoinComponent

private const val SEARCH_DEBOUNCE = 300L
private const val PAGE = 1
private const val PAGE_SIZE = 100

internal class SearchViewModel(
    private val dependencies: SearchFeatureDependencies,
    private val userDataStore: UserDataStore,
    private val searchPostsByQueryUseCase: SearchPostsByQueryUseCase,
    private val searchUsersByQueryUseCase: SearchUsersByQueryUseCase,
    private val fetchAllCategoriesUseCase: FetchAllCategoriesUseCase,
    private val subscriptionsInteractor: SubscriptionsInteractor,
    private val userMarksManager: UserMarksManager,
    private val postMarksManager: PostMarksManager,
    private val postLikeOrDislikeInteractor: PostLikeOrDislikeInteractor,
    private val categoryDomainToCategoryMapper: CategoryDomainToCategoryMapper,
    private val userInfoMapper: UserInfoDomainToUserInfoMapper,
    private val postMarkDomainToUiMapper: PostMarkDomainToUiMapper,
    private val userInfoToDomainMapper: UserInfoToDomainMapper,
    private val userMarkDomainToUiMapper: UserMarkDomainToUiMapper,
    private val snackbarDisplayer: SnackbarDisplayer,
    private val postUiStateCommunication: PostUiStateCommunication,
    private val userUiStateCommunication: UserUiStateCommunication,
    private val categoryTypeCommunication: CategoryTypeCommunication,
    private val usersStateFlowCommunication: UsersStateFlowCommunication,
    private val navigationCommunication: NavigationRouteFlowCommunication,
) : FriendSyncViewModel<SearchUiState>(SearchUiState.Initial), KoinComponent {

    val postUiStateFlow: StateFlow<PostUiState> = postUiStateCommunication.observe()
    val userUiStateFlow: StateFlow<UsersUiState> = userUiStateCommunication.observe()
    val categoryTypeFlow: StateFlow<CategoryType> = categoryTypeCommunication.observe()

    private val postsFlow = createMutableSharedFlowAsLiveData<List<Post>>()
    private val usersFlow = usersStateFlowCommunication.observe()
    private val loadedUiStateFlow = mutableState.filterIsInstance<SearchUiState.Loaded>()

    private val searchQueryFlow = loadedUiStateFlow
        .map { it.searchQueryValue }
        .stateIn(viewModelScope, SharingStarted.Lazily, String())

    private val selectedCategoryFlow = loadedUiStateFlow
        .map { it.selectedCategory }
        .stateIn(viewModelScope, SharingStarted.Lazily, Category.unknown)

    private var allDataJob: Job? = null
    private var searchDataJob: Job? = null
    private val defaultErrorMessage = MainResStrings.default_error_message
    private var currentUser: UserPreferences = UserPreferences.unknown

    init {
        allDataJob = loadAllData()

        usersFlow.map { users -> users.map(userInfoToDomainMapper::map) }
            .flatMapLatest(userMarksManager::observeUsersWithMarks)
            .map { users -> users.map(userMarkDomainToUiMapper::map) }
            .onEach(::handleUsersState)
            .onError(::handleError)
            .launchIn(viewModelScope)

        searchQueryFlow.debounce(SEARCH_DEBOUNCE)
            .onEach(::startSearchItems)
            .onError(::handleError)
            .launchIn(viewModelScope)

        selectedCategoryFlow
            .onEach(::handleSelectedCategory)
            .onError(::handleError)
            .launchIn(viewModelScope)

        combine(
            searchQueryFlow.debounce(SEARCH_DEBOUNCE),
            postMarksManager.observePostWithMarks(PostsObserveType.Simple)
                .map { it.map(postMarkDomainToUiMapper::map) },
            categoryTypeFlow
        ) { query, posts, category ->
            val postMarks = if (query.isBlank()) emptyList()
            else posts.filter { it.doesContainsText(query) }
            handlePostsState(postMarks, category)
        }.onError {
            mutableState.tryEmit(SearchUiState.Error(defaultErrorMessage))
        }.launchIn(viewModelScope)
    }


    private fun handlePostsState(posts: List<PostMark>, selectedCategory: CategoryType) {
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
            is SearchScreenEvent.OnFollowButtonClick -> doFollowButtonClick(event)
            is SearchScreenEvent.OnLikeClick -> doLikeButtonClick(event)
            is SearchScreenEvent.OnEditProfile -> navigateEditProfile()
        }
    }

    private fun loadAllData(): Job = viewModelScope.launchSafe(
        onError = { setUiStateToError(MainResStrings.default_error_message) }
    ) {
        fetchCurrentUser()
        mutableState.tryEmit(SearchUiState.Loading)
        fetchAllCategories()
    }

    private fun refreshAllData() {
        allDataJob = null
        allDataJob = loadAllData()
    }

    private fun fetchCurrentUser() {
        currentUser = userDataStore.fetchCurrentUser()
    }

    private fun startSearchItems(query: String) {
        cancelSearchJobs()
        if (query.isNotEmpty()) {
            searchDataJob = viewModelScope.launchSafe {
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
        usersStateFlowCommunication.emit(emptyList())
    }

    private suspend fun searchPostsByQuery(query: String) {
        val response = searchPostsByQueryUseCase(query, PAGE, PAGE_SIZE)
        if (response.isError()) {
            val message = response.message ?: defaultErrorMessage
            postUiStateCommunication.emit(PostUiState.Error(message))
        }
    }

    private suspend fun searchUsersByQuery(query: String) {
        when (val response = searchUsersByQueryUseCase(query, PAGE, PAGE_SIZE)) {
            is Result.Success -> {
                val users = response.data?.map(userInfoMapper::map) ?: emptyList()
                usersStateFlowCommunication.emit(users)
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
            }
        }
    }

    private fun doFollowButtonClick(event: SearchScreenEvent.OnFollowButtonClick) {
        val followerId = currentUser.id
        val followingId = event.userId
        viewModelScope.launchSafe {
            if (event.isFollow) subscriptionsInteractor.cancelSubscription(
                followerId, followingId
            ).apply { if (isError()) showErrorSnackbar(message) }
            else subscriptionsInteractor.createSubscription(
                followerId, followingId
            ).apply { if (isError()) showErrorSnackbar(message) }
        }
    }

    private fun doCategoryClick(category: Category) {
        val uiState = mutableState.value as? SearchUiState.Loaded ?: return
        if (uiState.selectedCategory == category) return
        mutableState.tryEmit(uiState.copy(selectedCategory = category))
    }

    private fun navigatePostScreen(postId: Int, shouldShowAddCommentDialog: Boolean = false) {
        navigationCommunication.emit(
            navigationParams(dependencies.getPostRoute(postId, shouldShowAddCommentDialog))
        )
    }

    private fun navigateProfileScreen(userId: Int) {
        navigationCommunication.emit(navigationParams(dependencies.getProfileRoute(userId)))
    }

    private fun doLikeButtonClick(event: SearchScreenEvent.OnLikeClick) {
        viewModelScope.launchSafe {
            if (event.isLiked) postLikeOrDislikeInteractor.unlikePost(
                currentUser.id,
                event.postId
            )
            else postLikeOrDislikeInteractor.likePost(
                currentUser.id,
                event.postId
            )
        }
    }

    private fun setSearchQuery(value: String) {
        val uiState = mutableState.value as? SearchUiState.Loaded ?: return
        val searchQueryValue = if (value.trim().isEmpty()) value.trim() else value
        mutableState.tryEmit(uiState.copy(searchQueryValue = searchQueryValue))
    }

    private fun showErrorSnackbar(message: String?) {
        val errorMessage = message ?: MainResStrings.default_error_message
        snackbarDisplayer.showSnackbar(FriendSyncSnackbar.Error(errorMessage))
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

    private fun navigateEditProfile() {
        navigationCommunication.emit(navigationParams(dependencies.getEditProfileRoute()))
    }

    private fun handleUsersState(users: List<UserInfoMark>) {
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

    override fun onCleared() {
        allDataJob = null
        searchDataJob = null
        super.onCleared()
    }
}
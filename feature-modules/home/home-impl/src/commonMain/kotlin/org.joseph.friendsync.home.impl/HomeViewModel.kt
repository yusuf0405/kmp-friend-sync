package org.joseph.friendsync.home.impl

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import org.joseph.friendsync.common.user.UserDataStore
import org.joseph.friendsync.common.user.UserPreferences
import org.joseph.friendsync.common.util.Result
import org.joseph.friendsync.common.util.coroutines.DispatcherProvider
import org.joseph.friendsync.common.util.coroutines.asyncWithDefault
import org.joseph.friendsync.common.util.coroutines.launchSafe
import org.joseph.friendsync.common.util.coroutines.onError
import org.joseph.friendsync.core.FriendSyncViewModel
import org.joseph.friendsync.core.ui.common.communication.NavigationRouteFlowCommunication
import org.joseph.friendsync.core.ui.common.communication.UsersStateFlowCommunication
import org.joseph.friendsync.core.ui.common.communication.navigationParams
import org.joseph.friendsync.core.ui.common.managers.post.PostMarksManager
import org.joseph.friendsync.core.ui.common.managers.subscriptions.UserMarksManager
import org.joseph.friendsync.core.ui.common.observers.post.PostsObserver
import org.joseph.friendsync.core.ui.common.usecases.post.like.PostLikeOrDislikeInteractor
import org.joseph.friendsync.core.ui.snackbar.FriendSyncSnackbar.Error
import org.joseph.friendsync.core.ui.snackbar.SnackbarDisplayer
import org.joseph.friendsync.core.ui.strings.MainResStrings
import org.joseph.friendsync.domain.models.PostDomain
import org.joseph.friendsync.domain.usecases.likes.FetchLikedPostsUseCase
import org.joseph.friendsync.domain.usecases.onboarding.FetchOnboardingUsersUseCase
import org.joseph.friendsync.domain.usecases.post.FetchRecommendedPostsUseCase
import org.joseph.friendsync.domain.usecases.subscriptions.FetchUserSubscriptionsInteractor
import org.joseph.friendsync.domain.usecases.subscriptions.SubscriptionsInteractor
import org.joseph.friendsync.home.impl.di.HomeFeatureDependencies
import org.joseph.friendsync.home.impl.onboarding.OnBoardingUiState
import org.joseph.friendsync.home.impl.onboarding.OnboardingStateStateFlowCommunication
import org.joseph.friendsync.ui.components.mappers.UserInfoDomainToUserInfoMapper
import org.koin.core.component.KoinComponent

private const val DEFAULT_PAGE = 1
private const val DEFAULT_PAGE_SIZE = 5

internal class HomeViewModel(
    private val homeFeatureDependencies: HomeFeatureDependencies,
    private val userDataStore: UserDataStore,
    private val dispatcherProvider: DispatcherProvider,
    private val fetchOnboardingUsersUseCase: FetchOnboardingUsersUseCase,
    private val fetchRecommendedPostsUseCase: FetchRecommendedPostsUseCase,
    private val fetchLikedPostsUseCase: FetchLikedPostsUseCase,
    private val postLikeOrDislikeInteractor: PostLikeOrDislikeInteractor,
    private val fetchUserSubscriptionsInteractor: FetchUserSubscriptionsInteractor,
    private val userInfoDomainToUserInfoMapper: UserInfoDomainToUserInfoMapper,
    private val subscriptionsInteractor: SubscriptionsInteractor,
    private val userMarksManager: UserMarksManager,
    private val postMarksManager: PostMarksManager,
    private val snackbarDisplayer: SnackbarDisplayer,
    private val onboardingCommunication: OnboardingStateStateFlowCommunication,
    private val usersStateFlowCommunication: UsersStateFlowCommunication,
    private val navigationCommunication: NavigationRouteFlowCommunication,
) : FriendSyncViewModel<HomeUiState>(HomeUiState.Initial), KoinComponent {

    private var currentPage = DEFAULT_PAGE
    private var currentUser: UserPreferences = UserPreferences.unknown

    private val defaultErrorMessage = MainResStrings.default_error_message

    private val mutex = Mutex()
    private var allDataJob: Job? = null

    val onBoardingUiState: StateFlow<OnBoardingUiState> = onboardingCommunication.observe()

    private var usersFlow = usersStateFlowCommunication.observe()

    private var isLastPage: Boolean = false

    init {
        allDataJob = startLoadAllData()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun startLoadAllData() = viewModelScope.launchSafe(
        dispatcherProvider.io,
        onError = { mutableState.tryEmit(HomeUiState.Error(defaultErrorMessage)) }
    ) {
        mutableState.tryEmit(HomeUiState.Loading)
        awaitAll(
            asyncFetchCurrentUser(),
            asyncFetchPosts(),
            asyncFetchOnboardingUsers(),
        )
        fetchLikedPostsUseCase.invoke(currentUser.id)
        fetchUserSubscriptionsInteractor.fetchUserSubscriptions(currentUser.id)

        postMarksManager.observePostWithMarks(PostsObserver.Recommended).onEach { postMarks ->
            val state = mutableState.value as? HomeUiState.Content ?: HomeUiState.Content.unknown
            mutableState.tryEmit(state.copy(postMarks = postMarks, isPaging = false))
        }.onError {
            mutableState.tryEmit(HomeUiState.Error(defaultErrorMessage))
        }.launchIn(viewModelScope)

        usersFlow.flatMapLatest { userMarksManager.observeUsersWithMarks(it) }.onEach { users ->
            val onboardingState = onboardingCommunication.value()
            onboardingCommunication.emit(
                onboardingState.copy(
                    users = users,
                    shouldShowOnBoarding = users.isNotEmpty()
                )
            )
        }.launchIn(viewModelScope)
    }

    fun onEvent(event: HomeScreenEvent) {
        when (event) {
            is HomeScreenEvent.FetchMorePosts -> fetchMorePosts()
            is HomeScreenEvent.RefreshAllData -> refreshAll()
            is HomeScreenEvent.OnCommentClick -> navigatePostScreen(event.postId, true)
            is HomeScreenEvent.OnLikeClick -> doLikeButtonClick(event)
            is HomeScreenEvent.OnProfileClick -> navigateProfileScreen(event.userId)
            is HomeScreenEvent.OnPostClick -> navigatePostScreen(event.postId)
            is HomeScreenEvent.OnStoriesClick -> {}
            is HomeScreenEvent.OnFollowItemClick -> {}
            is HomeScreenEvent.OnFollowButtonClick -> doFollowButtonClick(event)
            is HomeScreenEvent.OnBoardingFinish -> doOnboardingFinish()
            is HomeScreenEvent.OnUserClick -> navigateProfileScreen(event.user.id)
        }
    }

    private fun refreshAll() {
        allDataJob?.cancel()
        currentPage = DEFAULT_PAGE
        allDataJob = startLoadAllData()
    }

    private fun fetchMorePosts() {
        viewModelScope.launchSafe(dispatcherProvider.io) { asyncFetchPosts().await() }
    }

    private suspend fun asyncFetchCurrentUser() = asyncWithDefault(Unit) {
        currentUser = userDataStore.fetchCurrentUser()
    }

    private suspend fun asyncFetchPosts() = asyncWithDefault(Unit) {
        val state = mutableState.value as? HomeUiState.Content ?: HomeUiState.Content.unknown
        if (state.isPaging || isLastPage) return@asyncWithDefault
        if (state != HomeUiState.Content.unknown) mutableState.tryEmit(state.copy(isPaging = true))

        val response = fetchRecommendedPostsUseCase(
            page = currentPage,
            pageSize = DEFAULT_PAGE_SIZE,
            userId = currentUser.id
        )

        when (response) {
            is Result.Success -> fetchStateForSuccessfulPostsRequest(state, response)
            is Result.Error -> mutableState.emit(
                HomeUiState.Error(response.message ?: defaultErrorMessage)
            )
        }
    }

    private suspend fun fetchStateForSuccessfulPostsRequest(
        state: HomeUiState.Content,
        response: Result<List<PostDomain>>
    ) = response.data?.let { postsDomain ->
        mutex.withLock { currentPage++ }
        isLastPage = postsDomain.isEmpty()
        mutableState.update { state.copy(isPaging = false) }
    }

    private suspend fun asyncFetchOnboardingUsers() = asyncWithDefault(Unit) {
        when (val result = fetchOnboardingUsersUseCase(currentUser.id)) {
            is Result.Success -> {
                val posts = result.data?.map(userInfoDomainToUserInfoMapper::map) ?: emptyList()
                usersStateFlowCommunication.emit(posts)
            }

            is Result.Error -> {
                // handle error
            }
        }
    }

    fun navigateChatScreen() {
        val params = navigationParams(homeFeatureDependencies.getChatRoute())
        navigationCommunication.emit(params)
    }

    private fun navigatePostScreen(postId: Int, shouldShowAddCommentDialog: Boolean = false) {
        val route = homeFeatureDependencies.getPostRoute(postId, shouldShowAddCommentDialog)
        navigationCommunication.emit(navigationParams(route))
    }

    private fun navigateProfileScreen(userId: Int) {
        navigationCommunication.emit(navigationParams(homeFeatureDependencies.getProfileRoute(userId)))
    }

    private fun doLikeButtonClick(event: HomeScreenEvent.OnLikeClick) {
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

    private fun doFollowButtonClick(event: HomeScreenEvent.OnFollowButtonClick) {
        val followerId = currentUser.id
        val followingId = event.user.id
        viewModelScope.launchSafe {
            if (event.isFollow) subscriptionsInteractor.cancelSubscription(
                followerId, followingId
            )
            else subscriptionsInteractor.createSubscription(
                followerId, followingId
            ).apply { if (isError()) showErrorSnackbar(message) }
        }
    }

    private fun doOnboardingFinish() {
        onboardingCommunication.update { currentState ->
            currentState.copy(shouldShowOnBoarding = false)
        }
    }

    private fun showErrorSnackbar(message: String? = null) {
        snackbarDisplayer.showSnackbar(Error(message ?: defaultErrorMessage))
    }

    override fun onCleared() {
        allDataJob?.cancel()
        super.onCleared()
    }
}
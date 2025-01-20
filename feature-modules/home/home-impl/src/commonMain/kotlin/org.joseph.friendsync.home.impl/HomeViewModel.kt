package org.joseph.friendsync.home.impl

import androidx.lifecycle.viewModelScope
import kmp_friend_sync.core_ui.generated.resources.Res
import kmp_friend_sync.core_ui.generated.resources.default_error_message
import kotlinx.coroutines.Job
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import org.jetbrains.compose.resources.getString
import org.joseph.friendsync.core.FriendSyncViewModel
import org.joseph.friendsync.core.extensions.asyncSafe
import org.joseph.friendsync.core.extensions.launchSafe
import org.joseph.friendsync.core.ui.common.communication.NavigationRouteFlowCommunication
import org.joseph.friendsync.core.ui.common.communication.UsersStateFlowCommunication
import org.joseph.friendsync.core.ui.common.communication.navigationParams
import org.joseph.friendsync.core.ui.snackbar.FriendSyncSnackbar.Error
import org.joseph.friendsync.core.ui.snackbar.SnackbarDisplayer
import org.joseph.friendsync.domain.UserDataStore
import org.joseph.friendsync.domain.models.UserPreferences
import org.joseph.friendsync.domain.usecases.likes.FetchLikedPostsUseCase
import org.joseph.friendsync.domain.usecases.onboarding.FetchOnboardingUsersUseCase
import org.joseph.friendsync.domain.usecases.post.FetchRecommendedPostsUseCase
import org.joseph.friendsync.domain.usecases.post.PostLikeOrDislikeInteractor
import org.joseph.friendsync.domain.usecases.subscriptions.FetchUserSubscriptionsInteractor
import org.joseph.friendsync.domain.usecases.subscriptions.SubscriptionsInteractor
import org.joseph.friendsync.home.impl.di.HomeFeatureDependencies
import org.joseph.friendsync.home.impl.onboarding.OnBoardingUiState
import org.joseph.friendsync.home.impl.onboarding.OnboardingStateStateFlowCommunication
import org.joseph.friendsync.ui.components.mappers.PostDomainToPostMapper
import org.joseph.friendsync.ui.components.mappers.UserInfoDomainToUserInfoMapper
import org.joseph.friendsync.ui.components.markers.NewPostMarksManager
import org.joseph.friendsync.ui.components.markers.users.UserMarksManager
import org.joseph.friendsync.ui.components.models.Post
import org.koin.core.component.KoinComponent

private const val DEFAULT_PAGE = 1
private const val DEFAULT_PAGE_SIZE = 5

internal class HomeViewModel(
    private val homeFeatureDependencies: HomeFeatureDependencies,
    private val userDataStore: UserDataStore,
    private val fetchOnboardingUsersUseCase: FetchOnboardingUsersUseCase,
    private val fetchRecommendedPostsUseCase: FetchRecommendedPostsUseCase,
    private val fetchLikedPostsUseCase: FetchLikedPostsUseCase,
    private val postLikeOrDislikeInteractor: PostLikeOrDislikeInteractor,
    private val fetchUserSubscriptionsInteractor: FetchUserSubscriptionsInteractor,
    private val newPostMarksManager: NewPostMarksManager,
    private val userInfoDomainToUserInfoMapper: UserInfoDomainToUserInfoMapper,
    private val postDomainToPostMapper: PostDomainToPostMapper,
    private val subscriptionsInteractor: SubscriptionsInteractor,
    private val userMarksManager: UserMarksManager,
    private val snackbarDisplayer: SnackbarDisplayer,
    private val onboardingCommunication: OnboardingStateStateFlowCommunication,
    private val usersStateFlowCommunication: UsersStateFlowCommunication,
    private val navigationCommunication: NavigationRouteFlowCommunication,
) : FriendSyncViewModel<HomeUiState>(HomeUiState.Initial), KoinComponent {

    private var currentPage: Int = DEFAULT_PAGE
    private var currentUser: UserPreferences = UserPreferences.unknown
    private var isLastPage: Boolean = false

    private val defaultErrorMessage = Res.string.default_error_message
    private var allDataJob: Job? = null

    val onBoardingUiState: StateFlow<OnBoardingUiState> = onboardingCommunication.observe()
    private val usersFlow = usersStateFlowCommunication.observe()
    private val postsFlow = MutableStateFlow<List<Post>>(emptyList())

    init {
        allDataJob = startLoadAllData()
        observeData()
    }

    private fun startLoadAllData() = viewModelScope.launchSafe(
        onError = {
            viewModelScope.launchSafe {
                mutableState.emit(HomeUiState.Error(getString(defaultErrorMessage)))
            }
        }
    ) {
        mutableState.emit(HomeUiState.Loading)
        currentUser = userDataStore.fetchCurrentUser()

        awaitAll(
            asyncSafe { fetchRecommendedPosts() },
            asyncSafe { fetchOnboardingUsers() },
            asyncSafe { fetchLikedPostsUseCase.invoke(currentUser.id) },
            asyncSafe { fetchUserSubscriptionsInteractor.fetchUserSubscriptions(currentUser.id) },
        )
    }

    private fun observeData() {
        postsFlow.flatMapLatest(newPostMarksManager::observePostWithMarks).onEach { postMarks ->
            mutableState.update { currentState ->
                val state = currentState as? HomeUiState.Content ?: HomeUiState.Content.unknown
                state.copy(postMarks = postMarks, isPaging = false)
            }
        }.launchIn(viewModelScope)

        usersFlow.flatMapLatest(userMarksManager::observeUsersWithMarks).onEach { users ->
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
            is HomeScreenEvent.FetchMorePosts -> {
                viewModelScope.launchSafe { fetchRecommendedPosts() }
            }

            is HomeScreenEvent.RefreshAllData -> {
                allDataJob?.cancel()
                currentPage = DEFAULT_PAGE
                allDataJob = startLoadAllData()
            }

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

    private suspend fun fetchRecommendedPosts() {
        val state = mutableState.value as? HomeUiState.Content ?: HomeUiState.Content.unknown
        if (state.isPaging || isLastPage) return
        if (state != HomeUiState.Content.unknown) {
            mutableState.tryEmit(state.copy(isPaging = true))
        }

        fetchRecommendedPostsUseCase(
            page = currentPage,
            pageSize = DEFAULT_PAGE_SIZE,
            userId = currentUser.id
        ).map(postDomainToPostMapper::map).apply {
            postsFlow.emit(postsFlow.value + this)
            isLastPage = isEmpty()
        }

        currentPage++
        mutableState.update { state.copy(isPaging = false) }
    }

    private suspend fun fetchOnboardingUsers() {
        val usersDomain = fetchOnboardingUsersUseCase(currentUser.id)
        val users = usersDomain.map(userInfoDomainToUserInfoMapper::map)
        usersStateFlowCommunication.emit(users)
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
            if (event.isFollow) {
                subscriptionsInteractor.cancelSubscription(
                    followerId = followerId,
                    followingId = followingId
                )
            } else {
                subscriptionsInteractor.createSubscription(
                    followerId = followerId,
                    followingId = followingId
                ).apply { if (isError()) showErrorSnackbar(message) }
            }
        }
    }

    private fun doOnboardingFinish() {
        onboardingCommunication.update { currentState ->
            currentState.copy(shouldShowOnBoarding = false)
        }
    }

    private fun showErrorSnackbar(message: String? = null) {
        viewModelScope.launchSafe {
            snackbarDisplayer.showSnackbar(Error(message ?: getString(defaultErrorMessage)))
        }
    }

    override fun onCleared() {
        allDataJob?.cancel()
        super.onCleared()
    }
}
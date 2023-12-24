package org.joseph.friendsync.home.impl

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import cafe.adriel.voyager.core.screen.Screen
import kotlinx.coroutines.Job
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import org.joseph.friendsync.common.user.UserDataStore
import org.joseph.friendsync.common.user.UserPreferences
import org.joseph.friendsync.common.util.Result
import org.joseph.friendsync.common.util.coroutines.asyncWithDefault
import org.joseph.friendsync.common.util.coroutines.callSafe
import org.joseph.friendsync.common.util.coroutines.launchSafe
import org.joseph.friendsync.core.ui.common.communication.GlobalNavigationFlowCommunication
import org.joseph.friendsync.core.ui.common.communication.NavCommand
import org.joseph.friendsync.core.ui.common.communication.NavigationScreenStateFlowCommunication
import org.joseph.friendsync.core.ui.snackbar.FriendSyncSnackbar
import org.joseph.friendsync.core.ui.snackbar.FriendSyncSnackbar.Error
import org.joseph.friendsync.core.ui.snackbar.SnackbarDisplay
import org.joseph.friendsync.core.ui.strings.MainResStrings
import org.joseph.friendsync.domain.managers.SubscriptionsManager
import org.joseph.friendsync.domain.models.PostDomain
import org.joseph.friendsync.domain.models.UserInfoDomain
import org.joseph.friendsync.domain.usecases.onboarding.FetchOnboardingUsersUseCase
import org.joseph.friendsync.domain.usecases.post.FetchRecommendedPostsUseCase
import org.joseph.friendsync.domain.usecases.subscriptions.SubscriptionsInteractor
import org.joseph.friendsync.home.impl.onboarding.OnBoardingUiState
import org.joseph.friendsync.home.impl.onboarding.OnboardingStateStateFlowCommunication
import org.joseph.friendsync.post.api.PostScreenProvider
import org.joseph.friendsync.profile.api.ProfileScreenProvider
import org.joseph.friendsync.ui.components.mappers.PostDomainToPostMapper
import org.joseph.friendsync.ui.components.mappers.UserInfoDomainToUserInfoMapper
import org.joseph.friendsync.ui.components.models.user.UserInfo
import org.koin.core.component.KoinComponent

private const val DEFAULT_PAGE = 1
private const val DEFAULT_PAGE_SIZE = 5

class HomeViewModel(
    private val userDataStore: UserDataStore,
    private val fetchOnboardingUsersUseCase: FetchOnboardingUsersUseCase,
    private val fetchRecommendedPostsUseCase: FetchRecommendedPostsUseCase,
    private val userInfoDomainToUserInfoMapper: UserInfoDomainToUserInfoMapper,
    private val subscriptionsManager: SubscriptionsManager,
    private val postDomainToPostMapper: PostDomainToPostMapper,
    private val snackbarDisplay: SnackbarDisplay,
    private val onboardingCommunication: OnboardingStateStateFlowCommunication,
    private val navigationScreenCommunication: NavigationScreenStateFlowCommunication,
    private val globalNavigationFlowCommunication: GlobalNavigationFlowCommunication,
    private val postScreenProvider: PostScreenProvider,
    private val profileScreenProvider: ProfileScreenProvider,
) : StateScreenModel<HomeUiState>(HomeUiState.Initial), KoinComponent {

    private var currentPage = DEFAULT_PAGE
    private var currentUser: UserPreferences = UserPreferences.unknown

    private val defaultErrorMessage = MainResStrings.default_error_message

    private val mutex = Mutex()
    private var allDataJob: Job? = null

    val onBoardingUiState: StateFlow<OnBoardingUiState> = onboardingCommunication.observe()
    val navigationScreenFlow: SharedFlow<Screen?> = navigationScreenCommunication.observe()

    init {
        allDataJob = startLoadAllData()
    }

    private fun startLoadAllData() = screenModelScope.launchSafe(
        onError = {
            snackbarDisplay.showSnackbar(Error(defaultErrorMessage))
            mutableState.tryEmit(HomeUiState.Error(defaultErrorMessage))
        }
    ) {
        mutableState.tryEmit(HomeUiState.Loading)
        awaitAll(
            asyncFetchCurrentUser(),
            asyncFetchPosts(),
            asyncFetchOnboardingUsers(),
        )
        subscriptionsManager.fetchSubscriptionUserIds()
        subscriptionsManager.subscribeUserIdsFlow.onEach { userIds ->
            val currentState = onBoardingUiState.value
            val users = currentState.users.map { user ->
                user.copy(isSubscribed = userIds.contains(user.id))
            }
            onboardingCommunication.emit(currentState.copy(users = users))
        }.launchIn(screenModelScope)
    }

    fun onEvent(event: HomeScreenEvent) {
        when (event) {
            is HomeScreenEvent.FetchMorePosts -> fetchMorePosts()
            is HomeScreenEvent.RefreshAllData -> refreshAll()
            is HomeScreenEvent.OnCommentClick -> navigatePostScreen(event.postId, true)
            is HomeScreenEvent.OnLikeClick -> {}
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
        screenModelScope.launchSafe { asyncFetchPosts().await() }
    }

    private suspend fun asyncFetchCurrentUser() = asyncWithDefault(Unit) {
        currentUser = userDataStore.fetchCurrentUser()
    }

    private suspend fun asyncFetchPosts() = asyncWithDefault(Unit) {
        val state = mutableState.value as? HomeUiState.Content ?: HomeUiState.Content.unknown
        if (state.isPaging) return@asyncWithDefault
        if (state != HomeUiState.Content.unknown) mutableState.tryEmit(state.copy(isPaging = true))

        delay(1500)
        val response = fetchRecommendedPostsUseCase(
            page = currentPage,
            pageSize = DEFAULT_PAGE_SIZE,
            userId = currentUser.id
        )

        val uiState = when (response) {
            is Result.Success -> fetchStateForSuccessfulPostsRequest(state, response)
            is Result.Error -> HomeUiState.Error(response.message ?: defaultErrorMessage)
        }
        mutableState.emit(uiState)
    }


    private suspend fun fetchStateForSuccessfulPostsRequest(
        state: HomeUiState.Content,
        response: Result<List<PostDomain>>
    ): HomeUiState = response.data?.let { postsDomain ->
        val remotePosts = postsDomain.map { postDomainToPostMapper.map(it, currentUser.id) }
        val currentPosts = state.posts

        val allPosts = if (currentPage == DEFAULT_PAGE) remotePosts
        else currentPosts + remotePosts

        mutex.withLock { currentPage++ }
        state.copy(
            isPaging = false,
            posts = allPosts
        )
    } ?: mutableState.value

    private suspend fun asyncFetchOnboardingUsers() = asyncWithDefault(Unit) {
        when (val result = fetchOnboardingUsersUseCase(currentUser.id)) {
            is Result.Success -> {
                val onBoardingState = fetchStateForSuccessfulOnboardingRequest(result)
                onboardingCommunication.emit(onBoardingState)
            }

            is Result.Error -> {
                val message = result.message ?: defaultErrorMessage
                snackbarDisplay.showSnackbar(Error(message))
            }
        }

    }

    private fun fetchStateForSuccessfulOnboardingRequest(
        result: Result.Success<List<UserInfoDomain>>
    ): OnBoardingUiState {
        val usersDomain = result.data
        val onBoardingState = onboardingCommunication.value()
        return onBoardingState.copy(
            users = usersDomain?.map(userInfoDomainToUserInfoMapper::map) ?: emptyList(),
            shouldShowOnBoarding = usersDomain?.isNotEmpty() == true,
        )
    }

    fun navigateChatScreen() {
        globalNavigationFlowCommunication.emit(NavCommand.Chat)
    }

    private fun navigatePostScreen(postId: Int, shouldShowAddCommentDialog: Boolean = false) {
        navigationScreenCommunication.emit(
            postScreenProvider.postDetailsScreen(postId, shouldShowAddCommentDialog)
        )
    }

    private fun navigateProfileScreen(userId: Int) {
        navigationScreenCommunication.emit(profileScreenProvider.profileScreen(userId))
    }

    private fun doFollowButtonClick(event: HomeScreenEvent.OnFollowButtonClick) {
        screenModelScope.launchSafe {
            if (event.isFollow) subscriptionsManager.cancelSubscription(
                event.user.id,
                onError = { snackbarDisplay.showSnackbar(Error(it)) }
            )
            else subscriptionsManager.createSubscription(
                event.user.id,
                onError = { snackbarDisplay.showSnackbar(Error(it)) }
            )
        }
    }

    private fun doOnboardingFinish() {
        onboardingCommunication.update { currentState ->
            currentState.copy(shouldShowOnBoarding = false)
        }
    }

    override fun onDispose() {
        allDataJob?.cancel()
        super.onDispose()
    }
}
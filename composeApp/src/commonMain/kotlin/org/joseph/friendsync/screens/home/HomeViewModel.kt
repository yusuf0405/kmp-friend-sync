package org.joseph.friendsync.screens.home

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import cafe.adriel.voyager.navigator.Navigator
import kotlinx.coroutines.Job
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import org.joseph.friendsync.common.util.Result
import org.joseph.friendsync.common.util.coroutines.asyncWithDefault
import org.joseph.friendsync.common.util.coroutines.launchSafe
import org.joseph.friendsync.data.repository.defaultErrorMessage
import org.joseph.friendsync.domain.models.PostDomain
import org.joseph.friendsync.domain.models.UserInfoDomain
import org.joseph.friendsync.domain.usecases.onboarding.FetchOnboardingUsersUseCase
import org.joseph.friendsync.domain.usecases.post.FetchRecommendedPostsUseCase
import org.joseph.friendsync.managers.UserDataStore
import org.joseph.friendsync.managers.UserPreferences
import org.joseph.friendsync.mappers.PostDomainToPostMapper
import org.joseph.friendsync.mappers.UserInfoDomainToUserInfoMapper
import org.joseph.friendsync.screens.home.onboarding.OnBoardingUiState
import org.joseph.friendsync.screens.home.onboarding.OnboardingStateCommunication
import org.joseph.friendsync.screens.post_detils.PostScreenDestination
import org.koin.core.component.KoinComponent

private const val DEFAULT_PAGE = 1
private const val DEFAULT_PAGE_SIZE = 5

class HomeViewModel(
    private val userDataStore: UserDataStore,
    private val fetchOnboardingUsersUseCase: FetchOnboardingUsersUseCase,
    private val fetchRecommendedPostsUseCase: FetchRecommendedPostsUseCase,
    private val userInfoDomainToUserInfoMapper: UserInfoDomainToUserInfoMapper,
    private val postDomainToPostMapper: PostDomainToPostMapper,
    private val onboardingCommunication: OnboardingStateCommunication
) : StateScreenModel<HomeUiState>(HomeUiState.Initial), KoinComponent {

    private var currentPage = DEFAULT_PAGE
    private var currentUser: UserPreferences = UserPreferences.unknown

    private val mutex = Mutex()
    private var allDataJob: Job? = null

    val onBoardingUiState: StateFlow<OnBoardingUiState> = onboardingCommunication.observe()

    init {
        allDataJob = startLoadAllData()
    }

    private fun startLoadAllData() = screenModelScope.launchSafe(
        onError = {
            mutableState.tryEmit(HomeUiState.Error(defaultErrorMessage))
        }
    ) {
        mutableState.tryEmit(HomeUiState.Loading)
        awaitAll(
            asyncFetchCurrentUser(),
            asyncFetchPosts(),
            asyncFetchOnboardingUsers(),
        )
    }

    fun onEvent(event: HomeScreenEvent, navigator: Navigator) {
        when (event) {
            is HomeScreenEvent.OnCommentClick -> {}
            is HomeScreenEvent.OnLikeClick -> {}
            is HomeScreenEvent.OnProfileClick -> {}
            is HomeScreenEvent.OnPostClick -> navigator.push(PostScreenDestination(event.postId))
            is HomeScreenEvent.OnStoriesClick -> {}
            is HomeScreenEvent.OnFollowItemClick -> {}
            is HomeScreenEvent.OnFollowButtonClick -> {}
            is HomeScreenEvent.OnBoardingFinish -> doOnboardingFinish()
            is HomeScreenEvent.OnUserClick -> {}
            is HomeScreenEvent.FetchMorePosts -> fetchMorePosts()
            is HomeScreenEvent.RefreshAllData -> refreshAll()
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

        delay(3000)
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
        val onBoardingState = when (val result = fetchOnboardingUsersUseCase(currentUser.id)) {
            is Result.Success -> fetchStateForSuccessfulOnboardingRequest(result)
            is Result.Error -> onboardingCommunication.value().copy(errorMessage = result.message)
        }
        onboardingCommunication.emit(onBoardingState)
    }

    private fun fetchStateForSuccessfulOnboardingRequest(
        result: Result.Success<List<UserInfoDomain>>
    ): OnBoardingUiState {
        val usersDomain = result.data
        val onBoardingState = onboardingCommunication.value()
        return if (usersDomain == null) onBoardingState.copy(
            errorMessage = result.message
        ) else if (usersDomain.isEmpty()) onBoardingState.copy(
            shouldShowOnBoarding = false,
        )
        else onBoardingState.copy(
            users = usersDomain.map(userInfoDomainToUserInfoMapper::map),
            shouldShowOnBoarding = true,
        )
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
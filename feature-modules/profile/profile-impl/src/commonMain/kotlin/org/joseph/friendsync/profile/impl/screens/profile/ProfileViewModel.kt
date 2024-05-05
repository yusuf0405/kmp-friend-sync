package org.joseph.friendsync.profile.impl.screens.profile

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import org.joseph.friendsync.domain.UserDataStore
import org.joseph.friendsync.core.Result
import org.joseph.friendsync.core.extensions.launchSafe
import org.joseph.friendsync.core.extensions.onError
import org.joseph.friendsync.core.FriendSyncViewModel
import org.joseph.friendsync.core.ui.common.communication.BooleanStateFlowCommunication
import org.joseph.friendsync.core.ui.common.communication.NavigationRouteFlowCommunication
import org.joseph.friendsync.core.ui.common.communication.UsersStateFlowCommunication
import org.joseph.friendsync.core.ui.common.communication.navigationParams
import org.joseph.friendsync.domain.markers.users.UserMarksManager
import org.joseph.friendsync.core.ui.snackbar.FriendSyncSnackbar.Error
import org.joseph.friendsync.core.ui.snackbar.SnackbarDisplayer
import org.joseph.friendsync.core.ui.strings.MainResStrings
import org.joseph.friendsync.domain.models.UserDetailDomain
import org.joseph.friendsync.domain.usecases.post.FetchUserPostsUseCase
import org.joseph.friendsync.domain.usecases.subscriptions.HasUserSubscriptionUseCase
import org.joseph.friendsync.domain.usecases.subscriptions.SubscriptionsInteractor
import org.joseph.friendsync.domain.usecases.user.FetchUserByIdUseCase
import org.joseph.friendsync.profile.impl.communication.ProfilePostsStateCommunication
import org.joseph.friendsync.profile.impl.di.ProfileFeatureDependencies
import org.joseph.friendsync.profile.impl.mappers.UserDetailDomainToUserDetailMapper
import org.joseph.friendsync.profile.impl.mappers.toUserInfo
import org.joseph.friendsync.profile.impl.screens.edit.profile.EditProfileFeatureImpl
import org.joseph.friendsync.profile.impl.screens.profile.ProfileUiState.Content
import org.joseph.friendsync.ui.components.mappers.PostDomainToPostMapper
import org.joseph.friendsync.ui.components.mappers.UserInfoToDomainMapper
import org.joseph.friendsync.ui.components.mappers.UserMarkDomainToUiMapper
import org.koin.core.component.KoinComponent

const val UNKNOWN_USER_ID = -1

internal class ProfileViewModel(
    private val userId: Int = UNKNOWN_USER_ID,
    private val featureDependencies: ProfileFeatureDependencies,
    private val fetchUserPostsUseCase: FetchUserPostsUseCase,
    private val postDomainToPostMapper: PostDomainToPostMapper,
    private val userDataStore: UserDataStore,
    private val fetchUserByIdUseCase: FetchUserByIdUseCase,
    private val hasUserSubscriptionUseCase: HasUserSubscriptionUseCase,
    private val subscriptionsInteractor: SubscriptionsInteractor,
    private val userMarksManager: UserMarksManager,
    private val userDetailDomainToUserDetailMapper: UserDetailDomainToUserDetailMapper,
    private val userInfoToDomainMapper: UserInfoToDomainMapper,
    private val snackbarDisplayer: SnackbarDisplayer,
    private val profilePostsStateCommunication: ProfilePostsStateCommunication,
    private val shouldCurrentUserFlowCommunication: BooleanStateFlowCommunication,
    private val hasUserSubscriptionCommunication: BooleanStateFlowCommunication,
    private val usersStateFlowCommunication: UsersStateFlowCommunication,
    private val navigationCommunication: NavigationRouteFlowCommunication,
) : FriendSyncViewModel<ProfileUiState>(ProfileUiState.Initial), KoinComponent {

    val postsUiStateFlow: StateFlow<ProfilePostsUiState> = profilePostsStateCommunication.observe()
    val shouldCurrentUserFlow: StateFlow<Boolean> = shouldCurrentUserFlowCommunication.observe()
    val hasUserSubscriptionFlow: StateFlow<Boolean> = hasUserSubscriptionCommunication.observe()

    private val usersFlow = usersStateFlowCommunication.observe()
    private val defaultErrorMessage = MainResStrings.default_error_message
    private var currentUserId: Int = UNKNOWN_USER_ID

    init {
        viewModelScope.launchSafe(
            onError = { showErrorSnackbar(null) }
        ) {
            mutableState.tryEmit(ProfileUiState.Loading)
            currentUserId = userDataStore.fetchCurrentUser().id
            shouldCurrentUserFlowCommunication.emit(currentUserId == userId)
            fetchUserInfo()
            hasUserSubscriptionUseCase()
            fetchUserPosts()
        }

        fetchUserByIdUseCase.observeUserById(userId)
            .onEach(::processUserDetailDomain)
            .onError { showErrorSnackbar() }
            .launchIn(viewModelScope)


        usersFlow.map { users -> users.map(userInfoToDomainMapper::map) }
            .flatMapLatest(userMarksManager::observeUsersWithMarks)
            .map { it.firstOrNull() }
            .filterNotNull()
            .onEach { hasUserSubscriptionCommunication.emit(it.isSubscribed) }
            .launchIn(viewModelScope)
    }

    fun onEvent(event: ProfileScreenEvent) {
        when (event) {
            is ProfileScreenEvent.OnFollowButtonClick -> doFollowButtonClick(event)
            is ProfileScreenEvent.OnPostClick -> navigatePostScreen(event.postId)
            is ProfileScreenEvent.OnEditProfile -> navigateEditProfile()
        }
    }

    private suspend fun hasUserSubscriptionUseCase() {
        val subscription = hasUserSubscriptionUseCase(currentUserId, userId).data ?: false
        hasUserSubscriptionCommunication.emit(subscription)
    }

    private suspend fun fetchUserInfo() {
        val response = fetchUserByIdUseCase.fetchUserById(userId)
        if (response.isError()) {
            showErrorSnackbar(response.message)
            mutableState.tryEmit(ProfileUiState.Error(response.message ?: defaultErrorMessage))

        }
    }

    private fun processUserDetailDomain(userDomain: UserDetailDomain?) {
        val currentUserUiState = if (userDomain != null) {
            val user = userDetailDomainToUserDetailMapper.map(userDomain)
            usersStateFlowCommunication.emit(listOf(user.toUserInfo()))
            Content(user = user)
        } else {
            showErrorSnackbar(defaultErrorMessage)
            ProfileUiState.Error(defaultErrorMessage)
        }
        mutableState.tryEmit(currentUserUiState)
    }

    private suspend fun fetchUserPosts() {
        profilePostsStateCommunication.emit(ProfilePostsUiState.Loading)
        when (val response = fetchUserPostsUseCase(userId)) {
            is Result.Success -> {
                val posts = response.data?.map(postDomainToPostMapper::map)
                val state = ProfilePostsUiState.Content(posts ?: emptyList())
                profilePostsStateCommunication.emit(state)
            }

            is Result.Error -> {
                profilePostsStateCommunication.emit(
                    ProfilePostsUiState.Error(response.message ?: defaultErrorMessage)
                )
                showErrorSnackbar(response.message)
            }
        }
    }

    private fun doFollowButtonClick(event: ProfileScreenEvent.OnFollowButtonClick) {
        viewModelScope.launchSafe {
            if (event.isFollow) subscriptionsInteractor.cancelSubscription(
                currentUserId,
                event.userId,
            ).apply { if (isError()) showErrorSnackbar(message) }
            else subscriptionsInteractor.createSubscription(
                currentUserId,
                event.userId,
            ).apply { if (isError()) showErrorSnackbar(message) }
        }
    }

    private fun navigatePostScreen(postId: Int, shouldShowAddCommentDialog: Boolean = false) {
        val route = featureDependencies.getPostRoute(postId, shouldShowAddCommentDialog)
        navigationCommunication.emit(navigationParams(route))
    }

    private fun navigateEditProfile() {
        navigationCommunication.emit(navigationParams(EditProfileFeatureImpl.profileRoute))
    }

    private fun showErrorSnackbar(message: String? = null) {
        snackbarDisplayer.showSnackbar(Error(message ?: defaultErrorMessage))
    }
}
package org.joseph.friendsync.profile.impl

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import org.joseph.friendsync.common.user.UserDataStore
import org.joseph.friendsync.common.util.Result
import org.joseph.friendsync.common.util.coroutines.callSafe
import org.joseph.friendsync.common.util.coroutines.launchSafe
import org.joseph.friendsync.core.ui.common.communication.BooleanStateFlowCommunication
import org.joseph.friendsync.core.ui.common.communication.EventSharedFlowCommunication
import org.joseph.friendsync.core.ui.common.communication.NavigationScreenStateFlowCommunication
import org.joseph.friendsync.core.ui.snackbar.FriendSyncSnackbar
import org.joseph.friendsync.core.ui.snackbar.FriendSyncSnackbar.*
import org.joseph.friendsync.core.ui.snackbar.SnackbarDisplay
import org.joseph.friendsync.core.ui.strings.MainResStrings
import org.joseph.friendsync.domain.managers.SubscriptionsManager
import org.joseph.friendsync.domain.usecases.post.FetchUserPostsUseCase
import org.joseph.friendsync.domain.usecases.subscriptions.HasUserSubscriptionUseCase
import org.joseph.friendsync.domain.usecases.subscriptions.SubscriptionsInteractor
import org.joseph.friendsync.domain.usecases.user.FetchUserByIdUseCase
import org.joseph.friendsync.profile.impl.communication.ProfilePostsUiStateCommunication
import org.joseph.friendsync.profile.impl.mappers.UserDetailDomainToUserDetailMapper
import org.joseph.friendsync.profile.impl.models.ProfileTab
import org.joseph.friendsync.profile.impl.tabs.posts.ProfilePostsUiState
import org.joseph.friendsync.ui.components.mappers.PostDomainToPostMapper
import org.joseph.friendsync.ui.components.models.user.UserInfo
import org.koin.core.component.KoinComponent

const val UNKNOWN_USER_ID = -1

class ProfileViewModel(
    private val userId: Int = UNKNOWN_USER_ID,
    private val fetchUserPostsUseCase: FetchUserPostsUseCase,
    private val postDomainToPostMapper: PostDomainToPostMapper,
    private val userDataStore: UserDataStore,
    private val fetchUserByIdUseCase: FetchUserByIdUseCase,
    private val userDetailDomainToUserDetailMapper: UserDetailDomainToUserDetailMapper,
    private val snackbarDisplay: SnackbarDisplay,
    private val profilePostsUiStateCommunication: ProfilePostsUiStateCommunication,
    private val navigationScreenCommunication: NavigationScreenStateFlowCommunication,
    private val shouldCurrentUserFlowCommunication: BooleanStateFlowCommunication,
    private val hasUserSubscriptionCommunication: BooleanStateFlowCommunication,
    private val navigateBackEventFlowCommunication: EventSharedFlowCommunication,
    private val hasUserSubscriptionUseCase: HasUserSubscriptionUseCase,
    private val subscriptionsManager: SubscriptionsManager,
) : StateScreenModel<ProfileUiState>(ProfileUiState.Initial), KoinComponent {

    private val postsUiStateFlow = profilePostsUiStateCommunication.observe()

    val navigationScreenFlow = navigationScreenCommunication.observe()
    val shouldCurrentUserFlow = shouldCurrentUserFlowCommunication.observe()
    val hasUserSubscriptionFlow = hasUserSubscriptionCommunication.observe()
    val navigateBackEventFlow = navigateBackEventFlowCommunication.observe()

    private val defaultErrorMessage = MainResStrings.default_error_message
    private var currentUserId: Int = UNKNOWN_USER_ID

    init {
        screenModelScope.launchSafe(
            onError = { showErrorSnackbar(null) }
        ) {
            mutableState.tryEmit(ProfileUiState.Loading)
            shouldCurrentUserFlowCommunication.emit(false)
            currentUserId = userDataStore.fetchCurrentUser().id
            subscriptionsManager.fetchSubscriptionUserIds()
            fetchUserInfo()
            hasUserSubscriptionUseCase()
            fetchUserPosts()
        }

        subscriptionsManager.subscribeUserIdsFlow.onEach { userIds ->
            hasUserSubscriptionCommunication.emit(userIds.contains(userId))
        }.launchIn(screenModelScope)
    }

    fun onEvent(event: ProfileScreenEvent) {
        when (event) {
            is ProfileScreenEvent.OnNavigateToBack -> navigateToBack()
            is ProfileScreenEvent.OnFollowButtonClick -> doFollowButtonClick(event)
            else -> Unit
        }
    }

    private suspend fun hasUserSubscriptionUseCase() {
        val subscription = hasUserSubscriptionUseCase(currentUserId, userId).data ?: false
        hasUserSubscriptionCommunication.emit(subscription)
    }

    private suspend fun fetchUserInfo() {
        when (val response = fetchUserByIdUseCase(userId)) {
            is Result.Success -> {
                val userDomain = response.data
                val profileUiState = if (userDomain != null) {
                    ProfileUiState.Content(
                        userDetail = userDetailDomainToUserDetailMapper.map(userDomain),
                        tabs = ProfileTab.fetchProfileTabs(postsUiStateFlow),
                    )

                } else {
                    showErrorSnackbar(response.message)
                    ProfileUiState.Error(response.message ?: defaultErrorMessage)
                }
                mutableState.tryEmit(profileUiState)
            }

            is Result.Error -> {
                mutableState.tryEmit(
                    ProfileUiState.Error(response.message ?: defaultErrorMessage)
                )
                showErrorSnackbar(response.message)
            }
        }
    }

    private suspend fun fetchUserPosts() {
        profilePostsUiStateCommunication.emit(ProfilePostsUiState.Loading)
        delay(2000)
        when (val response = fetchUserPostsUseCase(userId)) {
            is Result.Success -> {
                val posts = response.data?.map { postDomainToPostMapper.map(it, userId) }
                val state = ProfilePostsUiState.Content(posts ?: emptyList())
                profilePostsUiStateCommunication.emit(state)
            }

            is Result.Error -> {
                profilePostsUiStateCommunication.emit(
                    ProfilePostsUiState.Error(response.message ?: defaultErrorMessage)
                )
                showErrorSnackbar(response.message)
            }
        }
    }

    private fun doFollowButtonClick(event: ProfileScreenEvent.OnFollowButtonClick) {
        screenModelScope.launchSafe {
            if (event.isFollow) subscriptionsManager.cancelSubscription(
                event.userId,
                onError = { snackbarDisplay.showSnackbar(Error(it)) }
            )
            else subscriptionsManager.createSubscription(
                event.userId,
                onError = { snackbarDisplay.showSnackbar(Error(it)) }
            )
        }
    }

    private fun navigateToBack() {
        navigateBackEventFlowCommunication.emit(Unit)
    }

    private fun showErrorSnackbar(message: String?) {
        snackbarDisplay.showSnackbar(Error(message ?: defaultErrorMessage))
    }
}
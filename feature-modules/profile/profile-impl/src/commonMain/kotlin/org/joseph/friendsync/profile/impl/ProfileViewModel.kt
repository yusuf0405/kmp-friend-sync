package org.joseph.friendsync.profile.impl

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.joseph.friendsync.common.user.UserDataStore
import org.joseph.friendsync.common.util.Result
import org.joseph.friendsync.common.util.coroutines.asyncWithDefault
import org.joseph.friendsync.common.util.coroutines.launchSafe
import org.joseph.friendsync.core.ui.common.communication.NavigationScreenStateFlowCommunication
import org.joseph.friendsync.core.ui.snackbar.FriendSyncSnackbar
import org.joseph.friendsync.core.ui.snackbar.SnackbarDisplay
import org.joseph.friendsync.core.ui.strings.MainResStrings
import org.joseph.friendsync.domain.usecases.post.FetchUserPostsUseCase
import org.joseph.friendsync.domain.usecases.user.FetchUserByIdUseCase
import org.joseph.friendsync.mappers.PostDomainToPostMapper
import org.joseph.friendsync.profile.impl.communication.ProfilePostsUiStateCommunication
import org.joseph.friendsync.profile.impl.edit_profile.EditProfileScreenDestination
import org.joseph.friendsync.profile.impl.manager.CurrentUserManager
import org.joseph.friendsync.profile.impl.mappers.UserDetailDomainToUserDetailMapper
import org.joseph.friendsync.profile.impl.models.ProfileTab
import org.joseph.friendsync.profile.impl.tabs.posts.ProfilePostsUiState
import org.koin.core.component.KoinComponent

const val UNKNOWN_USER_ID = -1

class ProfileViewModel(
    private val id: Int = UNKNOWN_USER_ID,
    private val userDataStore: UserDataStore,
    private val fetchUserPostsUseCase: FetchUserPostsUseCase,
    private val postDomainToPostMapper: PostDomainToPostMapper,
    private val currentUserManager: CurrentUserManager,
    private val fetchUserByIdUseCase: FetchUserByIdUseCase,
    private val userDetailDomainToUserDetailMapper: UserDetailDomainToUserDetailMapper,
    private val snackbarDisplay: SnackbarDisplay,
    private val profilePostsUiStateCommunication: ProfilePostsUiStateCommunication,
    private val navigationScreenCommunication: NavigationScreenStateFlowCommunication,
) : StateScreenModel<ProfileUiState>(ProfileUiState.Initial), KoinComponent {

    private val postsUiStateFlow = profilePostsUiStateCommunication.observe()

    val navigationScreenFlow = navigationScreenCommunication.observe()

    private val defaultErrorMessage = MainResStrings.default_error_message

    private var isCurrentUser = false

    private val currentUserFlow = currentUserManager.fetchCurrentUserFlow().onEach { user ->
        val profileUiState = ProfileUiState.Content(
            userDetail = user,
            tabs = ProfileTab.fetchProfileTabs(postsUiStateFlow),
            isCurrentUser = isCurrentUser
        )
        mutableState.tryEmit(profileUiState)
    }

    init {
        screenModelScope.launchSafe(
            onError = { showErrorSnackbar(null) }
        ) {
            mutableState.tryEmit(ProfileUiState.Loading)
            val currentUserId = fetchCurrentUserId().await()
            val userId = if (id == UNKNOWN_USER_ID) currentUserId else id
            isCurrentUser = userId == currentUserId
            if (isCurrentUser) startFetchCurrentUser(currentUserId)
            else fetchUserInfo(id)
            fetchUserPosts(userId)
        }
    }

    fun onEvent(event: ProfileScreenEvent) {
        when (event) {
            is ProfileScreenEvent.OnEditProfile -> navigateEditProfileScreen()
            is ProfileScreenEvent.OnEditBackgroundImage -> {
                val message = MainResStrings.function_is_temporarily_unavailable
                snackbarDisplay.showSnackbar(FriendSyncSnackbar.Sample(message))
            }
        }
    }

    private suspend fun startFetchCurrentUser(currentUserId: Int) {
        currentUserManager.startFetchCurrentUser(currentUserId)
        currentUserFlow.launchIn(screenModelScope)
    }

    private suspend fun fetchCurrentUserId() = asyncWithDefault(UNKNOWN_USER_ID) {
        userDataStore.fetchCurrentUser().id
    }

    private suspend fun fetchUserInfo(userId: Int) {
        when (val response = fetchUserByIdUseCase(userId)) {
            is Result.Success -> {
                val userDomain = response.data
                val profileUiState = if (userDomain != null) {
                    ProfileUiState.Content(
                        userDetail = userDetailDomainToUserDetailMapper.map(userDomain),
                        tabs = ProfileTab.fetchProfileTabs(postsUiStateFlow),
                        isCurrentUser = isCurrentUser
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

    private suspend fun fetchUserPosts(userId: Int) {
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

    private fun navigateEditProfileScreen() {
        val state = mutableState.value as? ProfileUiState.Content ?: return
        navigationScreenCommunication.emit(EditProfileScreenDestination(state.userDetail.id))
    }

    private fun showErrorSnackbar(message: String?) {
        snackbarDisplay.showSnackbar(FriendSyncSnackbar.Error(message ?: defaultErrorMessage))
    }
}
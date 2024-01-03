package org.joseph.friendsync.profile.impl.screens.current.user

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import org.joseph.friendsync.common.user.UserDataStore
import org.joseph.friendsync.common.util.coroutines.launchSafe
import org.joseph.friendsync.core.ui.common.managers.post.PostMarksManager
import org.joseph.friendsync.core.ui.common.observers.post.PostsObserver
import org.joseph.friendsync.core.ui.common.usecases.post.like.PostLikeOrDislikeInteractor
import org.joseph.friendsync.core.ui.snackbar.FriendSyncSnackbar
import org.joseph.friendsync.core.ui.snackbar.SnackbarDisplay
import org.joseph.friendsync.core.ui.strings.MainResStrings
import org.joseph.friendsync.domain.usecases.current.user.FetchCurrentUserFlowUseCase
import org.joseph.friendsync.domain.usecases.current.user.FetchCurrentUserUseCase
import org.joseph.friendsync.domain.usecases.post.FetchUserPostsUseCase
import org.joseph.friendsync.profile.impl.communication.CurrentUserPostsStateCommunication
import org.joseph.friendsync.profile.impl.navigation.ProfileNavigationFlowCommunication
import org.joseph.friendsync.profile.impl.navigation.ProfileScreenRouter
import org.joseph.friendsync.profile.impl.screens.current.user.tabs.posts.CurrentUserPostsUiState.Content
import org.joseph.friendsync.profile.impl.screens.current.user.tabs.posts.CurrentUserPostsUiState.Error
import org.joseph.friendsync.profile.impl.screens.current.user.tabs.posts.CurrentUserPostsUiState.Loading
import org.joseph.friendsync.profile.impl.screens.profile.UNKNOWN_USER_ID
import org.joseph.friendsync.ui.components.mappers.CurrentUserDomainToCurrentUserMapper
import org.joseph.friendsync.ui.components.models.user.CurrentUser
import org.koin.core.component.KoinComponent

internal class CurrentUserViewModel(
    private val fetchCurrentUserFlowUseCase: FetchCurrentUserFlowUseCase,
    private val fetchCurrentUserUseCase: FetchCurrentUserUseCase,
    private val fetchUserPostsUseCase: FetchUserPostsUseCase,
    private val postLikeOrDislikeInteractor: PostLikeOrDislikeInteractor,
    private val postsMarksManager: PostMarksManager,
    private val userDataStore: UserDataStore,
    private val snackbarDisplay: SnackbarDisplay,
    private val postsStateCommunication: CurrentUserPostsStateCommunication,
    private val navigationRouterFlowCommunication: ProfileNavigationFlowCommunication,
    private val currentUserMapper: CurrentUserDomainToCurrentUserMapper,
) : StateScreenModel<CurrentUserUiState>(CurrentUserUiState.Initial), KoinComponent {

    val postsUiStateFlow = postsStateCommunication.observe()
    val navigationRouterFlow = navigationRouterFlowCommunication.observe()

    private val defaultErrorMessage = MainResStrings.default_error_message
    private var currentUserId: Int = UNKNOWN_USER_ID

    init {
        screenModelScope.launchSafe(
            onError = {
                postsStateCommunication.emit(Error(defaultErrorMessage))
                showErrorSnackbar()
            }
        ) {
            mutableState.tryEmit(CurrentUserUiState.Loading)
            currentUserId = userDataStore.fetchCurrentUser().id
            fetchUserPostsUseCase.invoke(currentUserId)
            fetchCurrentUserUseCase.fetchCurrentUser()

            postsMarksManager.observePostWithMarks(PostsObserver.ForUser(currentUserId))
                .onStart { postsStateCommunication.emit(Loading) }
                .onEach { posts -> postsStateCommunication.emit(Content(posts)) }
                .launchIn(this)

            fetchCurrentUserFlowUseCase.fetchCurrentUserFlow()
                .map { currentUserMapper.map(it ?: return@map CurrentUser.unknown) }
                .onEach { user ->
                    val currentUserUiState = CurrentUserUiState.Content(user = user)
                    mutableState.tryEmit(currentUserUiState)
                }.launchIn(this)
        }
    }

    fun onEvent(event: CurrentUserEvent) {
        when (event) {
            is CurrentUserEvent.OnEditProfile -> navigateEditProfileScreen()
            is CurrentUserEvent.OnEditBackgroundImage -> {
                val message = MainResStrings.function_is_temporarily_unavailable
                snackbarDisplay.showSnackbar(FriendSyncSnackbar.Sample(message))
            }

            is CurrentUserEvent.OnCommentClick -> navigatePostScreen(event.postId, true)
            is CurrentUserEvent.OnLikeClick -> doLikeButtonClick(event)
            is CurrentUserEvent.OnProfileClick -> navigateProfileScreen(event.userId)
            is CurrentUserEvent.OnPostClick -> navigatePostScreen(event.postId)
        }
    }

    private fun doLikeButtonClick(event: CurrentUserEvent.OnLikeClick) {
        screenModelScope.launchSafe {
            if (event.isLiked) postLikeOrDislikeInteractor.unlikePost(
                currentUserId,
                event.postId
            )
            else postLikeOrDislikeInteractor.likePost(
                currentUserId,
                event.postId
            )
        }
    }


    private fun navigatePostScreen(postId: Int, shouldShowAddCommentDialog: Boolean = false) {
        navigationRouterFlowCommunication.emit(
            ProfileScreenRouter.PostDetails(postId, shouldShowAddCommentDialog)
        )
    }

    private fun navigateProfileScreen(userId: Int) {
        navigationRouterFlowCommunication.emit(ProfileScreenRouter.UserProfile(userId))
    }


    private fun navigateEditProfileScreen() {
        navigationRouterFlowCommunication.emit(ProfileScreenRouter.EditProfile)
    }

    private fun showErrorSnackbar(message: String? = null) {
        snackbarDisplay.showSnackbar(FriendSyncSnackbar.Error(message ?: defaultErrorMessage))
    }
}
package org.joseph.friendsync.profile.impl.screens.current.user

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import org.joseph.friendsync.core.FriendSyncViewModel
import org.joseph.friendsync.core.extensions.launchSafe
import org.joseph.friendsync.core.extensions.routeWithParam
import org.joseph.friendsync.core.ui.common.communication.NavigationRouteFlowCommunication
import org.joseph.friendsync.core.ui.common.communication.navigationParams
import org.joseph.friendsync.core.ui.snackbar.FriendSyncSnackbar
import org.joseph.friendsync.core.ui.snackbar.SnackbarDisplayer
import org.joseph.friendsync.core.ui.strings.MainResStrings
import org.joseph.friendsync.domain.UserDataStore
import org.joseph.friendsync.domain.markers.post.PostMarksManager
import org.joseph.friendsync.domain.post.PostsObserveType
import org.joseph.friendsync.domain.usecases.current.user.FetchCurrentUserFlowUseCase
import org.joseph.friendsync.domain.usecases.current.user.FetchCurrentUserUseCase
import org.joseph.friendsync.domain.usecases.post.FetchUserPostsUseCase
import org.joseph.friendsync.domain.usecases.post.PostLikeOrDislikeInteractor
import org.joseph.friendsync.profile.impl.communication.CurrentUserPostsStateCommunication
import org.joseph.friendsync.profile.impl.di.ProfileFeatureDependencies
import org.joseph.friendsync.profile.impl.screens.current.user.tabs.posts.CurrentUserPostsUiState.Content
import org.joseph.friendsync.profile.impl.screens.current.user.tabs.posts.CurrentUserPostsUiState.Error
import org.joseph.friendsync.profile.impl.screens.current.user.tabs.posts.CurrentUserPostsUiState.Loading
import org.joseph.friendsync.profile.impl.screens.edit.profile.EditProfileFeatureImpl
import org.joseph.friendsync.profile.impl.screens.profile.ProfileFeatureImpl
import org.joseph.friendsync.profile.impl.screens.profile.UNKNOWN_USER_ID
import org.joseph.friendsync.ui.components.mappers.CurrentUserDomainToCurrentUserMapper
import org.joseph.friendsync.ui.components.mappers.PostMarkDomainToUiMapper
import org.joseph.friendsync.ui.components.models.user.CurrentUser
import org.koin.core.component.KoinComponent

internal class CurrentUserViewModel(
    private val featureDependencies: ProfileFeatureDependencies,
    private val fetchCurrentUserFlowUseCase: FetchCurrentUserFlowUseCase,
    private val fetchCurrentUserUseCase: FetchCurrentUserUseCase,
    private val fetchUserPostsUseCase: FetchUserPostsUseCase,
    private val postLikeOrDislikeInteractor: PostLikeOrDislikeInteractor,
    private val postsMarksManager: PostMarksManager,
    private val userDataStore: UserDataStore,
    private val snackbarDisplayer: SnackbarDisplayer,
    private val postsStateCommunication: CurrentUserPostsStateCommunication,
    private val navigationCommunication: NavigationRouteFlowCommunication,
    private val currentUserMapper: CurrentUserDomainToCurrentUserMapper,
    private val postMarkDomainToUiMapper: PostMarkDomainToUiMapper,
) : FriendSyncViewModel<CurrentUserUiState>(CurrentUserUiState.Initial), KoinComponent {

    val postsUiStateFlow = postsStateCommunication.observe()

    private val defaultErrorMessage = MainResStrings.default_error_message
    private var currentUserId: Int = UNKNOWN_USER_ID

    init {
        viewModelScope.launchSafe(
            onError = { postsStateCommunication.emit(Error(defaultErrorMessage)) }
        ) {
            mutableState.tryEmit(CurrentUserUiState.Loading)
            currentUserId = userDataStore.fetchCurrentUser().id
            fetchUserPostsUseCase.invoke(currentUserId)
            fetchCurrentUserUseCase.fetchCurrentUser()

            postsMarksManager.observePostWithMarks(PostsObserveType.ForUser(currentUserId))
                .map { posts -> posts.map(postMarkDomainToUiMapper::map) }
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
                snackbarDisplayer.showSnackbar(FriendSyncSnackbar.Sample(message))
            }

            is CurrentUserEvent.OnCommentClick -> navigatePostScreen(event.postId, true)
            is CurrentUserEvent.OnLikeClick -> doLikeButtonClick(event)
            is CurrentUserEvent.OnProfileClick -> navigateProfileScreen(event.userId)
            is CurrentUserEvent.OnPostClick -> navigatePostScreen(event.postId)
        }
    }

    private fun doLikeButtonClick(event: CurrentUserEvent.OnLikeClick) {
        viewModelScope.launchSafe {
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
        val route = featureDependencies.getPostRoute(postId, shouldShowAddCommentDialog)
        navigationCommunication.emit(navigationParams(route))
    }

    private fun navigateProfileScreen(userId: Int) {
        val route = ProfileFeatureImpl.profileRoute.routeWithParam(userId)
        navigationCommunication.emit(navigationParams(route))
    }

    private fun navigateEditProfileScreen() {
        navigationCommunication.emit(navigationParams(EditProfileFeatureImpl.profileRoute))
    }
}
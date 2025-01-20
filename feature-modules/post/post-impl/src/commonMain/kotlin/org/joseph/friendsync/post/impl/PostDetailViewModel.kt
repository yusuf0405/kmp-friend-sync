package org.joseph.friendsync.post.impl

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import org.joseph.friendsync.core.FriendSyncViewModel
import org.joseph.friendsync.core.extensions.asyncWithDefault
import org.joseph.friendsync.core.extensions.launchSafe
import org.joseph.friendsync.core.extensions.onError
import org.joseph.friendsync.core.onFailure
import org.joseph.friendsync.core.ui.common.communication.NavigationRouteFlowCommunication
import org.joseph.friendsync.core.ui.common.communication.navigationParams
import org.joseph.friendsync.core.ui.snackbar.FriendSyncSnackbar
import org.joseph.friendsync.core.ui.snackbar.SnackbarDisplayer
import kmp_friend_sync.core_ui.generated.resources.Res
import kmp_friend_sync.core_ui.generated.resources.default_error_message
import kotlinx.coroutines.flow.catch
import org.jetbrains.compose.resources.getString
import org.joseph.friendsync.domain.UserDataStore
import org.joseph.friendsync.domain.markers.post.PostMarksManager
import org.joseph.friendsync.domain.models.AddCommentParams
import org.joseph.friendsync.domain.models.UserPreferences
import org.joseph.friendsync.domain.post.PostsObserveType
import org.joseph.friendsync.domain.usecases.comments.AddCommentToPostUseCase
import org.joseph.friendsync.domain.usecases.comments.DeleteCommentByIdUseCase
import org.joseph.friendsync.domain.usecases.comments.EditCommentUseCase
import org.joseph.friendsync.domain.usecases.comments.FetchPostCommentsInteractor
import org.joseph.friendsync.domain.usecases.post.FetchPostByIdUseCase
import org.joseph.friendsync.domain.usecases.post.PostLikeOrDislikeInteractor
import org.joseph.friendsync.post.impl.comment.CommentsStateStateFlowCommunication
import org.joseph.friendsync.post.impl.comment.CommentsUiState
import org.joseph.friendsync.post.impl.di.PostFeatureDependencies
import org.joseph.friendsync.ui.components.mappers.CommentDomainToCommentMapper
import org.joseph.friendsync.ui.components.mappers.PostMarkDomainToUiMapper
import org.koin.core.component.KoinComponent

internal class PostDetailViewModel(
    private val postId: Int,
    private val shouldShowAddCommentDialog: Boolean,
    private val postFeatureDependencies: PostFeatureDependencies,
    private val userDataStore: UserDataStore,
    private val fetchPostByIdUseCase: FetchPostByIdUseCase,
    private val fetchPostCommentsInteractor: FetchPostCommentsInteractor,
    private val addCommentToPostUseCase: AddCommentToPostUseCase,
    private val editCommentUseCase: EditCommentUseCase,
    private val deleteCommentByIdUseCase: DeleteCommentByIdUseCase,
    private val postLikeOrDislikeInteractor: PostLikeOrDislikeInteractor,
    private val postMarksManager: PostMarksManager,
    private val commentDomainToCommentMapper: CommentDomainToCommentMapper,
    private val postMarkDomainToUiMapper: PostMarkDomainToUiMapper,
    private val commentsStateCommunication: CommentsStateStateFlowCommunication,
    private val navigationCommunication: NavigationRouteFlowCommunication,
    private val snackbarDisplayer: SnackbarDisplayer,
) : FriendSyncViewModel<PostDetailUiState>(PostDetailUiState.Initial), KoinComponent {

    val commentsUiState: StateFlow<CommentsUiState> = commentsStateCommunication.observe()

    private var currentUser = UserPreferences.unknown
    private var defaultErrorMessage = Res.string.default_error_message

    private var postDataJob: Job? = null
    private var commentsDataJob: Job? = null

    init {
        viewModelScope.launchSafe {
            currentUser = awaitFetchCurrentUser().await()
            postDataJob = fetchPostById()
            commentsDataJob = fetchPostComments()
        }

        postMarksManager.observePostWithMarks(PostsObserveType.Post(postId))
            .map { posts -> posts.map(postMarkDomainToUiMapper::map) }
            .onEach { postMarks ->
                val postMark = postMarks.firstOrNull()
                val state = if (postMark != null) PostDetailUiState.Content(postMark)
                else PostDetailUiState.Error(getString(defaultErrorMessage))
                mutableState.tryEmit(state)
            }.launchIn(viewModelScope)

        fetchPostCommentsInteractor.observeAllPostComments(postId).onEach { commentsDomain ->
            commentsStateCommunication.emit(
                CommentsUiState.Content(
                    comments = commentsDomain.map(commentDomainToCommentMapper::map),
                    shouldShowAddCommentDialog = shouldShowAddCommentDialog
                )
            )
        }.catch {
            commentsStateCommunication.emit(CommentsUiState.Error(getString(defaultErrorMessage)))
        }.launchIn(viewModelScope)
    }

    private fun refreshPostData() {
        postDataJob?.cancel()
        postDataJob = fetchPostById()
    }

    private fun refreshCommentsData() {
        commentsDataJob?.cancel()
        commentsDataJob = fetchPostComments()
    }

    fun onEvent(event: PostDetailEvent) {
        when (event) {
            is PostDetailEvent.RefreshPostData -> refreshPostData()
            is PostDetailEvent.RefreshCommentsData -> refreshCommentsData()
            is PostDetailEvent.OnProfileClick -> navigateProfileScreen(event.userId)
            is PostDetailEvent.OnAddCommentClick -> doAddCommentClick()
            is PostDetailEvent.OnEditCommentValueChange -> doCommentMoreIconClick(event)
            is PostDetailEvent.OnNewCommentValueChange -> doOnCommentValueChange(event)
            is PostDetailEvent.OnEditCommentClick -> doEditCommentClick()
            is PostDetailEvent.OnAddDialogChange -> doAddDialogChange(event)
            is PostDetailEvent.OnEditDialogChange -> doEditDialogChange(event)
            is PostDetailEvent.OnDeleteCommentClick -> doDeleteCommentClick(event)
            is PostDetailEvent.OnLikeClick -> doLikeButtonClick(event)
        }
    }

    private suspend fun awaitFetchCurrentUser() = asyncWithDefault(UserPreferences.unknown) {
        userDataStore.fetchCurrentUser()
    }

    private fun fetchPostById(): Job = viewModelScope.launchSafe {
        mutableState.tryEmit(PostDetailUiState.Loading)
        val response = fetchPostByIdUseCase(postId)
        if (response.isError()) {
            mutableState.tryEmit(
                PostDetailUiState.Error(
                    response.message ?: getString(
                        defaultErrorMessage
                    )
                )
            )
        }
    }

    private fun fetchPostComments() = viewModelScope.launchSafe(
        onError = {
            viewModelScope.launchSafe {
                commentsStateCommunication.emit(CommentsUiState.Error(getString(defaultErrorMessage)))
            }
        }
    ) {
        commentsStateCommunication.emit(CommentsUiState.Loading)
        fetchPostCommentsInteractor.fetchPostComments(postId)
    }

    private fun doLikeButtonClick(event: PostDetailEvent.OnLikeClick) {
        viewModelScope.launchSafe(onError = { handleError() }) {
            if (event.isLiked) {
                postLikeOrDislikeInteractor.unlikePost(
                    currentUser.id,
                    event.postId
                ).onFailure { handleError() }
            } else {
                postLikeOrDislikeInteractor.likePost(
                    currentUser.id,
                    event.postId
                ).onFailure { handleError() }
            }
        }
    }

    private fun doCommentMoreIconClick(event: PostDetailEvent.OnEditCommentValueChange) {
        commentsStateCommunication.update { currentState ->
            val state = currentState as? CommentsUiState.Content ?: return@update currentState
            state.copy(editCommentValue = event.value)
        }
    }

    private fun doEditCommentClick() {
        val state = commentsUiState.value as? CommentsUiState.Content ?: return
        viewModelScope.launchSafe(onError = { handleError() }) {
            editCommentUseCase(
                commentId = state.editComment?.id ?: return@launchSafe,
                commentText = state.editCommentValue
            )
        }
    }

    private fun doAddCommentClick() {
        val state = commentsUiState.value as? CommentsUiState.Content ?: return
        viewModelScope.launchSafe(onError = { handleError() }) {
            val params = AddCommentParams(
                userId = currentUser.id,
                postId = postId,
                commentText = state.newCommentValue
            )
            addCommentToPostUseCase(params)
        }
        commentsStateCommunication.update { state.copy(newCommentValue = String()) }
    }

    private fun doDeleteCommentClick(event: PostDetailEvent.OnDeleteCommentClick) {
        viewModelScope.launchSafe(onError = { handleError() }) {
            deleteCommentByIdUseCase(postId, event.commentId)
        }
    }

    private fun navigateProfileScreen(userId: Int) {
        navigationCommunication.emit(navigationParams(postFeatureDependencies.getProfileRoute(userId)))
    }

    private fun doOnCommentValueChange(event: PostDetailEvent.OnNewCommentValueChange) {
        commentsStateCommunication.update { currentState ->
            val state = currentState as? CommentsUiState.Content ?: return@update currentState
            state.copy(newCommentValue = event.value)
        }
    }

    private fun doEditDialogChange(event: PostDetailEvent.OnEditDialogChange) {
        val state = commentsUiState.value as? CommentsUiState.Content ?: return
        commentsStateCommunication.emit(
            state.copy(
                shouldShowEditCommentDialog = event.isShow,
                editComment = event.comment,
                editCommentValue = event.comment?.comment ?: String()
            )
        )
    }

    private fun doAddDialogChange(event: PostDetailEvent.OnAddDialogChange) {
        val state = commentsUiState.value as? CommentsUiState.Content ?: return
        commentsStateCommunication.emit(
            state.copy(shouldShowAddCommentDialog = event.isShow)
        )
    }

    private fun handleError(message: String? = null) {
        viewModelScope.launchSafe {
            val notNullMessage = message ?: getString(defaultErrorMessage)
            snackbarDisplayer.showSnackbar(FriendSyncSnackbar.Error(notNullMessage))
        }
    }

    override fun onCleared() {
        commentsDataJob?.cancel()
        postDataJob?.cancel()
        super.onCleared()
    }
}
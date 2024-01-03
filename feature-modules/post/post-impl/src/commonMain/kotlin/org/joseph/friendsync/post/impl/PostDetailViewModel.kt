package org.joseph.friendsync.post.impl

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.joseph.friendsync.common.user.UserDataStore
import org.joseph.friendsync.common.user.UserPreferences
import org.joseph.friendsync.common.util.coroutines.asyncWithDefault
import org.joseph.friendsync.common.util.coroutines.launchSafe
import org.joseph.friendsync.common.util.coroutines.onError
import org.joseph.friendsync.core.ui.common.managers.post.PostMarksManager
import org.joseph.friendsync.core.ui.common.observers.post.PostsObserver
import org.joseph.friendsync.core.ui.common.usecases.post.like.PostLikeOrDislikeInteractor
import org.joseph.friendsync.core.ui.snackbar.FriendSyncSnackbar
import org.joseph.friendsync.core.ui.snackbar.SnackbarDisplay
import org.joseph.friendsync.core.ui.strings.MainResStrings
import org.joseph.friendsync.domain.usecases.comments.AddCommentToPostUseCase
import org.joseph.friendsync.domain.usecases.comments.DeleteCommentByIdUseCase
import org.joseph.friendsync.domain.usecases.comments.EditCommentUseCase
import org.joseph.friendsync.domain.usecases.comments.FetchPostCommentsUseCase
import org.joseph.friendsync.domain.usecases.post.FetchPostByIdUseCase
import org.joseph.friendsync.post.impl.comment.CommentsStateStateFlowCommunication
import org.joseph.friendsync.post.impl.comment.CommentsUiState
import org.joseph.friendsync.post.impl.navigation.PostNavigationFlowCommunication
import org.joseph.friendsync.post.impl.navigation.PostScreenRouter
import org.joseph.friendsync.ui.components.mappers.CommentDomainToCommentMapper
import org.koin.core.component.KoinComponent

internal class PostDetailViewModel(
    private val postId: Int,
    private val shouldShowAddCommentDialog: Boolean,
    private val userDataStore: UserDataStore,
    private val fetchPostByIdUseCase: FetchPostByIdUseCase,
    private val fetchPostCommentsUseCase: FetchPostCommentsUseCase,
    private val addCommentToPostUseCase: AddCommentToPostUseCase,
    private val editCommentUseCase: EditCommentUseCase,
    private val deleteCommentByIdUseCase: DeleteCommentByIdUseCase,
    private val postLikeOrDislikeInteractor: PostLikeOrDislikeInteractor,
    private val postMarksManager: PostMarksManager,
    private val commentDomainToCommentMapper: CommentDomainToCommentMapper,
    private val commentsStateCommunication: CommentsStateStateFlowCommunication,
    private val navigationRouterFlowCommunication: PostNavigationFlowCommunication,
    private val snackbarDisplay: SnackbarDisplay,
) : StateScreenModel<PostDetailUiState>(PostDetailUiState.Initial), KoinComponent {

    val commentsUiState: StateFlow<CommentsUiState> = commentsStateCommunication.observe()
    val navigationRouterFlow = navigationRouterFlowCommunication.observe()

    private var currentUser = UserPreferences.unknown

    private var defaultErrorMessage = MainResStrings.default_error_message

    private var postDataJob: Job? = null
    private var commentsDataJob: Job? = null

    init {
        screenModelScope.launchSafe {
            currentUser = awaitFetchCurrentUser().await()
            postDataJob = fetchPostById()
            commentsDataJob = fetchPostComments()
        }

        postMarksManager.observePostWithMarks(PostsObserver.Post(postId)).onEach { postMarks ->
            val postMark = postMarks.firstOrNull()
            val state = if (postMark != null) PostDetailUiState.Content(postMark)
            else PostDetailUiState.Error(defaultErrorMessage)
            mutableState.tryEmit(state)
        }.launchIn(screenModelScope)

        fetchPostCommentsUseCase.observeAllPostComments(postId).onEach { commentsDomain ->
            commentsStateCommunication.emit(
                CommentsUiState.Content(
                    comments = commentsDomain.map(commentDomainToCommentMapper::map),
                    shouldShowAddCommentDialog = shouldShowAddCommentDialog
                )
            )
        }.onError { commentsStateCommunication.emit(CommentsUiState.Error(defaultErrorMessage)) }
            .launchIn(screenModelScope)
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

    private fun fetchPostById() = screenModelScope.launchSafe {
        mutableState.tryEmit(PostDetailUiState.Loading)
        val response = fetchPostByIdUseCase(postId)
        if (response.isError()) {
            mutableState.tryEmit(PostDetailUiState.Error(response.message ?: defaultErrorMessage))
        }
    }

    private fun fetchPostComments() = screenModelScope.launchSafe {
        commentsStateCommunication.emit(CommentsUiState.Loading)
        val response = fetchPostCommentsUseCase(postId)
        if (response.isError()) commentsStateCommunication.emit(
            CommentsUiState.Error(response.message ?: defaultErrorMessage)
        )
    }

    private fun doLikeButtonClick(event: PostDetailEvent.OnLikeClick) {
        screenModelScope.launchSafe {
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

    private fun doCommentMoreIconClick(event: PostDetailEvent.OnEditCommentValueChange) {
        commentsStateCommunication.update { currentState ->
            val state = currentState as? CommentsUiState.Content ?: return@update currentState
            state.copy(editCommentValue = event.value)
        }
    }

    private fun doEditCommentClick() {
        val state = commentsUiState.value as? CommentsUiState.Content ?: return
        screenModelScope.launchSafe {
            val result = editCommentUseCase(
                commentId = state.editComment?.id ?: return@launchSafe,
                commentText = state.editCommentValue
            )
            if (result.isError()) handleError(result.message)
        }
    }

    private fun doAddCommentClick() {
        val state = commentsUiState.value as? CommentsUiState.Content ?: return
        screenModelScope.launchSafe {
            val result = addCommentToPostUseCase(
                userId = currentUser.id,
                postId = postId,
                commentText = state.newCommentValue
            )
            if (result.isError()) handleError(result.message)
        }
        commentsStateCommunication.update { state.copy(newCommentValue = String()) }
    }

    private fun doDeleteCommentClick(event: PostDetailEvent.OnDeleteCommentClick) {
        screenModelScope.launchSafe {
            val result = deleteCommentByIdUseCase(postId, event.commentId)
            if (result.isError()) handleError(result.message)
        }
    }

    private fun navigateProfileScreen(userId: Int) {
        navigationRouterFlowCommunication.emit(PostScreenRouter.UserProfile(userId))
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

    private fun handleError(message: String?) {
        val errorMessage = message ?: defaultErrorMessage
        snackbarDisplay.showSnackbar(FriendSyncSnackbar.Error(errorMessage))
    }

    override fun onDispose() {
        commentsDataJob?.cancel()
        postDataJob?.cancel()
        super.onDispose()
    }
}
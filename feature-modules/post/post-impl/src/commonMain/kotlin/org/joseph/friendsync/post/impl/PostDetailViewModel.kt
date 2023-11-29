package org.joseph.friendsync.post.impl

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import org.joseph.friendsync.common.user.UserDataStore
import org.joseph.friendsync.common.user.UserPreferences
import org.joseph.friendsync.common.util.Result
import org.joseph.friendsync.common.util.coroutines.asyncWithDefault
import org.joseph.friendsync.common.util.coroutines.launchSafe
import org.joseph.friendsync.core.ui.strings.MainResStrings
import org.joseph.friendsync.domain.models.CommentDomain
import org.joseph.friendsync.domain.models.PostDomain
import org.joseph.friendsync.domain.usecases.comments.AddCommentToPostUseCase
import org.joseph.friendsync.domain.usecases.comments.DeleteCommentByIdUseCase
import org.joseph.friendsync.domain.usecases.comments.EditCommentUseCase
import org.joseph.friendsync.domain.usecases.comments.FetchPostCommentsUseCase
import org.joseph.friendsync.domain.usecases.post.FetchPostByIdUseCase
import org.joseph.friendsync.mappers.CommentDomainToCommentMapper
import org.joseph.friendsync.mappers.PostDomainToPostMapper
import org.joseph.friendsync.post.impl.comment.CommentsStateStateFlowCommunication
import org.joseph.friendsync.post.impl.comment.CommentsUiState
import org.koin.core.component.KoinComponent

class PostDetailViewModel(
    private val postId: Int,
    private val shouldShowAddCommentDialog: Boolean,
    private val userDataStore: UserDataStore,
    private val fetchPostByIdUseCase: FetchPostByIdUseCase,
    private val fetchPostCommentsUseCase: FetchPostCommentsUseCase,
    private val addCommentToPostUseCase: AddCommentToPostUseCase,
    private val editCommentUseCase: EditCommentUseCase,
    private val deleteCommentByIdUseCase: DeleteCommentByIdUseCase,
    private val postDomainToPostMapper: PostDomainToPostMapper,
    private val commentDomainToCommentMapper: CommentDomainToCommentMapper,
    private val commentsStateCommunication: CommentsStateStateFlowCommunication
) : StateScreenModel<PostDetailUiState>(PostDetailUiState.Initial), KoinComponent {

    val commentsUiState: StateFlow<CommentsUiState> = commentsStateCommunication.observe()

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
            is PostDetailEvent.OnProfileClick -> doProfileClick()
            is PostDetailEvent.OnAddCommentClick -> doAddCommentClick()
            is PostDetailEvent.OnEditCommentValueChange -> doCommentMoreIconClick(event)
            is PostDetailEvent.OnNewCommentValueChange -> doOnCommentValueChange(event)
            is PostDetailEvent.OnEditCommentClick -> doEditCommentClick()
            is PostDetailEvent.OnAddDialogChange -> doAddDialogChange(event)
            is PostDetailEvent.OnEditDialogChange -> doEditDialogChange(event)
            is PostDetailEvent.OnDeleteCommentClick -> doDeleteCommentClick(event)
        }
    }

    private suspend fun awaitFetchCurrentUser() = asyncWithDefault(UserPreferences.unknown) {
        userDataStore.fetchCurrentUser()
    }


    private fun fetchPostById() = screenModelScope.launchSafe {
        mutableState.tryEmit(PostDetailUiState.Loading)
        delay(1000)
        val state = when (val response = fetchPostByIdUseCase(postId)) {
            is Result.Success -> fetchStateForSuccessfulPostRequest(response)
            is Result.Error -> PostDetailUiState.Error(response.message ?: defaultErrorMessage)
        }

        mutableState.tryEmit(state)
    }

    private fun fetchPostComments() = screenModelScope.launchSafe {
        commentsStateCommunication.emit(CommentsUiState.Loading)
        val state = when (val response = fetchPostCommentsUseCase(postId)) {
            is Result.Error -> CommentsUiState.Error(response.message ?: defaultErrorMessage)
            is Result.Success -> fetchStateForSuccessfulCommentsRequest(response)
        }
        commentsStateCommunication.emit(state)
    }

    private fun fetchStateForSuccessfulCommentsRequest(
        response: Result<List<CommentDomain>>,
    ): CommentsUiState {
        val postsDomain = response.data
        return if (postsDomain != null) {
            CommentsUiState.Content(postsDomain.map(commentDomainToCommentMapper::map))
                .copy(shouldShowAddCommentDialog = shouldShowAddCommentDialog)
        } else {
            CommentsUiState.Error(response.message ?: defaultErrorMessage)
        }
    }

    private fun fetchStateForSuccessfulPostRequest(
        response: Result<PostDomain>,
    ): PostDetailUiState {
        val postDomain = response.data
        return if (postDomain != null) {
            PostDetailUiState.Content(postDomainToPostMapper.map(postDomain, currentUser.id))
        } else {
            PostDetailUiState.Error(response.message ?: defaultErrorMessage)
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
            handleEditCommentResult(result, state.editCommentValue)
        }
    }

    private fun handleEditCommentResult(
        result: Result<Int>,
        newComment: String
    ) {
        if (result is Result.Error) {
            return
        }
        commentsStateCommunication.update { currentState ->
            if (currentState !is CommentsUiState.Content) return@update currentState
            val comments = currentState.comments.toMutableList()
            val editedCommentIndex = comments.indexOfFirst { it.id == result.data }
            comments[editedCommentIndex] = comments[editedCommentIndex].copy(comment = newComment)
            currentState.copy(comments = comments)
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
            handleAddCommentResult(result)
        }
        commentsStateCommunication.update { state.copy(newCommentValue = String()) }
    }

    private fun handleAddCommentResult(result: Result<CommentDomain?>) {
        if (result is Result.Error) {
            return
        }
        val comment = commentDomainToCommentMapper.map(result.data ?: return)
        commentsStateCommunication.update { currentState ->
            val commentState = currentState as CommentsUiState.Content
            val comments = commentState.comments.toMutableList()
            comments.add(0, comment)
            updateCommentCount(comments.size)
            commentState.copy(comments = comments)
        }
    }

    private fun doDeleteCommentClick(event: PostDetailEvent.OnDeleteCommentClick) {
        screenModelScope.launchSafe {
            val result = deleteCommentByIdUseCase(event.commentId)
            if (result is Result.Error) {
                return@launchSafe
            }
            commentsStateCommunication.update { currentState ->
                val commentState = currentState as CommentsUiState.Content
                val comments = commentState.comments.toMutableList()
                val deletedCommentIndex = comments.indexOfFirst { it.id == result.data }
                comments.removeAt(deletedCommentIndex)
                updateCommentCount(comments.size)
                commentState.copy(comments = comments)
            }
        }
    }

    private fun updateCommentCount(commentsCount: Int) {
        mutableState.update { currentState ->
            val postState = currentState as PostDetailUiState.Content
            postState.copy(post = postState.post.copy(commentCount = commentsCount))
        }
    }

    private fun doProfileClick() {
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

    override fun onDispose() {
        commentsDataJob?.cancel()
        postDataJob?.cancel()
        super.onDispose()
    }
}
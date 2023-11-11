package org.joseph.friendsync.screens.post_detils

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.StateFlow
import org.joseph.friendsync.common.util.Result
import org.joseph.friendsync.common.util.coroutines.asyncWithDefault
import org.joseph.friendsync.common.util.coroutines.launchSafe
import org.joseph.friendsync.data.repository.defaultErrorMessage
import org.joseph.friendsync.domain.models.PostDomain
import org.joseph.friendsync.domain.usecases.post.FetchPostByIdUseCase
import org.joseph.friendsync.managers.UserDataStore
import org.joseph.friendsync.managers.UserPreferences
import org.joseph.friendsync.mappers.PostDomainToPostMapper
import org.joseph.friendsync.models.sampleComments
import org.joseph.friendsync.screens.post_detils.comment.CommentsStateCommunication
import org.joseph.friendsync.screens.post_detils.comment.CommentsUiState
import org.koin.core.component.KoinComponent

class PostDetailViewModel(
    private val postId: Int,
    private val userDataStore: UserDataStore,
    private val fetchPostByIdUseCase: FetchPostByIdUseCase,
    private val postDomainToPostMapper: PostDomainToPostMapper,
    private val commentsStateCommunication: CommentsStateCommunication
) : StateScreenModel<PostDetailUiState>(PostDetailUiState.Initial), KoinComponent {

    val commentsUiState: StateFlow<CommentsUiState> = commentsStateCommunication.observe()

    private var postDataJob: Job? = null
    private var commentsDataJob: Job? = null

    init {
        postDataJob = fetchPostById()
        commentsDataJob = fetchPostComments()
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
            is PostDetailEvent.OnProfileClick -> doProfileClick(event)
            is PostDetailEvent.OnAddCommentClick -> doAddCommentClick()
            is PostDetailEvent.OnCommentMoreIconClick -> doCommentMoreIconClick()
        }
    }

    private fun fetchPostById() = screenModelScope.launchSafe {
        mutableState.tryEmit(PostDetailUiState.Loading)
        delay(2000)
        val currentUser = asyncWithDefault(UserPreferences.unknown) {
            userDataStore.fetchCurrentUser()
        }.await()

        val state = when (val response = fetchPostByIdUseCase(postId)) {
            is Result.Success -> fetchStateForSuccessfulPostRequest(response, currentUser.id)
            is Result.Error -> PostDetailUiState.Error(response.message ?: defaultErrorMessage)
        }

        mutableState.tryEmit(state)
    }

    private fun fetchPostComments() = screenModelScope.launchSafe {
        commentsStateCommunication.emit(CommentsUiState.Loading)
        delay(4000)
        commentsStateCommunication.emit(CommentsUiState.Content(sampleComments))
    }

    private fun fetchStateForSuccessfulPostRequest(
        response: Result<PostDomain>,
        currentUserId: Int
    ): PostDetailUiState {
        val postDomain = response.data
        return if (postDomain != null) {
            PostDetailUiState.Content(postDomainToPostMapper.map(postDomain, currentUserId))
        } else {
            PostDetailUiState.Error(response.message ?: defaultErrorMessage)
        }
    }

    private fun doCommentMoreIconClick() {

    }

    private fun doAddCommentClick() {

    }

    private fun doProfileClick(event: PostDetailEvent) {

    }

    override fun onDispose() {
        commentsDataJob?.cancel()
        postDataJob?.cancel()
        super.onDispose()
    }
}
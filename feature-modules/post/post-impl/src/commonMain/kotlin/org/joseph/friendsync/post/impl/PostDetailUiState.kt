package org.joseph.friendsync.post.impl

import org.joseph.friendsync.models.Post

sealed class PostDetailUiState {

    data object Initial : PostDetailUiState()

    data object Loading : PostDetailUiState()

    data class Error(val message: String) : PostDetailUiState()

    data class Content(
        val post: Post,
    ) : PostDetailUiState()

}
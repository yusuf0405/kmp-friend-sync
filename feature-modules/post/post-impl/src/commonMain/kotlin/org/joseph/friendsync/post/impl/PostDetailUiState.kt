package org.joseph.friendsync.post.impl

import org.joseph.friendsync.ui.components.models.PostMark


sealed class PostDetailUiState {

    data object Initial : PostDetailUiState()

    data object Loading : PostDetailUiState()

    data class Error(val message: String) : PostDetailUiState()

    data class Content(
        val postMark: PostMark
    ) : PostDetailUiState()

}
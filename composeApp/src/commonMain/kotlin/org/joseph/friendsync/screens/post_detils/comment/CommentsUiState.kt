package org.joseph.friendsync.screens.post_detils.comment

import org.joseph.friendsync.models.Comment

sealed class CommentsUiState {

    data object Loading : CommentsUiState()

    data class Error(val message: String) : CommentsUiState()

    data class Content(val comments: List<Comment>) : CommentsUiState()
}
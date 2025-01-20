package org.joseph.friendsync.search.impl.post

import org.joseph.friendsync.search.impl.ErrorMessage
import org.joseph.friendsync.ui.components.models.PostMark

sealed class PostUiState {

    data object Initial : PostUiState()

    data object Loading : PostUiState()

    data object Empty : PostUiState()

    data class Error(
        val message: ErrorMessage
    ) : PostUiState()

    data class Loaded(
        val posts: List<PostMark>,
        val isPaging: Boolean = false,
    ) : PostUiState()
}
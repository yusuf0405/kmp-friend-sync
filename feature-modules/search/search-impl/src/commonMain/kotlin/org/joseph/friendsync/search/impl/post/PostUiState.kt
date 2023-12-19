package org.joseph.friendsync.search.impl.post

import org.joseph.friendsync.ui.components.models.Post

sealed class PostUiState {

    data object Initial : PostUiState()

    data object Loading : PostUiState()

    data object Empty : PostUiState()

    data class Error(
        val message: String
    ) : PostUiState()

    data class Loaded(
        val posts: List<Post>,
        val isPaging: Boolean = false,
    ) : PostUiState()
}
package org.joseph.friendsync.home.impl

import org.joseph.friendsync.ui.components.models.Post


sealed class HomeUiState {

    data object Initial : HomeUiState()

    data object Loading : HomeUiState()

    data class Error(val message: String) : HomeUiState()

    data class Content(
        val isPaging: Boolean,
        val posts: List<Post>
    ) : HomeUiState() {

        companion object {

            val unknown = Content(
                isPaging = false,
                posts = emptyList()
            )
        }
    }
}
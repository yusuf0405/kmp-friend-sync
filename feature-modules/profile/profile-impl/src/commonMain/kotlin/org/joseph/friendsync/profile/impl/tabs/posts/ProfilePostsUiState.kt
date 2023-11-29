package org.joseph.friendsync.profile.impl.tabs.posts

import org.joseph.friendsync.models.Post


sealed class ProfilePostsUiState {

    data object Loading : ProfilePostsUiState()

    data class Content(
        val posts: List<Post>
    ) : ProfilePostsUiState()

    data class Error(
        val errorMessage: String = String(),
    ) : ProfilePostsUiState()
}
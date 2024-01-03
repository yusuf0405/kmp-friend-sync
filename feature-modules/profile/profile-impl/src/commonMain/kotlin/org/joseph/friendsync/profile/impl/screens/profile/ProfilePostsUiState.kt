package org.joseph.friendsync.profile.impl.screens.profile

import androidx.compose.runtime.Immutable
import org.joseph.friendsync.ui.components.models.Post
import org.joseph.friendsync.ui.components.models.PostMark


@Immutable
sealed class ProfilePostsUiState {

    data object Loading : ProfilePostsUiState()

    data class Content(
        val posts: List<Post>
    ) : ProfilePostsUiState()

    data class Error(
        val errorMessage: String = String(),
    ) : ProfilePostsUiState()
}
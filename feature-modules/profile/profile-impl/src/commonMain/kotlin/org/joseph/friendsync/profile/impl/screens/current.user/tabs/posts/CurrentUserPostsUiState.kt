package org.joseph.friendsync.profile.impl.screens.current.user.tabs.posts

import androidx.compose.runtime.Immutable
import org.joseph.friendsync.domain.markers.models.PostMarkDomain
import org.joseph.friendsync.ui.components.models.PostMark

@Immutable
sealed class CurrentUserPostsUiState {

    data object Loading : CurrentUserPostsUiState()

    data class Content(
        val postMarks: List<PostMark>
    ) : CurrentUserPostsUiState()

    data class Error(
        val errorMessage: String = String(),
    ) : CurrentUserPostsUiState()
}
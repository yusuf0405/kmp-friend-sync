package org.joseph.friendsync.home.impl

import androidx.compose.runtime.Immutable
import org.joseph.friendsync.ui.components.models.Post
import org.joseph.friendsync.ui.components.models.PostMark

@Immutable
internal sealed class HomeUiState {

    data object Initial : HomeUiState()

    data object Loading : HomeUiState()

    data class Error(val message: String) : HomeUiState()

    @Immutable
    data class Content(
        val isPaging: Boolean,
        val postMarks: List<PostMark>
    ) : HomeUiState() {

        companion object {

            val unknown = Content(
                isPaging = false,
                postMarks = emptyList()
            )
        }
    }
}
package org.joseph.friendsync.profile.impl.models

import androidx.compose.runtime.Composable
import kotlinx.coroutines.flow.StateFlow
import org.joseph.friendsync.core.ui.strings.MainResStrings
import org.joseph.friendsync.profile.impl.tabs.posts.ProfilePostsScreen
import org.joseph.friendsync.profile.impl.tabs.posts.ProfilePostsUiState

data class ProfileTab(
    val title: String,
    val content: @Composable () -> Unit
) {

    companion object {

        fun fetchProfileTabs(
            profilePostsUiState: StateFlow<ProfilePostsUiState>,
        ) = listOf(
            ProfileTab(
                title = MainResStrings.posts,
                content = { ProfilePostsScreen(uiStateFlow = profilePostsUiState) }
            ),
            ProfileTab(
                title = MainResStrings.stories,
                content = {}
            ),
            ProfileTab(
                title = MainResStrings.liked,
                content = {}
            ),
            ProfileTab(
                title = MainResStrings.tagged,
                content = {}
            )
        )
    }
}
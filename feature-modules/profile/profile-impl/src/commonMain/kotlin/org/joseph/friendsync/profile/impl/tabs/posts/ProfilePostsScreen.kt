package org.joseph.friendsync.profile.impl.tabs.posts

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import kotlinx.coroutines.flow.StateFlow

@Composable
fun ProfilePostsScreen(
    uiStateFlow: StateFlow<ProfilePostsUiState>,
) {
    val uiState by uiStateFlow.collectAsState()

}
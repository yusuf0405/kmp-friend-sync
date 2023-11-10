package org.joseph.friendsync.screens.home

import org.joseph.friendsync.models.Post

data class HomeUiState(
    val isPaging: Boolean = false,
    val isLoading: Boolean = true,
    val posts: List<Post> = emptyList(),
    val errorMessage: String? = null
)
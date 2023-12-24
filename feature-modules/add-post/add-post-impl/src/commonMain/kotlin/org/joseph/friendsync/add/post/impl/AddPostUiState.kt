package org.joseph.friendsync.add.post.impl

import androidx.compose.ui.graphics.ImageBitmap
import org.joseph.friendsync.common.user.UserPreferences

data class AddPostUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val message: String? = null,
    val user: UserPreferences = UserPreferences.unknown,
    val images: List<ImageBitmap> = emptyList(),
)
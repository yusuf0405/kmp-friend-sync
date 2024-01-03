package org.joseph.friendsync.profile.impl.screens.profile

import org.joseph.friendsync.ui.components.models.user.UserDetail

sealed class ProfileUiState {

    data object Initial : ProfileUiState()

    data object Loading : ProfileUiState()

    data class Error(val errorMessage: String) : ProfileUiState()

    data class Content(val user: UserDetail) : ProfileUiState()
}
package org.joseph.friendsync.profile.impl.screens.current.user

import org.joseph.friendsync.ui.components.models.user.CurrentUser

sealed class CurrentUserUiState {

    data object Initial : CurrentUserUiState()

    data object Loading : CurrentUserUiState()

    data class Error(val errorMessage: String) : CurrentUserUiState()

    data class Content(val user: CurrentUser) : CurrentUserUiState()
}
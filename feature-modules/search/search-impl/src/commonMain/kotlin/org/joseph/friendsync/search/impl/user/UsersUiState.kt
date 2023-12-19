package org.joseph.friendsync.search.impl.user

import org.joseph.friendsync.ui.components.models.user.UserInfo

sealed class UsersUiState {

    data object Initial : UsersUiState()

    data object Loading : UsersUiState()

    data object Empty : UsersUiState()

    data class Error(
        val message: String
    ) : UsersUiState()

    data class Loaded(
        val users: List<UserInfo>,
        val isPaging: Boolean = false,
    ) : UsersUiState()
}
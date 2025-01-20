package org.joseph.friendsync.search.impl.user

import org.joseph.friendsync.search.impl.ErrorMessage
import org.joseph.friendsync.ui.components.models.UserInfoMark

sealed class UsersUiState {

    data object Initial : UsersUiState()

    data object Loading : UsersUiState()

    data object Empty : UsersUiState()

    data class Error(
        val message: ErrorMessage
    ) : UsersUiState()

    data class Loaded(
        val users: List<UserInfoMark>,
        val isPaging: Boolean = false,
    ) : UsersUiState()
}
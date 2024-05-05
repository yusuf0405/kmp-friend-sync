package org.joseph.friendsync.search.impl.user

import org.joseph.friendsync.ui.components.models.UserInfoMark

sealed class UsersUiState {

    data object Initial : UsersUiState()

    data object Loading : UsersUiState()

    data object Empty : UsersUiState()

    data class Error(
        val message: String
    ) : UsersUiState()

    data class Loaded(
        val users: List<UserInfoMark>,
        val isPaging: Boolean = false,
    ) : UsersUiState()
}
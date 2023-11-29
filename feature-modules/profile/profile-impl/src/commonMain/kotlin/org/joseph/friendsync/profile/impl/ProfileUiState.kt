package org.joseph.friendsync.profile.impl

import org.joseph.friendsync.models.user.UserDetail
import org.joseph.friendsync.profile.impl.models.ProfileTab

sealed class ProfileUiState {

    data object Initial : ProfileUiState()

    data object Loading : ProfileUiState()

    data class Error(val errorMessage: String) : ProfileUiState()

    data class Content(
        val userDetail: UserDetail,
        val tabs: List<ProfileTab> = emptyList(),
        val isCurrentUser: Boolean = false
    ) : ProfileUiState()
}
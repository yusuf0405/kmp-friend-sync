package org.joseph.friendsync.profile.impl

import org.joseph.friendsync.profile.impl.models.ProfileTab
import org.joseph.friendsync.ui.components.models.user.UserDetail

sealed class ProfileUiState {

    data object Initial : ProfileUiState()

    data object Loading : ProfileUiState()

    data class Error(val errorMessage: String) : ProfileUiState()

    data class Content(
        val userDetail: UserDetail,
        val tabs: List<ProfileTab> = emptyList(),
    ) : ProfileUiState()
}
package org.joseph.friendsync.profile.impl.edit_profile

data class EditProfileUiState(
    val isLoading: Boolean = false,
    val name: String = String(),
    val lastName: String = String(),
    val email: String = String(),
    val aboutMe: String = String(),
    val education: String = String(),
    val avatar: String = String(),
)
package org.joseph.friendsync.profile.impl.screens.edit.profile

import org.joseph.friendsync.profile.impl.screens.profile.UNKNOWN_USER_ID
import org.joseph.friendsync.ui.components.models.user.CurrentUser

data class EditProfileUiState(
    val isLoading: Boolean = false,
    val id: Int = UNKNOWN_USER_ID,
    val name: String = String(),
    val lastName: String = String(),
    val email: String = String(),
    val aboutMe: String = String(),
    val education: String = String(),
    val avatar: String = String(),
)

fun CurrentUser.toEditProfileUiState() = EditProfileUiState(
    id = id,
    name = name,
    lastName = lastName,
    aboutMe = bio,
    avatar = avatar,
    education = education,
    email = email
)
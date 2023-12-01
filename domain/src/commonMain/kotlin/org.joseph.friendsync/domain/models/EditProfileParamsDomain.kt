package org.joseph.friendsync.domain.models

data class EditProfileParams(
    val id: Int,
    val name: String,
    val lastName: String,
    val email: String,
    val bio: String,
    val education: String,
    val avatar: String,
)
package org.joseph.friendsync.models.user

import kotlinx.datetime.LocalDate

data class UserDetail(
    val id: Int,
    val name: String,
    val lastName: String,
    val bio: String?,
    val avatar: String?,
    val profileBackground: String?,
    val education: String?,
    val releaseDate: LocalDate,
    val followersCount: Int,
    val followingCount: Int
) {
    fun fullName() = "$name $lastName"

    companion object {

        val unknown = UserDetail(
            id = -1,
            name = "Unknown",
            lastName = "",
            bio = "",
            avatar = "",
            profileBackground = "",
            education = "",
            releaseDate = LocalDate(1900, 1, 21),
            followersCount = 0,
            followingCount = 0
        )
    }
}
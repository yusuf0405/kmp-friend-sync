package org.joseph.friendsync.ui.components.models.user

import androidx.compose.runtime.Immutable
import kotlinx.datetime.LocalDate

@Immutable
data class UserDetail(
    val id: Int,
    val name: String,
    val lastName: String,
    val bio: String,
    val avatar: String,
    val profileBackground: String,
    val education: String,
    val releaseDate: String,
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
            releaseDate = LocalDate(1900, 1, 21).toString(),
            followersCount = 0,
            followingCount = 0
        )
    }
}
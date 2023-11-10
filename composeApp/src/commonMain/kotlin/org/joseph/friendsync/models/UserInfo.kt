package org.joseph.friendsync.models

import kotlinx.datetime.LocalDate

data class UserInfo(
    val id: Int,
    val name: String,
    val lastName: String,
    val avatar: String?,
    val releaseDate: LocalDate,
) {

    companion object {

        val preview = UserInfo(
            id = 1,
            name = "Joseph",
            lastName = "Barbera",
            avatar = "https://clipart-library.com/images/pTqre6K8c.jpg",
            releaseDate = LocalDate(1900, 1, 21)
        )
    }
}

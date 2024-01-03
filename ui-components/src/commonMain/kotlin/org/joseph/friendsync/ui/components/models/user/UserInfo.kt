package org.joseph.friendsync.ui.components.models.user

import androidx.compose.runtime.Immutable
import kotlinx.datetime.LocalDate

@Immutable
data class UserInfo(
    val id: Int,
    val name: String,
    val lastName: String,
    val avatar: String,
    val releaseDate: String,
) {

    companion object {

        val preview = UserInfo(
            id = 1,
            name = "Joseph",
            lastName = "Barbera",
            avatar = "https://clipart-library.com/images/pTqre6K8c.jpg",
            releaseDate = LocalDate(1900, 1, 21).toString()
        )
    }
}

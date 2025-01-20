package org.joseph.friendsync.ui.components.models

import androidx.compose.runtime.Immutable

@Immutable
data class FollowsUser(
    val id: Int,
    val name: String,
    val lastName: String,
    val profileUrl: String,
    val isFollowing: Boolean = false
)

val sampleUsers = listOf(
    FollowsUser(
        id = 1,
        name = "Joseph",
        lastName = "Barbera",
        profileUrl = "https://clipart-library.com/images/pTqre6K8c.jpg"
    ),
    FollowsUser(
        id = 2,
        name = "John",
        lastName = "Cena",
        profileUrl = "https://stihi.ru/pics/2016/10/13/9056.jpg",
    ),
    FollowsUser(
        id = 3,
        name = "Cristiano",
        lastName = "Barbera",
        profileUrl = "https://clipart-library.com/images/pTqre6K8c.jpg",
    ),
    FollowsUser(
        id = 4,
        name = "James",
        lastName = "Barbera",
        profileUrl = "https://clipart-library.com/images/pTqre6K8c.jpg",
    )
)
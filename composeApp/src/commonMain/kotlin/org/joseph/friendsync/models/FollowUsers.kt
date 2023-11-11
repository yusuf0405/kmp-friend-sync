package org.joseph.friendsync.models

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
        profileUrl = "https://avatars.mds.yandex.net/i?id=943da35c25955953cfeae1282e5856fd313be325-7543369-images-thumbs&n=13",
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
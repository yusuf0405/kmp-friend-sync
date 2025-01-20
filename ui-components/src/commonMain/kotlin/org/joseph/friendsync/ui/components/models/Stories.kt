package org.joseph.friendsync.ui.components.models

import androidx.compose.runtime.Immutable

@Immutable
data class Stories(
    val imageUrl: String,
    val userId: String,
    val userName: String,
    val releasedDate: String,
    val userImage: String,
    val isViewed: Boolean
)

val fakeStories = listOf(
    Stories(
        imageUrl = "https://avatars.mds.yandex.net/i?id=0d38323fb4018be34214f38c09430a643337ef6d-10414509-images-thumbs&n=13",
        userId = "1",
        userImage = "https://clipart-library.com/images/pTqre6K8c.jpg",
        isViewed = true,
        userName = "Joseph",
        releasedDate = "35m ago"
    ),
    Stories(
        imageUrl = "https://wallpapers.com/images/hd/summer-pictures-qydy6wrbphuvuntc.jpg",
        userId = "1",
        userImage = "https://stihi.ru/pics/2016/10/13/9056.jpg",
        isViewed = false,
        userName = "Joseph",
        releasedDate = "35m ago"
    ),
    Stories(
        imageUrl = "https://image.winudf.com/v2/image1/Y29tLmxpdmVwcm8ud2FsbHBhcGVycy53cDIwMDkyX3NjcmVlbl8xXzE1NjcwNDA0MjhfMDEw/screen-1.jpg?fakeurl=1&type=.jpg",
        userId = "2",
        userImage = "https://clipart-library.com/images/pTqre6K8c.jpg",
        isViewed = false,
        userName = "Joseph",
        releasedDate = "35m ago"
    ),
    Stories(
        imageUrl = "https://w.forfun.com/fetch/8a/8a052e4ded04bbd746e9364b5c15aab0.jpeg",
        userId = "2",
        userImage = "https://clipart-library.com/images/pTqre6K8c.jpg",
        isViewed = false,
        userName = "Joseph",
        releasedDate = "35m ago"
    ),
    Stories(
        imageUrl = "https://wpapers.ru/wallpapers/nature/13908/download/1920x1536_Зелёное-поле.jpg",
        userId = "2",
        userImage = "https://stihi.ru/pics/2016/10/13/9056.jpg",
        isViewed = false,
        userName = "Joseph",
        releasedDate = "35m ago"
    ),
)
package org.joseph.friendsync.models

import androidx.compose.runtime.Immutable
import org.joseph.friendsync.models.Post.PhotoPost
import org.joseph.friendsync.models.Post.TextPost

@Immutable
sealed class Post(
    val postId: Int
) {
    data class PhotoPost(
        val id: Int,
        val text: String,
        val imageUrls: List<String>,
        val createdAt: String,
        val likesCount: Int,
        val commentCount: Int,
        val authorId: Int,
        val authorName: String,
        val authorLastName: String,
        val authorImage: String,
        val isLiked: Boolean = false,
        val isOwnPost: Boolean = false
    ) : Post(id)

    data class TextPost(
        val id: Int,
        val text: String,
        val createdAt: String,
        val likesCount: Int,
        val commentCount: Int,
        val authorId: Int,
        val authorName: String,
        val authorLastName: String,
        val authorImage: String,
        val isLiked: Boolean = false,
        val isOwnPost: Boolean = false
    ) : Post(id)
}

val sampleTextPosts = listOf(
    TextPost(
        id = 99,
        text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.",
        createdAt = "Apr 12, 2023",
        likesCount = 221,
        commentCount = 41,
        authorId = 3,
        authorName = "Cristiano",
        authorImage = "https://picsum.photos/200",
        authorLastName = "Barbers"
    ),
    TextPost(
        id = 14,
        text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. " +
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.",
        createdAt = "Apr 12, 2023",
        likesCount = 221,
        commentCount = 41,
        authorId = 3,
        authorName = "Cristiano",
        authorImage = "https://picsum.photos/200",
        authorLastName = "Barbers"
    ),
    TextPost(
        id = 1533,
        text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. " +
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.",
        createdAt = "Apr 12, 2023",
        likesCount = 221,
        commentCount = 41,
        authorId = 3,
        authorName = "Cristiano",
        authorImage = "https://picsum.photos/200",
        authorLastName = "Barbers"
    ),
    TextPost(
        id = 16,
        text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. " +
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. " +
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. " +
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.",
        createdAt = "Apr 12, 2023",
        likesCount = 221,
        commentCount = 41,
        authorId = 3,
        authorName = "Cristiano",
        authorImage = "https://picsum.photos/200",
        authorLastName = "Barbers"
    ),
)

val samplePhotoPosts = listOf(
    PhotoPost(
        id = 11,
        text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.",
        imageUrls = listOf(
            "https://avatars.mds.yandex.net/i?id=0d38323fb4018be34214f38c09430a643337ef6d-10414509-images-thumbs&n=13",
            "https://clipart-library.com/images/pTqre6K8c.jpg",
            "https://townsquare.media/site/442/files/2012/05/bender.jpg?w=1200&h=0&zc=1&s=0&a=t&q=89",
            "https://sun9-67.userapi.com/c4741/u112406626/-6/x_706b6a3c.jpg",
            "https://www.wallpaperflare.com/static/956/184/864/rick-and-morty-adult-swim-cartoon-rick-sanchez-wallpaper.jpg"
        ),
        createdAt = "20 min",
        likesCount = 12,
        commentCount = 3,
        authorId = 1,
        authorName = "Mr Dip",
        authorImage = "http://0.0.0.0:8080/images/a2b165b4-7713-4ae1-ac5e-eedbbb28943e.png",
        authorLastName = "Barbers"
    ),

    PhotoPost(
        id = 31,
        text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.",
        imageUrls = listOf(
            "https://wallpapers.com/images/hd/summer-pictures-qydy6wrbphuvuntc.jpg",
            "https://townsquare.media/site/442/files/2012/05/bender.jpg?w=1200&h=0&zc=1&s=0&a=t&q=89",
            "https://sun9-67.userapi.com/c4741/u112406626/-6/x_706b6a3c.jpg"
        ),
        createdAt = "May 03, 2023",
        likesCount = 121,
        commentCount = 23,
        authorId = 2,
        authorName = "John Cena",
        authorImage = "http://192.168.30.243:8080/uploads/1.jpeg",
        authorLastName = "Barbers"
    ),
    PhotoPost(
        id = 13,
        text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.",
        imageUrls = listOf("https://yt3.googleusercontent.com/Io8UGoybnX-C6bXuvuxNKGG6Mz-8cfdkO5wKbdJRE7IdwO7jrwbWU9OSE96vESg9Y1ofx-OOUfg=s900-c-k-c0x00ffffff-no-rj"),
        createdAt = "Apr 12, 2023",
        likesCount = 221,
        commentCount = 41,
        authorId = 3,
        authorName = "Cristiano",
        authorImage = "https://picsum.photos/200",
        authorLastName = "Barbers"
    ),
    PhotoPost(
        id = 33,
        text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.",
        imageUrls =
        listOf(
            "https://1.bp.blogspot.com/-ErMHDuaxo6Y/XUU203uHh3I/AAAAAAAAbNo/XaWcpmF7XporvyZcq58C8e7xUQi-J3GJgCLcBGAs/s1600/Screenshot%2B%2528520%2529.png",
            "https://www.wallpaperflare.com/static/956/184/864/rick-and-morty-adult-swim-cartoon-rick-sanchez-wallpaper.jpg"
        ),
        createdAt = "Mar 31, 2023",
        likesCount = 90,
        commentCount = 13,
        authorId = 3,
        authorName = "Cristiano",
        authorImage = "https://picsum.photos/200",
        authorLastName = "Barbers"
    ),
    PhotoPost(
        id = 19,
        text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.",
        imageUrls = listOf(
            "https://www.wallpaperflare.com/static/956/184/864/rick-and-morty-adult-swim-cartoon-rick-sanchez-wallpaper.jpg"
        ),
        createdAt = "Jan 30, 2023",
        likesCount = 121,
        commentCount = 31,
        authorId = 4,
        authorName = "L. James",
        authorImage = "https://picsum.photos/200",
        authorLastName = "Barbers"
    ),
)
package org.joseph.friendsync.models

import androidx.compose.runtime.Immutable
import org.joseph.friendsync.models.user.UserInfo

@Immutable
data class Comment(
    val id: Int,
    val comment: String,
    val postId: Int,
    val likesCount: Int,
    val user: UserInfo,
    val releaseDate: String,
    val isCurrentUserComment: Boolean = false
)

val sampleComments = listOf(
    Comment(
        id = 1,
        comment = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.",
        releaseDate = "2023-04-30",
        postId = 323,
        likesCount = 0,
        user = UserInfo.preview
    ),
    Comment(
        id = 2,
        comment = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.",
        releaseDate = "2023-04-30",
        postId = 32,
        likesCount = 0,
        user = UserInfo.preview
    ),
    Comment(
        id = 3,
        comment = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.",
        releaseDate = "2023-04-30",
        postId = 3332,
        likesCount = 0,
        user = UserInfo.preview
    ),
    Comment(
        id = 4,
        comment = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.",
        releaseDate = "2023-04-30",
        postId = 33232,
        likesCount = 0,
        user = UserInfo.preview
    ),
)
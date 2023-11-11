package org.joseph.friendsync.models

data class Comment(
    val id: String,
    val comment: String,
    val date: String,
    val authName: String,
    val authImageUrl: String,
    val authId: Int,
    val postId: String,
)

val sampleComments = listOf(
    Comment(
        id = "1",
        comment = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.",
        authId = sampleUsers.random().id,
        authImageUrl = sampleUsers.random().profileUrl,
        authName = sampleUsers.random().name,
        date = "2023-04-30",
        postId = "32"

    ),
    Comment(
        id = "2",
        comment = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.",
        authId = sampleUsers.random().id,
        authImageUrl = sampleUsers.random().profileUrl,
        authName = sampleUsers.random().name,
        date = "2023-04-30",
        postId = "32"

    ),
    Comment(
        id = "3",
        comment = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.",
        authId = sampleUsers.random().id,
        authImageUrl = sampleUsers.random().profileUrl,
        authName = sampleUsers.random().name,
        date = "2023-04-30",
        postId = "32"

    ),
    Comment(
        id = "4",
        comment = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.",
        authId = sampleUsers.random().id,
        authImageUrl = sampleUsers.random().profileUrl,
        authName = sampleUsers.random().name,
        date = "2023-04-30",
        postId = "32"

    ),
    Comment(
        id = "5",
        comment = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.",
        authId = sampleUsers.random().id,
        authImageUrl = sampleUsers.random().profileUrl,
        authName = sampleUsers.random().name,
        date = "2023-04-30",
        postId = "32"

    ),
    Comment(
        id = "44",
        comment = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.",
        authId = sampleUsers.random().id,
        authImageUrl = sampleUsers.random().profileUrl,
        authName = sampleUsers.random().name,
        date = "2023-04-30",
        postId = "32"

    ),
    Comment(
        id = "6",
        comment = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.",
        authId = sampleUsers.random().id,
        authImageUrl = sampleUsers.random().profileUrl,
        authName = sampleUsers.random().name,
        date = "2023-04-30",
        postId = "32"

    ),
    Comment(
        id = "7",
        comment = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.",
        authId = sampleUsers.random().id,
        authImageUrl = sampleUsers.random().profileUrl,
        authName = sampleUsers.random().name,
        date = "2023-04-30",
        postId = "32"

    ),
)
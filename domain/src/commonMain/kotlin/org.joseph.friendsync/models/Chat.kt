package org.joseph.friendsync.models

data class Chat(
    val userId: String,
    val userName: String,
    val lastMessage: String,
    val lastMessageSendDate: String,
    val userImage: String
)

val sampleAvatars = listOf(
    "https://clipart-library.com/images/pTqre6K8c.jpg",
    "https://avatars.mds.yandex.net/i?id=943da35c25955953cfeae1282e5856fd313be325-7543369-images-thumbs&n=13",
    "https://clipart-library.com/images/pTqre6K8c.jpg",
    "https://clipart-library.com/images/pTqre6K8c.jpg",
)

val sampleChats = listOf(
    Chat(
        userId = "1",
        userImage = "https://clipart-library.com/images/pTqre6K8c.jpg",
        userName = "Jessica Thompson",
        lastMessage = "Hey you! Are u there?",
        lastMessageSendDate = "4h ago"
    ),
    Chat(
        userId = "1",
        userImage = "https://avatars.mds.yandex.net/i?id=943da35c25955953cfeae1282e5856fd313be325-7543369-images-thumbs&n=13",
        userName = "Kat Williams",
        lastMessage = "OMG! OMG! OMG!",
        lastMessageSendDate = "1h ago"
    ),
    Chat(
        userId = "2",
        userImage = "https://clipart-library.com/images/pTqre6K8c.jpg",
        userName = "Jacob Washington",
        lastMessage = "Sure. Sunday works for me!",
        lastMessageSendDate = "12h ago"
    ),
    Chat(
        userId = "2",
        userImage = "https://clipart-library.com/images/pTqre6K8c.jpg",
        userName = "Leslie Alexander",
        lastMessage = "Sent you an invite for next monday.",
        lastMessageSendDate = "2h ago"
    ),
    Chat(
        userId = "2",
        userImage = "https://avatars.mds.yandex.net/i?id=943da35c25955953cfeae1282e5856fd313be325-7543369-images-thumbs&n=13",
        userName = "Tony Monta",
        lastMessage = "How’s Alicia doing? Ask her to give m...",
        lastMessageSendDate = "6h ago"
    ),
    Chat(
        userId = "2",
        userImage = "https://avatars.mds.yandex.net/i?id=943da35c25955953cfeae1282e5856fd313be325-7543369-images-thumbs&n=13",
        userName = "Tony Monta",
        lastMessage = "How’s Alicia doing? Ask her to give m...",
        lastMessageSendDate = "6h ago"
    ),
    Chat(
        userId = "2",
        userImage = "https://avatars.mds.yandex.net/i?id=943da35c25955953cfeae1282e5856fd313be325-7543369-images-thumbs&n=13",
        userName = "Tony Monta",
        lastMessage = "How’s Alicia doing? Ask her to give m...",
        lastMessageSendDate = "6h ago"
    ),
    Chat(
        userId = "2",
        userImage = "https://avatars.mds.yandex.net/i?id=943da35c25955953cfeae1282e5856fd313be325-7543369-images-thumbs&n=13",
        userName = "Tony Monta",
        lastMessage = "How’s Alicia doing? Ask her to give m...",
        lastMessageSendDate = "6h ago"
    ),
)
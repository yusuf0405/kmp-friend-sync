package org.joseph.friendsync.ui.components.models

import androidx.compose.runtime.Immutable

@Immutable
data class Chat(
    val userId: String,
    val userName: String,
    val lastMessage: String,
    val lastMessageSendDate: String,
    val userImage: String
)

val sampleAvatars = listOf(
    "https://clipart-library.com/images/pTqre6K8c.jpg",
    "https://stihi.ru/pics/2016/10/13/9056.jpg",
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
        userImage = "https://stihi.ru/pics/2016/10/13/9056.jpg",
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
        userImage = "https://stihi.ru/pics/2016/10/13/9056.jpg",
        userName = "Tony Monta",
        lastMessage = "How’s Alicia doing? Ask her to give m...",
        lastMessageSendDate = "6h ago"
    ),
    Chat(
        userId = "2",
        userImage = "https://stihi.ru/pics/2016/10/13/9056.jpg",
        userName = "Tony Monta",
        lastMessage = "How’s Alicia doing? Ask her to give m...",
        lastMessageSendDate = "6h ago"
    ),
    Chat(
        userId = "2",
        userImage = "https://stihi.ru/pics/2016/10/13/9056.jpg",
        userName = "Tony Monta",
        lastMessage = "How’s Alicia doing? Ask her to give m...",
        lastMessageSendDate = "6h ago"
    ),
    Chat(
        userId = "2",
        userImage = "https://stihi.ru/pics/2016/10/13/9056.jpg",
        userName = "Tony Monta",
        lastMessage = "How’s Alicia doing? Ask her to give m...",
        lastMessageSendDate = "6h ago"
    ),
)
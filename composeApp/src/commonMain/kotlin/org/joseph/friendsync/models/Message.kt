package org.joseph.friendsync.models

import kotlinx.datetime.LocalDate

data class Message(
    val userId: String,
    val userImage: String,
    val releaseDate: String,
    val releaseTime: String,
    val message: String,
    val isYouMessage: Boolean
)

fun randomMassages() = listOf(
    "Alex, let’s meet this weekend. I’ll check\n" +
            "with Dave too \uD83D\uDE0E",
    "Sure. Let’s aim for saturday",
    "I’m visiting mom this sunday \uD83D\uDC7B",
    "Alrighty! Will give you a call shortly \uD83E\uDD17",
    "❤️",
    "Hey you! Are you there?",
    "\uD83D\uDC4B Hi Jess! What’s up?"
)

val sampleMessages = listOf(
    Message(
        userImage = sampleAvatars.random(),
        userId = "1",
        releaseDate = LocalDate.fromEpochDays(1).toString(),
        releaseTime = LocalDate.fromEpochDays(1).toString(),
        message = randomMassages().random(),
        isYouMessage = false
    ),
    Message(
        userImage = sampleAvatars.random(),
        userId = "2",
        releaseDate = LocalDate.fromEpochDays(1).toString(),
        releaseTime = LocalDate.fromEpochDays(1).toString(),
        message = randomMassages().random(),
        isYouMessage = true
    ),
    Message(
        userImage = sampleAvatars.random(),
        userId = "3",
        releaseDate = LocalDate.fromEpochDays(1).toString(),
        releaseTime = LocalDate.fromEpochDays(1).toString(),
        message = randomMassages().random(),
        isYouMessage = true
    ),
    Message(
        userImage = sampleAvatars.random(),
        userId = "4",
        releaseDate = LocalDate.fromEpochDays(1).toString(),
        releaseTime = LocalDate.fromEpochDays(1).toString(),
        message = randomMassages().random(),
        isYouMessage = false
    ),
    Message(
        userImage = sampleAvatars.random(),
        userId = "5",
        releaseDate = LocalDate.fromEpochDays(1).toString(),
        releaseTime = LocalDate.fromEpochDays(1).toString(),
        message = randomMassages().random(),
        isYouMessage = true
    ),
    Message(
        userImage = sampleAvatars.random(),
        userId = "6",
        releaseDate = LocalDate.fromEpochDays(1).toString(),
        releaseTime = LocalDate.fromEpochDays(1).toString(),
        message = randomMassages().random(),
        isYouMessage = false
    ),
    Message(
        userImage = sampleAvatars.random(),
        userId = "7",
        releaseDate = LocalDate.fromEpochDays(1).toString(),
        releaseTime = LocalDate.fromEpochDays(1).toString(),
        message = randomMassages().random(),
        isYouMessage = true
    ),
    Message(
        userImage = sampleAvatars.random(),
        userId = "8",
        releaseDate = LocalDate.fromEpochDays(1).toString(),
        releaseTime = LocalDate.fromEpochDays(1).toString(),
        message = randomMassages().random(),
        isYouMessage = true
    ),
    Message(
        userImage = sampleAvatars.random(),
        userId = "9",
        releaseDate = LocalDate.fromEpochDays(1).toString(),
        releaseTime = LocalDate.fromEpochDays(1).toString(),
        message = randomMassages().random(),
        isYouMessage = false
    ),
    Message(
        userImage = sampleAvatars.random(),
        userId = "10",
        releaseDate = LocalDate.fromEpochDays(1).toString(),
        releaseTime = LocalDate.fromEpochDays(1).toString(),
        message = randomMassages().random(),
        isYouMessage = true
    ),
    Message(
        userImage = sampleAvatars.random(),
        userId = "11",
        releaseDate = LocalDate.fromEpochDays(1).toString(),
        releaseTime = LocalDate.fromEpochDays(1).toString(),
        message = randomMassages().random(),
        isYouMessage = true
    ),
    Message(
        userImage = sampleAvatars.random(),
        userId = "12",
        releaseDate = LocalDate.fromEpochDays(1).toString(),
        releaseTime = LocalDate.fromEpochDays(1).toString(),
        message = randomMassages().random(),
        isYouMessage = true
    ),
    Message(
        userImage = sampleAvatars.random(),
        userId = "13",
        releaseDate = LocalDate.fromEpochDays(1).toString(),
        releaseTime = LocalDate.fromEpochDays(1).toString(),
        message = randomMassages().random(),
        isYouMessage = false
    ),
    Message(
        userImage = sampleAvatars.random(),
        userId = "14",
        releaseDate = LocalDate.fromEpochDays(1).toString(),
        releaseTime = LocalDate.fromEpochDays(1).toString(),
        message = randomMassages().random(),
        isYouMessage = true
    ),
)
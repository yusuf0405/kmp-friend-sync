package org.joseph.friendsync.models

data class Category(
    val id: Int,
    val name: String
) {

    companion object {
        val unknown = Category(
            id = -1,
            name = "Unknown"
        )
    }
}
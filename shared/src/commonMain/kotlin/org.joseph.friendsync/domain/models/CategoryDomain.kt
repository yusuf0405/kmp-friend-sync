package org.joseph.friendsync.domain.models

data class CategoryDomain(
    val id: Int,
    val name: String
) {

    companion object {
        val unknown = CategoryDomain(
            id = -1,
            name = String()
        )
    }
}
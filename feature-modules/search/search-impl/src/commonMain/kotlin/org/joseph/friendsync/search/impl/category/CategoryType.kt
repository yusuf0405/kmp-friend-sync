package org.joseph.friendsync.search.impl.category

enum class CategoryType(
    val value: String,
) {
    ALL("All"),
    PROFILES("Profiles"),
    PHOTOS("Photos"),
    TEXT("Text"),
    UNKNOWN("Unknown");

    companion object {

        fun findCategoryTypeByValue(value: String) = entries.find { it.value == value } ?: UNKNOWN
    }
}
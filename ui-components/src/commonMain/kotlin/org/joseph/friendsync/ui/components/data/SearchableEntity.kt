package org.joseph.friendsync.ui.components.data

interface SearchableEntity {

    fun doesContainsText(searchString: String): Boolean

}
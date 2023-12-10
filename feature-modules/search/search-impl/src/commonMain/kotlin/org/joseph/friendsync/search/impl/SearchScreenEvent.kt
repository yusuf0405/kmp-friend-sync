package org.joseph.friendsync.search.impl

import org.joseph.friendsync.ui.components.models.Category


sealed class SearchScreenEvent {

    data object RefreshAllData : SearchScreenEvent()

    data class OnSearchValueChange(val value: String) : SearchScreenEvent()

    data class OnCategoryClick(val category: Category) : SearchScreenEvent()
}
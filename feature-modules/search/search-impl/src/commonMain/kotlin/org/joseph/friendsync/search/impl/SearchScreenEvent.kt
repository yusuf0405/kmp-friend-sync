package org.joseph.friendsync.search.impl

import androidx.compose.runtime.Immutable
import org.joseph.friendsync.ui.components.models.Category


@Immutable
sealed class SearchScreenEvent {

    data object RefreshAllData : SearchScreenEvent()

    data class OnSearchValueChange(val value: String) : SearchScreenEvent()

    data class OnCategoryClick(val category: Category) : SearchScreenEvent()

    data class OnCommentClick(val postId: Int) : SearchScreenEvent()

    data class OnPostClick(val postId: Int) : SearchScreenEvent()

    data class OnProfileClick(val userId: Int) : SearchScreenEvent()

    data class OnFollowButtonClick(
        val userId: Int,
        val isFollow: Boolean
    ) : SearchScreenEvent()
}
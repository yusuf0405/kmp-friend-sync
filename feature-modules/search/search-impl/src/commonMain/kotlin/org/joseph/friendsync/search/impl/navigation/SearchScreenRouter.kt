package org.joseph.friendsync.search.impl.navigation

internal sealed class SearchScreenRouter {

    data class PostDetails(
        val postId: Int,
        val shouldShowAddCommentDialog: Boolean
    ) : SearchScreenRouter()

    data class UserProfile(val userId: Int) : SearchScreenRouter()

    data object EditProfile : SearchScreenRouter()

    data object Unknown : SearchScreenRouter()
}
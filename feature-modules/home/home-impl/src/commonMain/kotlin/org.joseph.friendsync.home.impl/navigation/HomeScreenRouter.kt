package org.joseph.friendsync.home.impl.navigation

internal sealed class HomeScreenRouter {

    data class PostDetails(
        val postId: Int,
        val shouldShowAddCommentDialog: Boolean
    ) : HomeScreenRouter()

    data class UserProfile(val userId: Int) : HomeScreenRouter()

    data object Unknown : HomeScreenRouter()
}
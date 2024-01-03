package org.joseph.friendsync.profile.impl.navigation

internal sealed class ProfileScreenRouter {

    data object EditProfile : ProfileScreenRouter()

    data object Unknown : ProfileScreenRouter()

    data class PostDetails(
        val postId: Int,
        val shouldShowAddCommentDialog: Boolean
    ) : ProfileScreenRouter()

    data class UserProfile(val userId: Int) : ProfileScreenRouter()
}
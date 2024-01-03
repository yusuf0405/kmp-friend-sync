package org.joseph.friendsync.post.impl.navigation

internal sealed class PostScreenRouter {

    data class UserProfile(val userId: Int) : PostScreenRouter()

    data object Unknown : PostScreenRouter()
}
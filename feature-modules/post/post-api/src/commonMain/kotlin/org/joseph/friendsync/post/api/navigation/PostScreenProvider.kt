package org.joseph.friendsync.post.api.navigation

import cafe.adriel.voyager.core.registry.ScreenProvider

sealed class PostScreenProvider : ScreenProvider {

    data class PostDetails(
        val id: Int,
        val shouldShowAddCommentDialog: Boolean = false
    ) : PostScreenProvider()
}
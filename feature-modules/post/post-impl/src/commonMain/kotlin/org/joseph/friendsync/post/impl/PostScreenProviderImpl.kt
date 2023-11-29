package org.joseph.friendsync.post.impl

import cafe.adriel.voyager.core.screen.Screen
import org.joseph.friendsync.post.api.PostScreenProvider

class PostScreenProviderImpl : PostScreenProvider {

    override fun postDetailsScreen(postId: Int, shouldShowAddCommentDialog: Boolean): Screen {
        return PostScreenDestination(
            postId = postId,
            shouldShowAddCommentDialog = shouldShowAddCommentDialog
        )
    }
}
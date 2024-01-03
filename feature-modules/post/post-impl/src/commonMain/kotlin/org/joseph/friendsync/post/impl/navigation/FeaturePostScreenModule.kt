package org.joseph.friendsync.post.impl.navigation

import cafe.adriel.voyager.core.registry.screenModule
import org.joseph.friendsync.post.api.navigation.PostScreenProvider
import org.joseph.friendsync.post.impl.PostScreenDestination

val featurePostScreenModule = screenModule {
    register<PostScreenProvider.PostDetails> { provider ->
        PostScreenDestination(
            postId = provider.id,
            shouldShowAddCommentDialog = provider.shouldShowAddCommentDialog
        )
    }
}
package org.joseph.friendsync.post.api

import cafe.adriel.voyager.core.screen.Screen

interface PostScreenProvider {

    fun postDetailsScreen(postId: Int, shouldShowAddCommentDialog: Boolean = false): Screen
}
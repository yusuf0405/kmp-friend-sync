package org.joseph.friendsync.home.impl.di

interface HomeFeatureDependencies {

    fun getProfileRoute(userId: Int): String

    fun getChatRoute(): String

    fun getPostRoute(postId: Int, shouldShowAddCommentDialog: Boolean): String
}
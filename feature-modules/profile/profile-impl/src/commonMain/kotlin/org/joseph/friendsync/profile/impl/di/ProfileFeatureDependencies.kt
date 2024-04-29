package org.joseph.friendsync.profile.impl.di

interface ProfileFeatureDependencies {

    fun getPostRoute(postId: Int, shouldShowAddCommentDialog: Boolean): String
}
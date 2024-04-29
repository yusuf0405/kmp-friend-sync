package org.joseph.friendsync.search.impl.di

interface SearchFeatureDependencies {

    fun getPostRoute(postId: Int, shouldShowAddCommentDialog: Boolean): String

    fun getProfileRoute(userId: Int): String

    fun getEditProfileRoute(): String
}
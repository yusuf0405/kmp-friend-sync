package org.joseph.friendsync.di

import org.joseph.friendsync.add.post.impl.AddPostFeatureImpl
import org.joseph.friendsync.auth.impl.AuthFeatureImpl
import org.joseph.friendsync.chat.impl.ChatFeatureImpl
import org.joseph.friendsync.core.extensions.routeWithParam
import org.joseph.friendsync.core.extensions.routeWithParams
import org.joseph.friendsync.home.impl.HomeFeatureImpl
import org.joseph.friendsync.post.impl.PostFeatureImpl
import org.joseph.friendsync.profile.impl.CurrentUserProfileFeatureImpl
import org.joseph.friendsync.profile.impl.screens.edit.profile.EditProfileFeatureImpl
import org.joseph.friendsync.profile.impl.screens.profile.ProfileFeatureImpl
import org.joseph.friendsync.search.impl.SearchFeatureImpl
import org.joseph.friendsync.splash.SplashScreenDestination

object DependencyProvider {

    fun splashFeature(): String = SplashScreenDestination.splashRoute

    fun authFeature(): String = AuthFeatureImpl.authRoute

    fun homeFeature(): String = HomeFeatureImpl.homeRoute

    fun chatFeature(): String = ChatFeatureImpl.chatRoute

    fun profileFeature(userId: Int): String = ProfileFeatureImpl.profileRoute.routeWithParam(userId)

    fun currentUserProfileFeature(): String = CurrentUserProfileFeatureImpl.profileRoute

    fun editProfileFeature(): String = EditProfileFeatureImpl.profileRoute

    fun postFeature(
        postId: Int,
        shouldShowAddCommentDialog: Boolean
    ): String = PostFeatureImpl.postRoute.routeWithParams(postId, shouldShowAddCommentDialog)

    fun addPostFeature(): String = AddPostFeatureImpl.addPostRoute

    fun searchFeature(): String = SearchFeatureImpl.searchRoute
}
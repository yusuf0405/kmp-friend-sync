package org.joseph.friendsync.di

import org.joseph.friendsync.add.post.impl.AddPostFeatureImpl
import org.joseph.friendsync.auth.impl.AuthFeatureImpl
import org.joseph.friendsync.chat.impl.ChatFeatureImpl
import org.joseph.friendsync.home.impl.HomeFeatureImpl
import org.joseph.friendsync.post.impl.PostFeatureImpl
import org.joseph.friendsync.profile.impl.CurrentUserProfileFeatureImpl
import org.joseph.friendsync.profile.impl.screens.edit.profile.EditProfileFeatureImpl
import org.joseph.friendsync.profile.impl.screens.profile.ProfileFeatureImpl
import org.joseph.friendsync.search.impl.SearchFeatureImpl
import org.joseph.friendsync.splash.SplashScreenDestination
import org.koin.core.qualifier.StringQualifier
import org.koin.core.qualifier.qualifier
import org.koin.dsl.module

val FEATURE_API_MODULES: StringQualifier = qualifier(name = "featureApiModules")

internal val featureApiModule = module {
    single(qualifier = FEATURE_API_MODULES) {
        listOf(
            SplashScreenDestination,
            HomeFeatureImpl,
            CurrentUserProfileFeatureImpl,
            ProfileFeatureImpl,
            PostFeatureImpl,
            AddPostFeatureImpl,
            SearchFeatureImpl,
            EditProfileFeatureImpl,
            AuthFeatureImpl,
            ChatFeatureImpl
        )
    }
}
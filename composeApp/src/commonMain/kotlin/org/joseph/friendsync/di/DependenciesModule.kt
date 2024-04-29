package org.joseph.friendsync.di

import org.joseph.friendsync.auth.impl.AuthFeatureDependencies
import org.joseph.friendsync.di.DependencyProvider.postFeature
import org.joseph.friendsync.di.DependencyProvider.profileFeature
import org.joseph.friendsync.di.DependencyProvider.searchFeature
import org.joseph.friendsync.home.impl.di.HomeFeatureDependencies
import org.joseph.friendsync.post.impl.di.PostFeatureDependencies
import org.joseph.friendsync.profile.impl.di.ProfileFeatureDependencies
import org.joseph.friendsync.search.impl.di.SearchFeatureDependencies
import org.koin.dsl.module

internal val featureDependencyModule = module {
    single<HomeFeatureDependencies> {
        object : HomeFeatureDependencies {
            override fun getProfileRoute(userId: Int): String = profileFeature(userId)
            override fun getChatRoute(): String = DependencyProvider.chatFeature()
            override fun getPostRoute(postId: Int, shouldShowAddCommentDialog: Boolean): String =
                postFeature(postId, shouldShowAddCommentDialog)
        }
    }
    single<PostFeatureDependencies> {
        object : PostFeatureDependencies {
            override fun getProfileRoute(userId: Int): String = searchFeature()
        }
    }
    single<SearchFeatureDependencies> {
        object : SearchFeatureDependencies {
            override fun getPostRoute(postId: Int, shouldShowAddCommentDialog: Boolean): String =
                postFeature(postId, shouldShowAddCommentDialog)
            override fun getProfileRoute(userId: Int): String = profileFeature(userId)
            override fun getEditProfileRoute(): String = DependencyProvider.editProfileFeature()
        }
    }

    single<AuthFeatureDependencies> {
        object : AuthFeatureDependencies {
            override fun getHomeRoute(): String = DependencyProvider.homeFeature()
        }
    }
    single<ProfileFeatureDependencies> {
        object : ProfileFeatureDependencies {
            override fun getPostRoute(postId: Int, shouldShowAddCommentDialog: Boolean): String =
                postFeature(postId, shouldShowAddCommentDialog)
        }
    }
}
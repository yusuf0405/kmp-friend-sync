package org.joseph.friendsync.data.di

import org.joseph.friendsync.data.cloud.source.auth.AuthDataSource
import org.joseph.friendsync.data.cloud.source.auth.AuthDataSourceImpl
import org.joseph.friendsync.data.cloud.source.category.CategoryCloudDataSource
import org.joseph.friendsync.data.cloud.source.category.CategoryCloudDataSourceImpl
import org.joseph.friendsync.data.cloud.source.comments.CommentsCloudDataSource
import org.joseph.friendsync.data.cloud.source.comments.CommentsCloudDataSourceImpl
import org.joseph.friendsync.data.cloud.source.like.PostLikeCloudDataSource
import org.joseph.friendsync.data.cloud.source.like.PostLikeCloudDataSourceImpl
import org.joseph.friendsync.data.cloud.source.posts.PostsCloudDataSource
import org.joseph.friendsync.data.cloud.source.posts.PostsCloudDataSourceImpl
import org.joseph.friendsync.data.cloud.source.posts.PostsReadCloudDataSource
import org.joseph.friendsync.data.cloud.source.posts.PostsReadCloudDataSourceImpl
import org.joseph.friendsync.data.cloud.source.subscriptions.SubscriptionCloudDataSource
import org.joseph.friendsync.data.cloud.source.subscriptions.SubscriptionCloudDataSourceImpl
import org.joseph.friendsync.data.cloud.source.users.UsersCloudDataSource
import org.joseph.friendsync.data.cloud.source.users.UsersCloudDataSourceImpl
import org.joseph.friendsync.data.local.source.comments.CommentsAddLocalDataSource
import org.joseph.friendsync.data.local.source.comments.CommentsAddLocalDataSourceImpl
import org.joseph.friendsync.data.local.source.comments.CommentsDeleteLocalDataSource
import org.joseph.friendsync.data.local.source.comments.CommentsDeleteLocalDataSourceImpl
import org.joseph.friendsync.data.local.source.comments.CommentsEditLocalDataSource
import org.joseph.friendsync.data.local.source.comments.CommentsEditLocalDataSourceImpl
import org.joseph.friendsync.data.local.source.comments.CommentsReadLocalDataSource
import org.joseph.friendsync.data.local.source.comments.CommentsReadLocalDataSourceImpl
import org.joseph.friendsync.data.local.source.liked.post.PostLikeLocalDataSource
import org.joseph.friendsync.data.local.source.liked.post.PostLikeLocalDataSourceImpl
import org.joseph.friendsync.data.local.source.posts.PostAddLocalDataSource
import org.joseph.friendsync.data.local.source.posts.PostAddLocalDataSourceImpl
import org.joseph.friendsync.data.local.source.posts.PostDeleteLocalDataSource
import org.joseph.friendsync.data.local.source.posts.PostDeleteLocalDataSourceImpl
import org.joseph.friendsync.data.local.source.posts.PostReadLocalDataSource
import org.joseph.friendsync.data.local.source.posts.PostReadLocalDataSourceImpl
import org.joseph.friendsync.data.local.source.subscriptions.SubscriptionsLocalDataSource
import org.joseph.friendsync.data.local.source.subscriptions.SubscriptionsLocalDataSourceImpl
import org.joseph.friendsync.data.local.source.subscriptions.SubscriptionsReadLocalDataSource
import org.joseph.friendsync.data.local.source.subscriptions.SubscriptionsReadLocalDataSourceImpl
import org.joseph.friendsync.data.local.source.users.UserLocalDataSource
import org.joseph.friendsync.data.local.source.users.UserLocalDataSourceImpl
import org.joseph.friendsync.data.local.source.users.UserReadLocalDataSource
import org.joseph.friendsync.data.local.source.users.UserReadLocalDataSourceImpl
import org.koin.dsl.module

private val localDataSourceModule = module {
    single<CommentsReadLocalDataSource> { CommentsReadLocalDataSourceImpl(get(), get(), get()) }
    single<CommentsAddLocalDataSource> { CommentsAddLocalDataSourceImpl(get(), get(), get()) }
    single<CommentsDeleteLocalDataSource> { CommentsDeleteLocalDataSourceImpl(get(), get()) }
    single<CommentsEditLocalDataSource> { CommentsEditLocalDataSourceImpl(get(), get()) }
    single<PostReadLocalDataSource> { PostReadLocalDataSourceImpl(get(), get(), get()) }
    single<PostAddLocalDataSource> { PostAddLocalDataSourceImpl(get(), get(), get()) }
    single<PostDeleteLocalDataSource> { PostDeleteLocalDataSourceImpl(get(), get()) }
    single<UserReadLocalDataSource> { UserReadLocalDataSourceImpl(get(), get(), get()) }
    single<UserLocalDataSource> { UserLocalDataSourceImpl(get(), get(), get(), get()) }
    single<PostLikeLocalDataSource> { PostLikeLocalDataSourceImpl(get(), get(), get(), get()) }
    single<SubscriptionsLocalDataSource> { SubscriptionsLocalDataSourceImpl(get(), get(), get()) }
    single<SubscriptionsReadLocalDataSource> {
        SubscriptionsReadLocalDataSourceImpl(get(), get(), get())
    }
}

private val cloudDataSourceModule = module {
    single<CommentsCloudDataSource> { CommentsCloudDataSourceImpl(get(), get(), get()) }
    single<PostsReadCloudDataSource> { PostsReadCloudDataSourceImpl(get(), get(), get()) }
    single<PostsCloudDataSource> { PostsCloudDataSourceImpl(get(), get(), get()) }
    single<AuthDataSource> { AuthDataSourceImpl(get(), get()) }
    single<CategoryCloudDataSource> { CategoryCloudDataSourceImpl(get(), get(), get()) }
    single<UsersCloudDataSource> {
        UsersCloudDataSourceImpl(get(), get(), get(), get(), get(), get())
    }
    single<PostLikeCloudDataSource> { PostLikeCloudDataSourceImpl(get(), get(), get()) }
    single<SubscriptionCloudDataSource> { SubscriptionCloudDataSourceImpl(get(), get(), get()) }
}

internal val dataSourceModule = listOf(localDataSourceModule, cloudDataSourceModule)
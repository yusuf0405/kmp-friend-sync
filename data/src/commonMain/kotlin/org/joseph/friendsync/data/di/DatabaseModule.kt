package org.joseph.friendsync.data.di

import org.joseph.friendsync.domain.UserDataStore
import org.joseph.friendsync.data.database.AppDatabase
import org.joseph.friendsync.data.local.dao.comments.CommentsDao
import org.joseph.friendsync.data.local.dao.liked.post.LikePostDao
import org.joseph.friendsync.data.local.dao.post.PostDao
import org.joseph.friendsync.data.local.dao.post.RecommendedPostDao
import org.joseph.friendsync.data.local.dao.subscriptions.SubscriptionsDao
import org.joseph.friendsync.data.local.dao.user.CurrentUserDao
import org.joseph.friendsync.data.local.dao.user.UserDao
import org.koin.dsl.module

internal val databaseModule = module {
    single { DatabaseFactory(get()) }
    single<AppDatabase> { get<DatabaseFactory>().createRoomDatabase() }
    single<UserDataStore> { get<DatabaseFactory>().createUserDataStore() }

    single<RecommendedPostDao> { get<AppDatabase>().recommendedPostDao() }
    single<PostDao> { get<AppDatabase>().postDao() }
    single<UserDao> { get<AppDatabase>().userDao() }
    single<CurrentUserDao> { get<AppDatabase>().currentUserDao() }
    single<LikePostDao> { get<AppDatabase>().likePostDao() }
    single<SubscriptionsDao> { get<AppDatabase>().subscriptionsDao() }
    single<CommentsDao> { get<AppDatabase>().commentsDao() }
}
package org.joseph.friendsync.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import org.joseph.friendsync.data.local.dao.comments.CommentsDao
import org.joseph.friendsync.data.local.dao.liked.post.LikePostDao
import org.joseph.friendsync.data.local.dao.post.PostDao
import org.joseph.friendsync.data.local.dao.post.RecommendedPostDao
import org.joseph.friendsync.data.local.dao.subscriptions.SubscriptionsDao
import org.joseph.friendsync.data.local.dao.user.CurrentUserDao
import org.joseph.friendsync.data.local.dao.user.UserDao
import org.joseph.friendsync.data.local.models.CommentEntity
import org.joseph.friendsync.data.local.models.CurrentUserLocal
import org.joseph.friendsync.data.local.models.LikePostLocal
import org.joseph.friendsync.data.local.models.PostLocal
import org.joseph.friendsync.data.local.models.SubscriptionLocal
import org.joseph.friendsync.data.local.models.UserDetailLocal

internal const val dataBaseFileName = "friend-sync.db"
internal const val dataStoreFileName = "user.json"

@Database(
    entities = [
        CommentEntity::class,
        LikePostLocal::class,
        PostLocal::class,
        SubscriptionLocal::class,
        UserDetailLocal::class,
        CurrentUserLocal::class,
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun commentsDao(): CommentsDao
    abstract fun likePostDao(): LikePostDao
    abstract fun postDao(): PostDao
    abstract fun recommendedPostDao(): RecommendedPostDao
    abstract fun subscriptionsDao(): SubscriptionsDao
    abstract fun userDao(): UserDao
    abstract fun currentUserDao(): CurrentUserDao
}
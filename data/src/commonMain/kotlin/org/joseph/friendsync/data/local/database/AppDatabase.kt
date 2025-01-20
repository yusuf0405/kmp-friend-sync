package org.joseph.friendsync.data.local.database

import androidx.room.ConstructedBy
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
import org.joseph.friendsync.data.local.models.LikedPostLocal
import org.joseph.friendsync.data.local.models.PostEntity
import org.joseph.friendsync.data.local.models.SubscriptionLocal
import org.joseph.friendsync.data.local.models.UserDetailLocal
import androidx.room.RoomDatabaseConstructor

internal const val dataBaseFileName = "friend-sync.db"
internal const val dataStoreFileName = "user.json"

@Database(
    entities = [
        CommentEntity::class,
        LikedPostLocal::class,
        PostEntity::class,
        SubscriptionLocal::class,
        UserDetailLocal::class,
        CurrentUserLocal::class,
    ],
    version = 1
)
@ConstructedBy(DatabaseConstructor::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun commentsDao(): CommentsDao
    abstract fun likePostDao(): LikePostDao
    abstract fun postDao(): PostDao
    abstract fun recommendedPostDao(): RecommendedPostDao
    abstract fun subscriptionsDao(): SubscriptionsDao
    abstract fun userDao(): UserDao
    abstract fun currentUserDao(): CurrentUserDao
}
// The Room compiler generates the `actual` implementations.
@Suppress("NO_ACTUAL_FOR_EXPECT")
internal expect object DatabaseConstructor : RoomDatabaseConstructor<AppDatabase> {
    override fun initialize(): AppDatabase
}
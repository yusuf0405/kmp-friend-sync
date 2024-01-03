package org.joseph.friendsync.data.local.dao.liked.post

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.joseph.friendsync.common.util.coroutines.DispatcherProvider
import org.joseph.friendsync.data.local.mappers.LikedPostSqlToLikedPostLocalMapper
import org.joseph.friendsync.data.local.models.LikePostLocal
import org.joseph.friendsync.data.models.like.LikedPostCloud
import org.joseph.friendsync.database.AppDatabase

class LikePostDaoImpl(
    private val appDatabase: AppDatabase,
    private val dispatcherProvider: DispatcherProvider,
    private val likedPostSqlToLikedPostLocalMapper: LikedPostSqlToLikedPostLocalMapper
) : LikePostDao {

    private val likesPostQueries by lazy {
        appDatabase.likes_postQueries
    }

    override suspend fun getAllLikesPosts(userId: Int): List<LikePostLocal> {
        return likesPostQueries.allLikesPosts(userId.toLong()).executeAsList()
            .map(likedPostSqlToLikedPostLocalMapper::map)
    }

    override fun getAllLikesPostsReactive(userId: Int): Flow<List<LikePostLocal>> {
        return likesPostQueries.reactiveAllLikesPosts(userId.toLong())
            .asFlow()
            .mapToList(dispatcherProvider.io)
            .map { items -> items.map(likedPostSqlToLikedPostLocalMapper::map) }
    }

    override suspend fun getLikesPostById(id: Long): LikePostLocal? {
        val post = likesPostQueries.likesPostById(id).executeAsOneOrNull() ?: return null
        return likedPostSqlToLikedPostLocalMapper.map(post)
    }

    override suspend fun deleteLikesPostById(postId: Long) {
        likesPostQueries.deleteLikesPostByPostId(postId)
    }

    override suspend fun insertOrUpdateLikesPost(likePost: LikedPostCloud) {
        with(likePost) {
            likesPostQueries.insertOrUpdateLikesPost(
                id.toLong(),
                userId.toLong(),
                postId.toLong()
            )
        }
    }
}

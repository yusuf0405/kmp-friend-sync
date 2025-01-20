package org.joseph.friendsync.data.local.source.liked.post

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import org.joseph.friendsync.core.DispatcherProvider
import org.joseph.friendsync.data.local.dao.liked.post.LikePostDao
import org.joseph.friendsync.data.local.mappers.LikedPostLocalToDataMapper
import org.joseph.friendsync.data.mappers.LikedPostDataToLocalMapper
import org.joseph.friendsync.data.models.LikedPostData

internal class PostLikeLocalDataSourceImpl(
    private val likePostDao: LikePostDao,
    private val dispatcherProvider: DispatcherProvider,
    private val likedPostLocalToDataMapper: LikedPostLocalToDataMapper,
    private val likedPostDataToLocalMapper: LikedPostDataToLocalMapper
) : PostLikeLocalDataSource {

    override fun observeAllLikesPosts(userId: Int): Flow<List<LikedPostData>> = likePostDao
        .observeAllLikesPosts(userId)
        .map { likedPosts -> likedPosts.map(likedPostLocalToDataMapper::map) }
        .flowOn(dispatcherProvider.io)
        .catch {
            throw IllegalStateException("Failed to observe all liked posts from cache", it)
        }


    override suspend fun getAllLikesPosts(userId: Int): List<LikedPostData> {
        return withContext(dispatcherProvider.io) {
            try {
                likePostDao.getAllLikesPosts(userId).map(likedPostLocalToDataMapper::map)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                throw IllegalStateException("Failed to get all liked posts from cache", e)
            }
        }
    }

    override suspend fun getLikesPostById(id: Long): LikedPostData {
        return withContext(dispatcherProvider.io) {
            try {
                likedPostLocalToDataMapper.map(checkNotNull(likePostDao.getLikesPostById(id)))
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                throw IllegalStateException("Failed to get liked post from cache", e)
            }
        }
    }

    override suspend fun deleteLikesPostById(id: Long) {
        return withContext(dispatcherProvider.io) {
            try {
                likePostDao.deleteLikesPostById(id)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                throw IllegalStateException("Failed to delete liked post from cache", e)
            }
        }
    }

    override suspend fun addLikedPost(likePost: LikedPostData) {
        withContext(dispatcherProvider.io) {
            try {
                likePostDao.insertOrUpdateLikesPost(likedPostDataToLocalMapper.map(likePost))
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                throw IllegalStateException("Failed to add liked post from cache", e)
            }
        }
    }

    override suspend fun addLikedPosts(likePosts: List<LikedPostData>) {
        withContext(dispatcherProvider.io) {
            try {
                val likedPostsLocal = likePosts.map(likedPostDataToLocalMapper::map)
                likePostDao.insertOrUpdateLikesPosts(likedPostsLocal)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                throw IllegalStateException("Failed to add liked post from cache", e)
            }
        }
    }
}
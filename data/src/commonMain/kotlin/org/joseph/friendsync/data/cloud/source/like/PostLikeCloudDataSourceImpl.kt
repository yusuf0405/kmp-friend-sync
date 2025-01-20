package org.joseph.friendsync.data.cloud.source.like

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.withContext
import org.joseph.friendsync.core.DispatcherProvider
import org.joseph.friendsync.data.cloud.mappers.LikedPostCloudToDataMapper
import org.joseph.friendsync.data.cloud.service.like.PostLikeService
import org.joseph.friendsync.data.models.LikedPostData

internal class PostLikeCloudDataSourceImpl(
    private val postLikeService: PostLikeService,
    private val dispatcherProvider: DispatcherProvider,
    private val likedPostCloudToDataMapper: LikedPostCloudToDataMapper
) : PostLikeCloudDataSource {

    override suspend fun fetchLikedPosts(userId: Int): List<LikedPostData> {
        return withContext(dispatcherProvider.io) {
            try {
                val likedPosts = postLikeService.fetchLikedPosts(userId).data ?: emptyList()
                likedPosts.map(likedPostCloudToDataMapper::map)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                throw IllegalStateException(
                    "Failed to fetch liked posts from cloud ${e.stackTraceToString()}",
                    e
                )
            }
        }
    }

    override suspend fun likePost(userId: Int, postId: Int): LikedPostData {
        return withContext(dispatcherProvider.io) {
            try {
                val likedPost = postLikeService.likePost(userId, postId).data
                likedPostCloudToDataMapper.map(checkNotNull(likedPost))
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                throw IllegalStateException("Failed to like post from cloud", e)
            }
        }
    }

    override suspend fun unlikePost(userId: Int, postId: Int) {
        return withContext(dispatcherProvider.io) {
            try {
                postLikeService.unlikePost(userId, postId)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                throw IllegalStateException("Failed to unlike post from cloud", e)
            }
        }
    }
}
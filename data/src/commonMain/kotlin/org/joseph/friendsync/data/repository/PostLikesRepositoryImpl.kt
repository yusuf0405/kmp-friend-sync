package org.joseph.friendsync.data.repository

import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import org.joseph.friendsync.data.cloud.source.like.PostLikeCloudDataSource
import org.joseph.friendsync.data.local.source.liked.post.PostLikeLocalDataSource
import org.joseph.friendsync.data.local.source.posts.PostAddLocalDataSource
import org.joseph.friendsync.data.mappers.LikedPostDataToDomainMapper
import org.joseph.friendsync.domain.models.LikedPostDomain
import org.joseph.friendsync.domain.repository.PostLikesRepository

internal class PostLikesRepositoryImpl(
    private val postLikeCloudDataSource: PostLikeCloudDataSource,
    private val postLikeLocalDataSource: PostLikeLocalDataSource,
    private val postAddLocalDataSource: PostAddLocalDataSource,
    private val likedPostDataToDomainMapper: LikedPostDataToDomainMapper,
) : PostLikesRepository {

    override fun observeLikedPosts(userId: Int): Flow<List<LikedPostDomain>> {
        return postLikeLocalDataSource
            .observeAllLikesPosts(userId)
            .map { posts -> posts.map(likedPostDataToDomainMapper::map) }
    }

    override suspend fun fetchLikedPosts(userId: Int): List<LikedPostDomain> {
        val likedPostsData = postLikeCloudDataSource.fetchLikedPosts(userId)
        withContext(NonCancellable) {
            postLikeLocalDataSource.addLikedPosts(likedPostsData)
        }
        return likedPostsData.map(likedPostDataToDomainMapper::map)
    }

    override suspend fun likePost(userId: Int, postId: Int) {
        val likedPostsData = postLikeCloudDataSource.likePost(userId, postId)
        withContext(NonCancellable) {
            postAddLocalDataSource.incrementOrDecrementLikesCount(postId, true)
            postLikeLocalDataSource.addLikedPost(likedPostsData)
        }
    }

    override suspend fun unlikePost(userId: Int, postId: Int) {
        postLikeCloudDataSource.unlikePost(userId, postId)
        withContext(NonCancellable) {
            postAddLocalDataSource.incrementOrDecrementLikesCount(postId, false)
            postLikeLocalDataSource.deleteLikesPostById(postId.toLong())
        }
    }
}
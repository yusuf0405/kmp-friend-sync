package org.joseph.friendsync.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import org.joseph.friendsync.core.Result
import org.joseph.friendsync.core.DispatcherProvider
import org.joseph.friendsync.core.extensions.callSafe
import org.joseph.friendsync.core.map
import org.joseph.friendsync.data.local.dao.liked.post.LikePostDao
import org.joseph.friendsync.data.local.dao.post.PostDao
import org.joseph.friendsync.data.local.dao.post.RecommendedPostDao
import org.joseph.friendsync.data.mappers.LikedPostCloudToLikedPostDomainMapper
import org.joseph.friendsync.data.mappers.LikedPostLocalToLikedPostDomainMapper
import org.joseph.friendsync.data.service.LikePostService
import org.joseph.friendsync.domain.models.LikedPostDomain
import org.joseph.friendsync.domain.repository.PostLikesRepository

internal class PostLikesRepositoryImpl(
    private val likePostService: LikePostService,
    private val postDao: PostDao,
    private val likePostDao: LikePostDao,
    private val recommendedPostDao: RecommendedPostDao,
    private val dispatcherProvider: DispatcherProvider,
    private val likedPostCloudToLikedPostDomainMapper: LikedPostCloudToLikedPostDomainMapper,
    private val likedPostLocalToLikedPostDomainMapper: LikedPostLocalToLikedPostDomainMapper,
) : PostLikesRepository {

    override suspend fun fetchLikedPosts(
        userId: Int
    ): Result<List<LikedPostDomain>> = withContext(dispatcherProvider.io) {
        callSafe {
            when (val response = likePostService.fetchLikedPosts(userId)) {
                is Result.Success -> {
                    val postCloud = response.data?.data ?: return@callSafe Result.defaultError()
//                    postCloud.forEach { likePostDao.insertOrUpdateLikesPost(it) }
                    Result.Success(postCloud.map(likedPostCloudToLikedPostDomainMapper::map))
                }

                is Result.Error -> {
                    if (response.message != null) Result.Error(response.message!!)
                    else Result.defaultError()
                }
            }
        }
    }

    override fun observeLikedPosts(
        userId: Int
    ): Flow<List<LikedPostDomain>> = likePostDao.getAllLikesPostsReactive(userId).map { posts ->
        posts.map(likedPostLocalToLikedPostDomainMapper::map)
    }

    override suspend fun likePost(userId: Int, postId: Int): Result<Unit> {
        return withContext(dispatcherProvider.io) {
            val result = callSafe { likePostService.likePost(userId, postId) }
            val likedPostCloud = result.data?.data
            if (result.isSuccess() && likedPostCloud != null) {
                recommendedPostDao.incrementDecrementLikesCount(postId, true)
                postDao.incrementDecrementLikesCount(postId, true)
//                likePostDao.insertOrUpdateLikesPost(likedPostCloud)
            }
            result.map { }
        }
    }

    override suspend fun unlikePost(userId: Int, postId: Int): Result<Unit> {
        return withContext(dispatcherProvider.io) {
            val result = callSafe { likePostService.unlikePost(userId, postId) }
            if (result.isSuccess()) {
                recommendedPostDao.incrementDecrementLikesCount(postId, false)
                postDao.incrementDecrementLikesCount(postId, false)
                likePostDao.deleteLikesPostById(postId.toLong())
            }
            result.map { }
        }
    }
}
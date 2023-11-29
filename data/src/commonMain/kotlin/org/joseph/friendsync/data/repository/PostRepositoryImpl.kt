package org.joseph.friendsync.data.repository

import kotlinx.coroutines.withContext
import org.joseph.friendsync.common.util.Result
import org.joseph.friendsync.common.util.coroutines.DispatcherProvider
import org.joseph.friendsync.common.util.coroutines.callSafe
import org.joseph.friendsync.data.mappers.PostCloudToPostDomainMapper
import org.joseph.friendsync.data.models.post.RecommendedPostsParam
import org.joseph.friendsync.data.service.PostService
import org.joseph.friendsync.domain.models.PostDomain
import org.joseph.friendsync.domain.repository.PostRepository

internal class PostRepositoryImpl(
    private val postService: PostService,
    private val dispatcherProvider: DispatcherProvider,
    private val postCloudToPostDomainMapper: PostCloudToPostDomainMapper,
) : PostRepository {

    private val defaultErrorMessage = "Oops, we could not send your request, try later!"

    override suspend fun addPost(
        byteArray: List<ByteArray?>,
        message: String?,
        userId: Int
    ): Result<PostDomain> = withContext(dispatcherProvider.io) {
        callSafe(Result.Error(message = defaultErrorMessage)) {
            val response = postService.addPost(byteArray, message, userId)
            if (response.data == null) {
                Result.Error(
                    message = response.errorMessage ?: defaultErrorMessage
                )
            } else {
                Result.Success(data = postCloudToPostDomainMapper.map(response.data))
            }
        }
    }

    override suspend fun fetchPostById(
        postId: Int
    ): Result<PostDomain> = withContext(dispatcherProvider.io) {
        callSafe(Result.Error(message = defaultErrorMessage)) {
            val response = postService.fetchPostById(postId)
            if (response.data == null) {
                Result.Error(
                    message = response.errorMessage ?: defaultErrorMessage
                )
            } else {
                Result.Success(data = postCloudToPostDomainMapper.map(response.data))
            }
        }
    }

    override suspend fun fetchUserPosts(
        userId: Int
    ): Result<List<PostDomain>> = withContext(dispatcherProvider.io) {
        callSafe(Result.Error(message = defaultErrorMessage)) {
            val response = postService.fetchUserPosts(userId)
            if (response.data == null) {
                Result.Error(
                    message = response.errorMessage ?: defaultErrorMessage
                )
            } else {
                Result.Success(data = response.data.map(postCloudToPostDomainMapper::map))
            }
        }
    }

    override suspend fun fetchRecommendedPosts(
        page: Int,
        pageSize: Int,
        userId: Int
    ): Result<List<PostDomain>> = withContext(dispatcherProvider.io) {
        callSafe(Result.Error(message = defaultErrorMessage)) {
            val param = RecommendedPostsParam(page, pageSize, userId)
            val response = postService.fetchRecommendedPosts(param)
            if (response.data == null) {
                Result.Error(
                    message = response.errorMessage ?: defaultErrorMessage
                )
            } else {
                Result.Success(data = response.data.map(postCloudToPostDomainMapper::map))
            }
        }
    }
}
package org.joseph.friendsync.data.repository

import kotlinx.coroutines.withContext
import org.joseph.friendsync.common.util.Result
import org.joseph.friendsync.common.util.coroutines.DispatcherProvider
import org.joseph.friendsync.common.util.coroutines.callSafe
import org.joseph.friendsync.common.util.filterNotNullOrError
import org.joseph.friendsync.common.util.map
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

    override suspend fun addPost(
        byteArray: List<ByteArray?>,
        message: String?,
        userId: Int
    ): Result<PostDomain> = withContext(dispatcherProvider.io) {
        callSafe {
            val response = postService.addPost(byteArray, message, userId)
            if (response.data == null)
                Result.defaultError()
            else
                Result.Success(postCloudToPostDomainMapper.map(response.data))
        }
    }

    override suspend fun fetchPostById(
        postId: Int
    ): Result<PostDomain> = withContext(dispatcherProvider.io) {
        callSafe {
            postService.fetchPostById(postId).map { response ->
                response.data?.let(postCloudToPostDomainMapper::map)
            }.filterNotNullOrError()
        }
    }

    override suspend fun fetchUserPosts(
        userId: Int
    ): Result<List<PostDomain>> = withContext(dispatcherProvider.io) {
        callSafe {
            postService.fetchUserPosts(userId).map { response ->
                response.data?.map(postCloudToPostDomainMapper::map) ?: emptyList()
            }
        }
    }

    override suspend fun fetchRecommendedPosts(
        page: Int,
        pageSize: Int,
        userId: Int
    ): Result<List<PostDomain>> = withContext(dispatcherProvider.io) {
        callSafe {
            postService.fetchRecommendedPosts(page, pageSize, userId).map { response ->
                response.data?.map(postCloudToPostDomainMapper::map) ?: emptyList()
            }
        }
    }

    override suspend fun searchPosts(
        query: String,
        page: Int,
        pageSize: Int
    ): Result<List<PostDomain>> = withContext(dispatcherProvider.io) {
        callSafe {
            postService.searchPosts(query, page, pageSize).map { response ->
                response.data?.map(postCloudToPostDomainMapper::map) ?: emptyList()
            }
        }
    }
}
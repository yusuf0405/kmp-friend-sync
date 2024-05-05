package org.joseph.friendsync.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import org.joseph.friendsync.core.Result
import org.joseph.friendsync.core.DispatcherProvider
import org.joseph.friendsync.core.extensions.callSafe
import org.joseph.friendsync.core.map
import org.joseph.friendsync.data.local.dao.post.PostDao
import org.joseph.friendsync.data.local.dao.post.RecommendedPostDao
import org.joseph.friendsync.data.mappers.PostCloudToPostDomainMapper
import org.joseph.friendsync.data.mappers.PostLocalToPostDomainMapper
import org.joseph.friendsync.data.service.PostService
import org.joseph.friendsync.domain.models.PostDomain
import org.joseph.friendsync.domain.repository.PostRepository

internal class PostRepositoryImpl(
    private val postService: PostService,
    private val postDao: PostDao,
    private val recommendedPostDao: RecommendedPostDao,
    private val dispatcherProvider: DispatcherProvider,
    private val postCloudToPostDomainMapper: PostCloudToPostDomainMapper,
    private val postLocalToPostDomainMapper: PostLocalToPostDomainMapper,
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
            else {
//                postDao.insertOrUpdatePost(response.data)
                Result.Success(postCloudToPostDomainMapper.map(response.data))
            }
        }
    }

    override suspend fun fetchPostById(
        postId: Int
    ): Result<PostDomain> = withContext(dispatcherProvider.io) {
        callSafe {
            when (val response = postService.fetchPostById(postId)) {
                is Result.Success -> {
                    val postCloud = response.data?.data ?: return@callSafe Result.defaultError()
//                    postDao.insertOrUpdatePost(postCloud)
                    Result.Success(postCloudToPostDomainMapper.map(postCloud))
                }

                is Result.Error -> {
                    if (response.message != null) Result.Error(response.message!!)
                    else Result.defaultError()
                }
            }
        }
    }

    override suspend fun fetchUserPosts(
        userId: Int
    ): Result<List<PostDomain>> = withContext(dispatcherProvider.io) {
        callSafe {
            when (val response = postService.fetchUserPosts(userId)) {
                is Result.Success -> {
                    val postsCloud = response.data?.data ?: emptyList()
//                    postDao.insertOrUpdatePosts(postsCloud)
                    Result.Success(postsCloud.map(postCloudToPostDomainMapper::map))
                }

                is Result.Error -> {
                    if (response.message != null) Result.Error(response.message!!)
                    else Result.defaultError()
                }
            }
        }
    }

    override fun observeRecommendedPosts(): Flow<List<PostDomain>> =
        recommendedPostDao.getAllPostsReactive().map { posts ->
            posts.map(postLocalToPostDomainMapper::map)
        }

    override fun observePosts(): Flow<List<PostDomain>> =
        postDao.getAllPostsReactive().map { posts ->
            posts.map(postLocalToPostDomainMapper::map)
        }

    override fun observePost(postId: Int): Flow<PostDomain?> {
        return postDao.getPostReactive(postId).map { post ->
            if (post == null) return@map null
            postLocalToPostDomainMapper.map(post)
        }
    }

    override fun observeUserPosts(userId: Int): Flow<List<PostDomain>> =
        postDao.getUserPostsReactive(userId).map { posts ->
            posts.map(postLocalToPostDomainMapper::map)
        }

    override suspend fun fetchRecommendedPosts(
        page: Int,
        pageSize: Int,
        userId: Int
    ): Result<List<PostDomain>> = withContext(dispatcherProvider.io) {
        callSafe {
            when (val response = postService.fetchRecommendedPosts(page, pageSize, userId)) {
                is Result.Success -> {
                    val postsCloud = response.data?.data ?: emptyList()
//                    recommendedPostDao.insertOrUpdatePosts(postsCloud)
                    Result.Success(postsCloud.map(postCloudToPostDomainMapper::map))
                }

                is Result.Error -> {
                    if (response.message != null) Result.Error(response.message!!)
                    else Result.defaultError()
                }
            }
        }
    }

    override suspend fun searchPosts(
        query: String,
        page: Int,
        pageSize: Int
    ): Result<List<PostDomain>> = withContext(dispatcherProvider.io) {
        callSafe {
            when (val response = postService.searchPosts(query, page, pageSize)) {
                is Result.Success -> {
                    val postsCloud = response.data?.data ?: emptyList()
//                    postDao.insertOrUpdatePosts(postsCloud)
                    Result.Success(postsCloud.map(postCloudToPostDomainMapper::map))
                }

                is Result.Error -> {
                    if (response.message != null) Result.Error(response.message!!)
                    else Result.defaultError()
                }
            }
        }
    }

    override suspend fun removeAllPostsInLocalDb() {
        postDao.removeAllPosts()
    }

    override suspend fun removeAllRecommendedPostsInLocalDb() {
        recommendedPostDao.getAllPosts()
    }
}
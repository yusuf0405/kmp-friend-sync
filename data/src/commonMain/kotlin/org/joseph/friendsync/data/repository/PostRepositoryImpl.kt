package org.joseph.friendsync.data.repository

import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import org.joseph.friendsync.data.cloud.source.posts.PostsCloudDataSource
import org.joseph.friendsync.data.cloud.source.posts.PostsReadCloudDataSource
import org.joseph.friendsync.data.local.source.posts.PostAddLocalDataSource
import org.joseph.friendsync.data.local.source.posts.PostDeleteLocalDataSource
import org.joseph.friendsync.data.local.source.posts.PostReadLocalDataSource
import org.joseph.friendsync.data.mappers.PostDataToDomainMapper
import org.joseph.friendsync.domain.models.PostDomain
import org.joseph.friendsync.domain.repository.PostRepository

internal class PostRepositoryImpl(
    private val postAddLocalDataSource: PostAddLocalDataSource,
    private val postDeleteLocalDataSource: PostDeleteLocalDataSource,
    private val postReadLocalDataSource: PostReadLocalDataSource,
    private val postsCloudDataSource: PostsCloudDataSource,
    private val postsReadCloudDataSource: PostsReadCloudDataSource,
    private val postDataToDomainMapper: PostDataToDomainMapper,
) : PostRepository {

    override fun observeRecommendedPosts(): Flow<List<PostDomain>> = postReadLocalDataSource
        .observeRecommendedPosts()
        .map { posts -> posts.map(postDataToDomainMapper::map) }

    override fun observePosts(): Flow<List<PostDomain>> = postReadLocalDataSource
        .observeAllPosts()
        .map { posts -> posts.map(postDataToDomainMapper::map) }

    override fun observePost(postId: Int): Flow<PostDomain> = postReadLocalDataSource
        .observePost(postId)
        .map(postDataToDomainMapper::map)

    override fun observeUserPosts(userId: Int): Flow<List<PostDomain>> = postReadLocalDataSource
        .observeUserPosts(userId)
        .map { posts -> posts.map(postDataToDomainMapper::map) }

    override suspend fun addPost(
        byteArray: List<ByteArray?>,
        message: String?,
        userId: Int
    ): PostDomain {
        val postData = postsCloudDataSource.addPost(byteArray, message, userId)
        withContext(NonCancellable) {
            postAddLocalDataSource.addPost(postData, false)
        }
        return postDataToDomainMapper.map(postData)
    }

    override suspend fun fetchPostById(postId: Int): PostDomain {
        val postData = postsReadCloudDataSource.fetchPostById(postId)
        withContext(NonCancellable) {
            postAddLocalDataSource.addPost(postData, false)
        }
        return postDataToDomainMapper.map(postData)
    }

    override suspend fun fetchUserPosts(userId: Int): List<PostDomain> {
        val postsData = postsReadCloudDataSource.fetchUserPosts(userId)
        withContext(NonCancellable) {
            postAddLocalDataSource.addPosts(postsData, false)
        }
        return postsData.map(postDataToDomainMapper::map)
    }

    override suspend fun fetchRecommendedPosts(
        page: Int,
        pageSize: Int,
        userId: Int
    ): List<PostDomain> {
        val postsData = postsReadCloudDataSource.fetchRecommendedPosts(page, pageSize, userId)
        withContext(NonCancellable) {
            postAddLocalDataSource.addPosts(postsData, true)
        }
        return postsData.map(postDataToDomainMapper::map)
    }

    override suspend fun searchPosts(query: String, page: Int, pageSize: Int): List<PostDomain> {
        val postsData = postsCloudDataSource.searchPosts(query, page, pageSize)
        withContext(NonCancellable) {
            postAddLocalDataSource.addPosts(postsData, false)
        }
        return postsData.map(postDataToDomainMapper::map)
    }

    override suspend fun removeAllPostsInLocalDb() {
        postDeleteLocalDataSource.removeAllPosts()
    }
}
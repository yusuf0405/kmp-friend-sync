package org.joseph.friendsync.domain.repository

import kotlinx.coroutines.flow.Flow
import org.joseph.friendsync.core.Result
import org.joseph.friendsync.domain.models.PostDomain

interface PostRepository {

    fun observeRecommendedPosts(): Flow<List<PostDomain>>

    fun observePosts(): Flow<List<PostDomain>>

    fun observePost(postId: Int): Flow<PostDomain?>

    fun observeUserPosts(userId: Int): Flow<List<PostDomain>>

    suspend fun addPost(
        byteArray: List<ByteArray?>,
        message: String?,
        userId: Int,
    ): Result<PostDomain>

    suspend fun fetchPostById(postId: Int): Result<PostDomain>

    suspend fun fetchUserPosts(userId: Int): Result<List<PostDomain>>

    suspend fun fetchRecommendedPosts(
        page: Int,
        pageSize: Int,
        userId: Int
    ): Result<List<PostDomain>>

    suspend fun searchPosts(
        query: String,
        page: Int,
        pageSize: Int,
    ): Result<List<PostDomain>>

    suspend fun removeAllPostsInLocalDb()

    suspend fun removeAllRecommendedPostsInLocalDb()
}

package org.joseph.friendsync.data.local.dao.post

import kotlinx.coroutines.flow.Flow
import org.joseph.friendsync.data.local.models.PostLocal
import org.joseph.friendsync.data.models.post.PostCloud

interface PostDao {

    suspend fun getAllPosts(): List<PostLocal>

    suspend fun searchPosts(query: String): List<PostLocal>

    fun getAllPostsReactive(): Flow<List<PostLocal>>

    fun getPostReactive(postId: Int): Flow<PostLocal?>

    fun getUserPostsReactive(userId: Int): Flow<List<PostLocal>>

    suspend fun getPostById(id: Long): PostLocal?

    suspend fun deletePostById(id: Int)

    suspend fun insertOrUpdatePost(post: PostCloud)

    suspend fun insertOrUpdatePosts(posts: List<PostCloud>)

    suspend fun incrementDecrementCommentsCount(postId: Int, isIncrement: Boolean)

    suspend fun incrementDecrementLikesCount(postId: Int, isIncrement: Boolean)

    suspend fun removeAllPosts()
}
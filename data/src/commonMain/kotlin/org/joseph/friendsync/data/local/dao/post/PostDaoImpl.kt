package org.joseph.friendsync.data.local.dao.post

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import io.github.aakira.napier.Napier
import org.joseph.friendsync.database.AppDatabase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.withContext
import org.joseph.friendsync.common.util.coroutines.DispatcherProvider
import org.joseph.friendsync.data.local.mappers.PostSqlToPostLocalMapper
import org.joseph.friendsync.data.local.mappers.RecommendedPostSqlToPostLocalMapper
import org.joseph.friendsync.data.local.models.PostLocal
import org.joseph.friendsync.data.models.post.PostCloud

class PostDaoImpl(
    private val appDatabase: AppDatabase,
    private val dispatcherProvider: DispatcherProvider,
    private val postSqlToPostLocalMapper: PostSqlToPostLocalMapper
) : PostDao {

    private val postsQueries by lazy { appDatabase.postsQueries }
    private val postImageUrlsQueries by lazy { appDatabase.post_image_urlsQueries }

    override suspend fun getAllPosts(): List<PostLocal> = withContext(dispatcherProvider.io) {
        postsQueries
            .allPosts()
            .executeAsList()
            .map { postSqlToPostLocalMapper.map(it, getImageUrlsForPost(it.id)) }
    }

    override suspend fun searchPosts(
        query: String
    ): List<PostLocal> = withContext(dispatcherProvider.io) {
        postsQueries
            .searchPosts(query)
            .executeAsList()
            .map { postSqlToPostLocalMapper.map(it, getImageUrlsForPost(it.id)) }
    }

    override fun getAllPostsReactive(): Flow<List<PostLocal>> {
        return postsQueries.reactiveAllPosts()
            .asFlow()
            .mapToList(dispatcherProvider.io)
            .map { posts ->
                posts.map { post ->
                    postSqlToPostLocalMapper.map(post, getImageUrlsForPost(post.id))
                }
            }.flowOn(dispatcherProvider.io)
    }

    override fun getPostReactive(postId: Int): Flow<PostLocal?> {
        return postsQueries.postById(postId.toLong())
            .asFlow()
            .mapToList(dispatcherProvider.io)
            .map { posts ->
                val post = posts.firstOrNull { it.id == postId.toLong() } ?: return@map null
                postSqlToPostLocalMapper.map(post, getImageUrlsForPost(post.id))
            }.flowOn(dispatcherProvider.io)
    }

    override fun getUserPostsReactive(userId: Int): Flow<List<PostLocal>> {
        return postsQueries.reactiveUserPosts(userId.toLong())
            .asFlow()
            .mapToList(dispatcherProvider.io)
            .map { posts ->
                posts.map { post ->
                    postSqlToPostLocalMapper.map(post, getImageUrlsForPost(post.id))
                }
            }.flowOn(dispatcherProvider.io)
    }

    override suspend fun incrementDecrementLikesCount(
        postId: Int,
        isIncrement: Boolean
    ) = withContext(dispatcherProvider.io) {
        val countChange = if (isIncrement) 1 else -1
        postsQueries.incrementDecrementLikesCount(countChange.toLong(), postId.toLong())
    }

    override suspend fun removeAllPosts() = withContext(dispatcherProvider.io) {
        postsQueries.removeAllPosts()
        postImageUrlsQueries.removeAllpostImageUrls()
    }

    override suspend fun incrementDecrementCommentsCount(
        postId: Int,
        isIncrement: Boolean
    ) = withContext(dispatcherProvider.io) {
        val countChange = if (isIncrement) 1 else -1
        postsQueries.incrementDecrementCommentsCount(countChange.toLong(), postId.toLong())
    }

    override suspend fun getPostById(id: Long): PostLocal? = withContext(dispatcherProvider.io) {
        val postSql = postsQueries.postById(id).executeAsOneOrNull() ?: return@withContext null
        postSqlToPostLocalMapper.map(postSql, getImageUrlsForPost(postSql.id))
    }

    override suspend fun deletePostById(id: Int) {
        postsQueries.deletePostById(id.toLong())
        deleteImageUrlsForPost(id.toLong())
    }

    override suspend fun insertOrUpdatePost(post: PostCloud) = withContext(dispatcherProvider.io) {
        postsQueries.insertOrUpdatePost(
            post.id.toLong(),
            post.message,
            post.releaseDate,
            post.likesCount.toLong(),
            post.commentsCount.toLong(),
            post.savedCount.toLong(),
            post.user.id.toLong(),
            post.user.name,
            post.user.lastName,
            post.user.avatar,
            post.user.releaseDate,
        )
        post.imageUrls.forEach { url -> insertOrUpdateImageUrl(url, post.id) }
    }

    override suspend fun insertOrUpdatePosts(posts: List<PostCloud>) {
        posts.forEach { post -> insertOrUpdatePost(post) }
    }

    private suspend fun getImageUrlsForPost(
        postId: Long
    ): List<String> = withContext(dispatcherProvider.io) {
        postImageUrlsQueries
            .imageUrlsForPost(postId).executeAsList()
            .map { it.image_url }
    }

    private suspend fun deleteImageUrlsForPost(postId: Long) = withContext(dispatcherProvider.io) {
        postImageUrlsQueries.deleteImageUrlsForPost(postId)
    }

    private suspend fun insertOrUpdateImageUrl(
        imageUrl: String,
        postId: Int
    ) = withContext(dispatcherProvider.io) {
        postImageUrlsQueries.insertOrUpdateImageUrl(
            postId.toLong(),
            imageUrl
        )
    }
}

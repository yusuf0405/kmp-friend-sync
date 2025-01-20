package org.joseph.friendsync.data.local.source.posts

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import org.joseph.friendsync.core.DispatcherProvider
import org.joseph.friendsync.data.local.dao.post.PostDao
import org.joseph.friendsync.data.local.mappers.PostLocalToDataMapper
import org.joseph.friendsync.data.models.PostData

/**
 * Реализация локального источника данных для чтения постов из кэша.
 *
 * Класс предоставляет методы для поиска, получения и наблюдения за постами в кэше.
 *
 * @property postDao DAO для доступа к данным постов.
 * @property dispatcherProvider Провайдер диспетчеров для управления потоками выполнения.
 */
internal class PostReadLocalDataSourceImpl(
    private val postDao: PostDao,
    private val dispatcherProvider: DispatcherProvider,
    private val postLocalToDataMapper: PostLocalToDataMapper,
) : PostReadLocalDataSource {

    override fun observeAllPosts(): Flow<List<PostData>> = postDao
        .observeAllPosts()
        .map { posts -> posts.map(postLocalToDataMapper::map) }
        .flowOn(dispatcherProvider.io)
        .catch {
            throw IllegalStateException("Failed to observe all posts from cache", it)
        }

    override fun observeRecommendedPosts(): Flow<List<PostData>> = postDao
        .observeRecommendedPosts(true)
        .map { posts -> posts.map(postLocalToDataMapper::map) }
        .flowOn(dispatcherProvider.io)
        .catch {
            throw IllegalStateException("Failed to observe all posts from cache", it)
        }

    override fun observePost(postId: Int): Flow<PostData> = postDao
        .observePost(postId)
        .flowOn(dispatcherProvider.io)
        .map { it!! }
        .map(postLocalToDataMapper::map)
        .catch {
            throw IllegalStateException("Failed to observe post from cache", it)
        }

    override fun observeUserPosts(userId: Int): Flow<List<PostData>> = postDao
        .observeUserPosts(userId)
        .map { posts -> posts.map(postLocalToDataMapper::map) }
        .flowOn(dispatcherProvider.io)
        .catch {
            throw IllegalStateException("Failed to observe user posts from cache", it)
        }

    override suspend fun searchPosts(query: String): List<PostData> {
        return withContext(dispatcherProvider.io) {
            try {
                postDao.searchPosts(query).map(postLocalToDataMapper::map)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                throw IllegalStateException("Failed to search posts from cache", e)
            }
        }
    }

    override suspend fun getAllPosts(): List<PostData> {
        return withContext(dispatcherProvider.io) {
            try {
                postDao.getAllPosts().map(postLocalToDataMapper::map)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                throw IllegalStateException("Failed to get all posts from cache", e)
            }
        }
    }

    override suspend fun getPostById(postId: Long): PostData {
        return withContext(dispatcherProvider.io) {
            try {
                postLocalToDataMapper.map(checkNotNull(postDao.getPostById(postId)))
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                throw IllegalStateException("Failed to get post by id from cache", e)
            }
        }
    }
}
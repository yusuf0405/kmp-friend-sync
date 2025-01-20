package org.joseph.friendsync.data.local.source.posts

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.withContext
import org.joseph.friendsync.core.DispatcherProvider
import org.joseph.friendsync.data.local.dao.post.PostDao

/**
 * Реализация локального источника данных для удаления постов из кэша.
 *
 * Класс предоставляет методы для удаления одного поста по его идентификатору и удаления всех постов из кэша.
 *
 * @property postDao DAO для доступа к данным постов.
 * @property dispatcherProvider Провайдер диспетчеров для управления потоками выполнения.
 */
internal class PostDeleteLocalDataSourceImpl(
    private val postDao: PostDao,
    private val dispatcherProvider: DispatcherProvider
) : PostDeleteLocalDataSource {

    override suspend fun deletePostById(id: Int) {
        withContext(dispatcherProvider.io) {
            try {
                postDao.deletePostById(id)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                throw IllegalStateException("Failed to delete post from cache", e)
            }
        }
    }

    override suspend fun removeAllPosts() {
        withContext(dispatcherProvider.io) {
            try {
                postDao.removeAllPosts()
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                throw IllegalStateException("Failed to remove all posts from cache", e)
            }
        }
    }
}
package org.joseph.friendsync.data.local.source.posts

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.withContext
import org.joseph.friendsync.core.DispatcherProvider
import org.joseph.friendsync.data.local.dao.post.PostDao
import org.joseph.friendsync.data.mappers.PostDataToLocalMapper
import org.joseph.friendsync.data.models.PostData

/**
 * Реализация локального источника данных для добавления и изменения постов в кэше.
 *
 * Класс предоставляет методы для добавления одного или нескольких постов в кэш,
 * а также для инкремента или декремента счетчиков комментариев и лайков у постов в кэше.
 *
 * @property postDao DAO для доступа к данным постов.
 * @property dispatcherProvider Провайдер диспетчеров для управления потоками выполнения.
 */
internal class PostAddLocalDataSourceImpl(
    private val postDao: PostDao,
    private val dispatcherProvider: DispatcherProvider,
    private val postDataToLocalMapper: PostDataToLocalMapper,
) : PostAddLocalDataSource {

    override suspend fun addPost(post: PostData, isRecommended: Boolean) {
        return withContext(dispatcherProvider.io) {
            try {
                val postLocal = postDataToLocalMapper.map(post).copy(isRecommended = isRecommended)
                postDao.insertOrUpdatePost(postLocal)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                throw IllegalStateException("Failed to add post from cache", e)
            }
        }
    }

    override suspend fun addPosts(posts: List<PostData>, isRecommended: Boolean) {
        return withContext(dispatcherProvider.io) {
            try {
                val postsLocal = posts.map(postDataToLocalMapper::map).map { postsLocal ->
                    if (isRecommended) {
                        postsLocal.copy(isRecommended = true)
                    } else {
                        postsLocal
                    }
                }
                println("postsLocal: $postsLocal")
                postDao.insertOrUpdatePosts(postsLocal)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                throw IllegalStateException("Failed to add posts from cache", e)
            }
        }
    }

    override suspend fun incrementOrDecrementCommentsCount(postId: Int, isIncrement: Boolean) {
        return withContext(dispatcherProvider.io) {
            try {
                postDao.incrementOrDecrementCommentsCount(postId, isIncrement)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                throw IllegalStateException(
                    "Failed to increment or decrement comments count from cache",
                    e
                )
            }
        }
    }

    override suspend fun incrementOrDecrementLikesCount(postId: Int, isIncrement: Boolean) {
        return withContext(dispatcherProvider.io) {
            try {
                postDao.incrementOrDecrementLikesCount(postId, isIncrement)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                throw IllegalStateException(
                    "Failed to increment or decrement likes count from cache",
                    e
                )
            }
        }
    }
}
package org.joseph.friendsync.data.cloud.source.posts

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.withContext
import org.joseph.friendsync.core.DispatcherProvider
import org.joseph.friendsync.data.cloud.mappers.PostCloudToDataMapper
import org.joseph.friendsync.data.cloud.service.posts.PostService
import org.joseph.friendsync.data.models.PostData

/**
 * Реализация облачного источника данных для управления постами.
 *
 * Класс предоставляет методы для добавления нового поста и поиска постов через облачный сервис.
 *
 * @property postService Сервис для управления постами.
 * @property dispatcherProvider Провайдер диспетчеров для управления потоками выполнения.
 */
internal class PostsCloudDataSourceImpl(
    private val postService: PostService,
    private val dispatcherProvider: DispatcherProvider,
    private val postCloudToDataMapper: PostCloudToDataMapper,
) : PostsCloudDataSource {

    override suspend fun searchPosts(query: String, page: Int, pageSize: Int): List<PostData> {
        return withContext(dispatcherProvider.io) {
            try {
                val posts = postService.searchPosts(query, page, pageSize).data
                checkNotNull(posts).map(postCloudToDataMapper::map)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                throw IllegalStateException("Failed to search posts from cloud", e)
            }
        }
    }

    override suspend fun addPost(
        byteArrays: List<ByteArray?>,
        message: String?,
        userId: Int
    ): PostData {
        return withContext(dispatcherProvider.io) {
            try {
                val post = postService.addPost(byteArrays, message, userId).data
                postCloudToDataMapper.map(checkNotNull(post))
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                throw IllegalStateException("Failed to add post from cloud", e)
            }
        }
    }
}
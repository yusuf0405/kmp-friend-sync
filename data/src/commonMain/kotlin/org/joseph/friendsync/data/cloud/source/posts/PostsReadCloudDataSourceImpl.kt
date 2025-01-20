package org.joseph.friendsync.data.cloud.source.posts

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.withContext
import org.joseph.friendsync.core.DispatcherProvider
import org.joseph.friendsync.data.cloud.mappers.PostCloudToDataMapper
import org.joseph.friendsync.data.cloud.service.posts.PostService
import org.joseph.friendsync.data.models.PostData

/**
 * Реализация источника данных для чтения постов из облака.
 *
 * Класс предоставляет методы для получения постов пользователя, конкретного поста и рекомендованных постов из облака.
 *
 * @property postService Сервис для взаимодействия с API постов.
 * @property dispatcherProvider Провайдер диспетчеров для управления потоками.
 */
internal class PostsReadCloudDataSourceImpl(
    private val postService: PostService,
    private val dispatcherProvider: DispatcherProvider,
    private val postCloudToDataMapper: PostCloudToDataMapper,
) : PostsReadCloudDataSource {

    override suspend fun fetchUserPosts(userId: Int): List<PostData> {
        return withContext(dispatcherProvider.io) {
            try {
                val posts = postService.fetchUserPosts(userId).data
                checkNotNull(posts).map(postCloudToDataMapper::map)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                throw IllegalStateException("Failed to get user posts from cloud", e)
            }
        }
    }

    override suspend fun fetchPostById(postId: Int): PostData {
        return withContext(dispatcherProvider.io) {
            try {
                postCloudToDataMapper.map(checkNotNull(postService.fetchPostById(postId).data))
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                throw IllegalStateException("Failed to get post by id from cloud", e)
            }
        }
    }

    override suspend fun fetchRecommendedPosts(
        page: Int,
        pageSize: Int,
        userId: Int
    ): List<PostData> {
        return withContext(dispatcherProvider.io) {
            try {
                val posts = postService.fetchRecommendedPosts(page, pageSize, userId).data
                checkNotNull(posts).map(postCloudToDataMapper::map)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                throw IllegalStateException("Failed to get recommended posts from cloud", e)
            }
        }
    }
}
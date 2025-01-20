package org.joseph.friendsync.data.cloud.source.posts

import org.joseph.friendsync.data.cloud.models.PostCloud
import org.joseph.friendsync.data.models.PostData

internal interface PostsReadCloudDataSource {

    /**
     * Получает список постов пользователя.
     *
     * Выполняет сетевой запрос для получения всех постов пользователя с заданным идентификатором.
     *
     * @param userId Идентификатор пользователя, для которого нужно получить посты.
     * @return Список [PostData] с постами пользователя.
     * @throws IllegalStateException Если не удалось получить посты из облака.
     */
    suspend fun fetchUserPosts(userId: Int): List<PostData>

    /**
     * Получает пост по его идентификатору из облачного сервиса.
     *
     * @param postId Идентификатор поста, который нужно получить.
     * @return [PostData] с данными полученного поста.
     */
    @Throws(IllegalStateException::class)
    suspend fun fetchPostById(postId: Int): PostData

    /**
     * Получает список рекомендованных постов из облачного сервиса.
     *
     * @param page Номер страницы для пагинации.
     * @param pageSize Размер страницы для пагинации.
     * @param userId Идентификатор пользователя, для которого нужно получить рекомендации.
     * @return Список [PostData] с рекомендованными постами.
     */
    suspend fun fetchRecommendedPosts(page: Int, pageSize: Int, userId: Int): List<PostData>
}
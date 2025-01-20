package org.joseph.friendsync.data.cloud.service.posts

import org.joseph.friendsync.data.cloud.models.PostListResponse
import org.joseph.friendsync.data.cloud.models.PostResponse

internal interface PostService {

    /**
     * Получает список постов пользователя.
     *
     * Выполняет GET-запрос для получения всех постов пользователя с заданным идентификатором.
     *
     * @param userId Идентификатор пользователя, для которого нужно получить посты.
     * @return [PostListResponse] с полученными постами.
     */
    suspend fun fetchUserPosts(userId: Int): PostListResponse

    /**
     * Получает пост по его идентификатору.
     *
     * Выполняет GET-запрос для получения поста с заданным идентификатором.
     *
     * @param postId Идентификатор поста, который нужно получить.
     * @return [PostResponse] с данными полученного поста.
     */
    suspend fun fetchPostById(postId: Int): PostResponse

    /**
     * Получает список рекомендованных постов.
     *
     * Выполняет GET-запрос для получения рекомендованных постов с параметрами пагинации и идентификатором пользователя.
     *
     * @param page Номер страницы для пагинации.
     * @param pageSize Размер страницы для пагинации.
     * @param userId Идентификатор пользователя, для которого нужно получить рекомендации.
     * @return [PostListResponse] с рекомендованными постами.
     */
    suspend fun fetchRecommendedPosts(page: Int, pageSize: Int, userId: Int): PostListResponse

    /**
     * Ищет посты по запросу.
     *
     * Выполняет GET-запрос для поиска постов по заданному запросу с параметрами пагинации.
     *
     * @param query Строка запроса для поиска постов.
     * @param page Номер страницы для пагинации.
     * @param pageSize Размер страницы для пагинации.
     * @return [PostListResponse] с найденными постами.
     */
    suspend fun searchPosts(query: String, page: Int, pageSize: Int): PostListResponse

    /**
     * Добавляет новый пост.
     *
     * Выполняет POST-запрос для добавления нового поста с изображениями и сообщением.
     *
     * @param byteArrays Список байтовых массивов изображений.
     * @param message Сообщение поста.
     * @param userId Идентификатор пользователя, добавляющего пост.
     * @return [PostResponse] с данными добавленного поста.
     */
    suspend fun addPost(byteArrays: List<ByteArray?>, message: String?, userId: Int): PostResponse
}
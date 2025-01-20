package org.joseph.friendsync.data.cloud.source.posts

import org.joseph.friendsync.data.models.PostData

internal interface PostsCloudDataSource {

    /**
     * Ищет посты по запросу через облачный сервис.
     *
     * @param query Строка запроса для поиска постов.
     * @param page Номер страницы для пагинации.
     * @param pageSize Размер страницы для пагинации.
     * @return Список [PostData] с найденными постами.
     */
    suspend fun searchPosts(query: String, page: Int, pageSize: Int): List<PostData>

    /**
     * Добавляет новый пост через облачный сервис.
     *
     * @param byteArrays Список байтовых массивов изображений.
     * @param message Сообщение поста.
     * @param userId Идентификатор пользователя, добавляющего пост.
     * @return [PostData] с данными добавленного поста.
     */
    suspend fun addPost(byteArrays: List<ByteArray?>, message: String?, userId: Int): PostData
}
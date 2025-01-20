package org.joseph.friendsync.data.local.source.posts

import org.joseph.friendsync.data.models.PostData

internal interface PostAddLocalDataSource {

    /**
     * Добавляет пост в кэш или обновляет его данные.
     *
     * @param post Добавляемый пост.
     */
    suspend fun addPost(post: PostData, isRecommended: Boolean)

    /**
     * Добавляет несколько постов в кэш или обновляет их данные.
     *
     * @param posts Список добавляемых постов.
     */
    suspend fun addPosts(posts: List<PostData>, isRecommended: Boolean)

    /**
     * Инкрементирует или декрементирует счетчик комментариев у поста в кэше.
     *
     * @param postId Идентификатор поста.
     * @param isIncrement Флаг инкремента (true) или декремента (false).
     */
    suspend fun incrementOrDecrementCommentsCount(postId: Int, isIncrement: Boolean)

    /**
     * Инкрементирует или декрементирует счетчик лайков у поста в кэше.
     *
     * @param postId Идентификатор поста.
     * @param isIncrement Флаг инкремента (true) или декремента (false).
     */
    suspend fun incrementOrDecrementLikesCount(postId: Int, isIncrement: Boolean)
}
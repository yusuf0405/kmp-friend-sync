package org.joseph.friendsync.data.local.source.posts

import kotlinx.coroutines.flow.Flow
import org.joseph.friendsync.data.models.PostData

internal interface PostReadLocalDataSource {

    /**
     * Ищет посты по запросу в кэше.
     *
     * @param query Строка запроса для поиска постов.
     * @return Список [PostData] с найденными постами.
     */
    suspend fun searchPosts(query: String): List<PostData>

    /**
     * Получает все посты из кэша.
     *
     * @return Список [PostData] со всеми постами из кэша.
     */
    suspend fun getAllPosts(): List<PostData>

    /**
     * Получает пост по его идентификатору из кэша.
     *
     * @param postId Идентификатор поста, который нужно получить.
     * @return [PostData] с данными полученного поста.
     */
    suspend fun getPostById(postId: Long): PostData

    /**
     * Наблюдает за всеми постами в кэше.
     *
     * @return [Flow] с изменениями списка всех постов.
     */
    fun observeAllPosts(): Flow<List<PostData>>

    /**
     * Наблюдает за рекомендованными постами в кэше.
     *
     * @return [Flow] с изменениями списка всех постов.
     */
    fun observeRecommendedPosts(): Flow<List<PostData>>

    /**
     * Наблюдает за постом по его идентификатору в кэше.
     *
     * @param postId Идентификатор поста, за которым нужно наблюдать.
     * @return [Flow] с изменениями поста.
     */
    fun observePost(postId: Int): Flow<PostData>

    /**
     * Наблюдает за постами пользователя в кэше.
     *
     * @param userId Идентификатор пользователя, чьи посты нужно наблюдать.
     * @return [Flow] с изменениями списка постов пользователя.
     */
    fun observeUserPosts(userId: Int): Flow<List<PostData>>
}
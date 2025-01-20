package org.joseph.friendsync.data.local.source.posts

internal interface PostDeleteLocalDataSource {

    /**
     * Удаляет пост из кэша по его идентификатору.
     *
     * @param id Идентификатор удаляемого поста.
     */
    suspend fun deletePostById(id: Int)

    /** Удаляет все посты из кэша. */
    suspend fun removeAllPosts()
}
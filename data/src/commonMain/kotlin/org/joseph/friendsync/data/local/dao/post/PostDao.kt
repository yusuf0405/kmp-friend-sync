package org.joseph.friendsync.data.local.dao.post

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import org.joseph.friendsync.data.local.models.PostEntity

@Dao
interface PostDao {

    @Query("SELECT * FROM posts_table")
    suspend fun getAllPosts(): List<PostEntity>

    @Query("SELECT * FROM posts_table WHERE message LIKE '%' || :query || '%'")
    suspend fun searchPosts(query: String): List<PostEntity>

    @Query("SELECT * FROM posts_table")
    fun observeAllPosts(): Flow<List<PostEntity>>

    @Query("SELECT * FROM posts_table WHERE is_recommended = :isRecommended")
    fun observeRecommendedPosts(isRecommended: Boolean): Flow<List<PostEntity>>

    @Query("SELECT * FROM posts_table WHERE id = :postId")
    fun observePost(postId: Int): Flow<PostEntity?>

    @Query("SELECT * FROM posts_table WHERE user_id = :userId")
    fun observeUserPosts(userId: Int): Flow<List<PostEntity>>

    @Query("SELECT * FROM posts_table WHERE id = :id LIMIT 1")
    suspend fun getPostById(id: Long): PostEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdatePost(post: PostEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdatePosts(posts: List<PostEntity>)

    @Query("UPDATE posts_table SET comments_count = comments_count + CASE WHEN :isIncrement THEN 1 ELSE -1 END WHERE id = :postId")
    suspend fun incrementOrDecrementCommentsCount(postId: Int, isIncrement: Boolean)

    @Query("UPDATE posts_table SET likes_count = likes_count + CASE WHEN :isIncrement THEN 1 ELSE -1 END WHERE id = :postId")
    suspend fun incrementOrDecrementLikesCount(postId: Int, isIncrement: Boolean)

    @Query("DELETE FROM posts_table WHERE id = :id")
    suspend fun deletePostById(id: Int)

    @Query("DELETE FROM posts_table")
    suspend fun removeAllPosts()
}
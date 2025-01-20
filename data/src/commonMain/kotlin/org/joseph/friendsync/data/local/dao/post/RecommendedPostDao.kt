package org.joseph.friendsync.data.local.dao.post

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import org.joseph.friendsync.data.local.models.PostEntity

@Dao
interface RecommendedPostDao {

    @Query("SELECT * FROM posts_table")
    suspend fun getAllPosts(): List<PostEntity>

    @Query("SELECT * FROM posts_table")
    fun getAllPostsReactive(): Flow<List<PostEntity>>

    @Query("SELECT * FROM posts_table WHERE id = :id LIMIT 1")
    suspend fun getPostById(id: Long): PostEntity?

    @Query("DELETE FROM posts_table WHERE id = :id")
    suspend fun deletePostById(id: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdatePost(post: PostEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdatePosts(posts: List<PostEntity>)

    @Query("UPDATE posts_table SET comments_count = comments_count + CASE WHEN :isIncrement THEN 1 ELSE -1 END WHERE id = :postId")
    suspend fun incrementDecrementCommentsCount(postId: Int, isIncrement: Boolean)

    @Query("UPDATE posts_table SET likes_count = likes_count + CASE WHEN :isIncrement THEN 1 ELSE -1 END WHERE id = :postId")
    suspend fun incrementDecrementLikesCount(postId: Int, isIncrement: Boolean)

    @Query("DELETE FROM posts_table")
    suspend fun removeAllPosts()
}

package org.joseph.friendsync.data.local.dao.liked.post

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import org.joseph.friendsync.data.local.models.LikePostLocal

@Dao
interface LikePostDao {

    @Query("SELECT * FROM like_post_table WHERE user_id = :userId")
    suspend fun getAllLikesPosts(userId: Int): List<LikePostLocal>

    @Query("SELECT * FROM like_post_table WHERE user_id = :userId")
    fun getAllLikesPostsReactive(userId: Int): Flow<List<LikePostLocal>>

    @Query("SELECT * FROM like_post_table WHERE id = :id LIMIT 1")
    suspend fun getLikesPostById(id: Long): LikePostLocal?

    @Query("DELETE FROM like_post_table WHERE id = :id")
    suspend fun deleteLikesPostById(id: Long)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateLikesPost(likePost: LikePostLocal)
}
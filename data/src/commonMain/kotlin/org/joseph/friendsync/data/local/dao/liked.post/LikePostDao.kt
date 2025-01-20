package org.joseph.friendsync.data.local.dao.liked.post

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import org.joseph.friendsync.data.local.models.LikedPostLocal
import org.joseph.friendsync.data.local.models.PostEntity

@Dao
interface LikePostDao {

    @Query("SELECT * FROM like_post_table WHERE user_id = :userId")
    suspend fun getAllLikesPosts(userId: Int): List<LikedPostLocal>

    @Query("SELECT * FROM like_post_table WHERE user_id = :userId")
    fun observeAllLikesPosts(userId: Int): Flow<List<LikedPostLocal>>

    @Query("SELECT * FROM like_post_table WHERE id = :id LIMIT 1")
    suspend fun getLikesPostById(id: Long): LikedPostLocal?

    @Query("DELETE FROM like_post_table WHERE id = :id")
    suspend fun deleteLikesPostById(id: Long)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateLikesPost(likePost: LikedPostLocal)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateLikesPosts(posts: List<LikedPostLocal>)
}
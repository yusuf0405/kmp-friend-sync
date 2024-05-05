package org.joseph.friendsync.data.local.dao.user

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import org.joseph.friendsync.data.local.models.CurrentUserLocal

@Dao
interface CurrentUserDao {

    @Query("SELECT * FROM current_user_table WHERE id = :id")
    fun getCurrentUserReactive(id: Int): Flow<CurrentUserLocal>

    @Query("SELECT * FROM current_user_table WHERE id = :id LIMIT 1")
    suspend fun getUserById(id: Long): CurrentUserLocal

    @Query("DELETE FROM current_user_table WHERE id = :id")
    suspend fun deleteCurrentUserById(id: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateUser(user: CurrentUserLocal)

//    @Update
//    suspend fun editUserWithParams(params: EditProfileParams)

    @Query("UPDATE current_user_table SET followers_count = followers_count + CASE WHEN :isIncrement THEN 1 ELSE -1 END WHERE id = :id")
    suspend fun incrementDecrementFollowersCount(id: Int, isIncrement: Boolean)

    @Query("UPDATE current_user_table SET following_count = following_count + CASE WHEN :isIncrement THEN 1 ELSE -1 END WHERE id = :id")
    suspend fun incrementDecrementFollowingCount(id: Int, isIncrement: Boolean)
}
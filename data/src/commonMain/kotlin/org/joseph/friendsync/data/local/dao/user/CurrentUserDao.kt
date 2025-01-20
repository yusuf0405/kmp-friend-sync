package org.joseph.friendsync.data.local.dao.user

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import org.joseph.friendsync.data.local.models.CurrentUserLocal

@Dao
interface CurrentUserDao {

    @Query("SELECT * FROM current_user_table WHERE id = :id")
    fun observeCurrentUser(id: Int): Flow<CurrentUserLocal>

    @Query("SELECT * FROM current_user_table WHERE id = :id LIMIT 1")
    suspend fun getUserById(id: Long): CurrentUserLocal

    @Query("DELETE FROM current_user_table WHERE id = :id")
    suspend fun deleteCurrentUserById(id: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateUser(user: CurrentUserLocal)

    @Query("UPDATE current_user_table SET name = :name, last_name = :lastName, email = :email, bio = :bio, education = :education, avatar = :avatar WHERE id = :id")
    suspend fun editUserWithParams(
        id: Int,
        name: String,
        lastName: String,
        email: String,
        bio: String?,
        education: String?,
        avatar: String?
    )

    @Query("UPDATE current_user_table SET followers_count = followers_count + CASE WHEN :isIncrement THEN 1 ELSE -1 END WHERE id = :id")
    suspend fun incrementDecrementFollowersCount(id: Int, isIncrement: Boolean)

    @Query("UPDATE current_user_table SET following_count = following_count + CASE WHEN :isIncrement THEN 1 ELSE -1 END WHERE id = :id")
    suspend fun incrementDecrementFollowingCount(id: Int, isIncrement: Boolean)
}
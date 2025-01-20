package org.joseph.friendsync.data.local.dao.user

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import org.joseph.friendsync.data.local.models.UserDetailLocal

@Dao
interface UserDao {

    @Query("SELECT * FROM users_table")
    suspend fun getAllUsers(): List<UserDetailLocal>

    @Query("SELECT * FROM users_table WHERE name LIKE '%' || :query || '%' OR last_name LIKE '%' || :query || '%'")
    suspend fun searchUsers(query: String): List<UserDetailLocal>

    @Query("SELECT * FROM users_table")
    fun observeAllUsers(): Flow<List<UserDetailLocal>>

    @Query("SELECT * FROM users_table WHERE id = :id")
    fun observeUser(id: Int): Flow<UserDetailLocal?>

    @Query("SELECT * FROM users_table WHERE id = :id LIMIT 1")
    suspend fun getUserById(id: Long): UserDetailLocal?

    @Query("DELETE FROM users_table WHERE id = :id")
    suspend fun deleteUserById(id: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateUser(user: UserDetailLocal)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateUsers(users: List<UserDetailLocal>)

    @Query("UPDATE users_table SET followers_count = followers_count + CASE WHEN :isIncrement THEN 1 ELSE -1 END WHERE id = :id")
    suspend fun incrementDecrementFollowersCount(id: Int, isIncrement: Boolean)

    @Query("UPDATE users_table SET following_count = following_count + CASE WHEN :isIncrement THEN 1 ELSE -1 END WHERE id = :id")
    suspend fun incrementDecrementFollowingCount(id: Int, isIncrement: Boolean)

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
}
package org.joseph.friendsync.data.local.dao.user

import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.joseph.friendsync.data.local.models.PostLocal
import org.joseph.friendsync.data.local.models.UserDetailLocal
import org.joseph.friendsync.data.models.post.PostCloud
import org.joseph.friendsync.data.models.user.UserDetailCloud

@Dao
interface UserDao {

    @Query("SELECT * FROM users_table")
    suspend fun getAllUsers(): List<UserDetailLocal>

    @Query("SELECT * FROM users_table WHERE name LIKE '%' || :query || '%' OR last_name LIKE '%' || :query || '%'")
    suspend fun searchUsers(query: String): List<UserDetailLocal>

    @Query("SELECT * FROM users_table")
    fun getAllUsersReactive(): Flow<List<UserDetailLocal>>

    @Query("SELECT * FROM users_table WHERE id = :id")
    fun getUserReactive(id: Int): Flow<UserDetailLocal?>

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
}
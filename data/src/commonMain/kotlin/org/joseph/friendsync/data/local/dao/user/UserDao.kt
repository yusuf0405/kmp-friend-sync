package org.joseph.friendsync.data.local.dao.user

import kotlinx.coroutines.flow.Flow
import org.joseph.friendsync.data.local.models.PostLocal
import org.joseph.friendsync.data.local.models.UserDetailLocal
import org.joseph.friendsync.data.models.post.PostCloud
import org.joseph.friendsync.data.models.user.UserDetailCloud

interface UserDao {

    suspend fun getAllUsers(): List<UserDetailLocal>

    suspend fun searchUsers(query: String): List<UserDetailLocal>

    fun getAllUsersReactive(): Flow<List<UserDetailLocal>>

    fun getUserReactive(id: Int): Flow<UserDetailLocal?>

    suspend fun getUserById(id: Long): UserDetailLocal?

    suspend fun deleteUserById(id: Int)

    suspend fun insertOrUpdateUser(user: UserDetailCloud)

    suspend fun insertOrUpdateUsers(users: List<UserDetailCloud>)

    suspend fun incrementDecrementFollowersCount(id: Int, isIncrement: Boolean)

    suspend fun incrementDecrementFollowingCount(id: Int, isIncrement: Boolean)
}
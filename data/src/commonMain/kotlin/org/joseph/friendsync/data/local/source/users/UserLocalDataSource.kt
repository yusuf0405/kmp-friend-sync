package org.joseph.friendsync.data.local.source.users

import org.joseph.friendsync.data.models.UserDetailData

internal interface UserLocalDataSource {

    suspend fun searchUsers(query: String): List<UserDetailData>

    suspend fun deleteUserById(id: Int)

    suspend fun addUser(user: UserDetailData)

    suspend fun addUsers(users: List<UserDetailData>)

    suspend fun incrementDecrementFollowersCount(id: Int, isIncrement: Boolean)

    suspend fun incrementDecrementFollowingCount(id: Int, isIncrement: Boolean)
}
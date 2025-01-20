package org.joseph.friendsync.data.local.source.users

import kotlinx.coroutines.flow.Flow
import org.joseph.friendsync.data.models.UserDetailData

internal interface UserReadLocalDataSource {

    @Throws(IllegalStateException::class)
    fun observeAllUsers(): Flow<List<UserDetailData>>

    @Throws(IllegalStateException::class)
    fun observeUser(id: Int): Flow<UserDetailData>

    @Throws(IllegalStateException::class)
    suspend fun getAllUsers(): List<UserDetailData>

    @Throws(IllegalStateException::class)
    suspend fun getUserById(id: Long): UserDetailData
}
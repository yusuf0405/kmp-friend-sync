package org.joseph.friendsync.domain

import kotlinx.coroutines.flow.Flow
import org.joseph.friendsync.domain.models.UserPreferences

interface UserDataStore {

    suspend fun saveCurrentUser(user: UserPreferences)

    suspend fun removeCurrentUser(user: UserPreferences)

    suspend fun fetchCurrentUser(): UserPreferences

    fun observeCurrentUser(): Flow<UserPreferences>
}
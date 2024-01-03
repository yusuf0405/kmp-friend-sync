package org.joseph.friendsync.common.user

import kotlinx.coroutines.flow.Flow

interface UserDataStore {

    fun saveCurrentUser(user: UserPreferences)

    fun removeCurrentUser(user: UserPreferences)

    fun fetchCurrentUser(): UserPreferences

    fun observeCurrentUser(): Flow<UserPreferences>

    fun isUserAuthorized(): Boolean
}
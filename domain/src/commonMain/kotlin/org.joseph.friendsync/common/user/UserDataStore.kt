package org.joseph.friendsync.common.user

interface UserDataStore {

    fun saveCurrentUser(user: UserPreferences)

    fun removeCurrentUser(user: UserPreferences)

    fun fetchCurrentUser(): UserPreferences

    fun isUserAuthorized(): Boolean
}
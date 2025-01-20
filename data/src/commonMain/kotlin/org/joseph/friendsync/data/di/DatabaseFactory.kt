package org.joseph.friendsync.data.di

import org.joseph.friendsync.domain.UserDataStore
import org.joseph.friendsync.core.PlatformConfiguration
import org.joseph.friendsync.data.local.database.AppDatabase

expect class DatabaseFactory constructor(platformConfiguration: PlatformConfiguration) {
    fun createRoomDatabase(): AppDatabase
    fun createUserDataStore(): UserDataStore
}
package org.joseph.friendsync.data.di

import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.coroutines.Dispatchers
import org.joseph.friendsync.domain.UserDataStore
import org.joseph.friendsync.core.PlatformConfiguration
import org.joseph.friendsync.data.database.AppDatabase
import org.joseph.friendsync.data.database.UserDataStoreImpl
import org.joseph.friendsync.data.database.dataStoreFileName
import org.joseph.friendsync.data.database.dataBaseFileName

actual class DatabaseFactory actual constructor(
    private val platformConfiguration: PlatformConfiguration
) {
    private val app by lazy { platformConfiguration.androidContext }

    actual fun createRoomDatabase(): AppDatabase {
        val dbFile = app.getDatabasePath(dataBaseFileName)
        return Room.databaseBuilder<AppDatabase>(app, dbFile.absolutePath)
            .setDriver(BundledSQLiteDriver())
            .setQueryCoroutineContext(Dispatchers.IO)
            .build()
    }

    actual fun createUserDataStore(): UserDataStore {
        return UserDataStoreImpl {
            app.filesDir.resolve(dataStoreFileName).absolutePath
        }
    }
}

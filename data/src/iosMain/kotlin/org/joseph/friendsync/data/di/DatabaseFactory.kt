package org.joseph.friendsync.data.di

import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.joseph.friendsync.domain.UserDataStore
import org.joseph.friendsync.core.PlatformConfiguration
import org.joseph.friendsync.data.local.database.AppDatabase
import org.joseph.friendsync.data.local.database.UserDataStoreImpl
import org.joseph.friendsync.data.local.database.dataStoreFileName
import org.joseph.friendsync.data.local.database.dataBaseFileName
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSURL
import platform.Foundation.NSUserDomainMask

actual class DatabaseFactory actual constructor(platformConfiguration: PlatformConfiguration) {

    actual fun createRoomDatabase(): AppDatabase {
        val dbFile = "${fileDirectory()}/$dataBaseFileName"
        return Room.databaseBuilder<AppDatabase>(
            name = dbFile
        ).setDriver(BundledSQLiteDriver())
            .setQueryCoroutineContext(Dispatchers.IO)
            .build()
    }

    @OptIn(ExperimentalForeignApi::class)
    actual fun createUserDataStore(): UserDataStore {
        return UserDataStoreImpl {
            val documentDirectory: NSURL? = NSFileManager.defaultManager.URLForDirectory(
                directory = NSDocumentDirectory,
                inDomain = NSUserDomainMask,
                appropriateForURL = null,
                create = false,
                error = null,
            )
            requireNotNull(documentDirectory).path + "/$dataStoreFileName"
        }
    }

    @OptIn(ExperimentalForeignApi::class)
    private fun fileDirectory(): String {
        val documentDirectory: NSURL? = NSFileManager.defaultManager.URLForDirectory(
            directory = NSDocumentDirectory,
            inDomain = NSUserDomainMask,
            appropriateForURL = null,
            create = false,
            error = null,
        )
        return requireNotNull(documentDirectory).path!!
    }
}
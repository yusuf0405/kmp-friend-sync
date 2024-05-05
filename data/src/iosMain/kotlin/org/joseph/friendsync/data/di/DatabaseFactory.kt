package org.joseph.friendsync.data.di

import androidx.room.Room
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.coroutines.Dispatchers
import org.joseph.friendsync.domain.UserDataStore
import org.joseph.friendsync.core.PlatformConfiguration
import org.joseph.friendsync.data.database.AppDatabase
import org.joseph.friendsync.data.database.UserDataStoreImpl
import org.joseph.friendsync.data.database.dataStoreFileName
import org.joseph.friendsync.data.database.dataBaseFileName
import org.joseph.friendsync.data.database.instantiateImpl
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSHomeDirectory
import platform.Foundation.NSURL
import platform.Foundation.NSUserDomainMask

actual class DatabaseFactory actual constructor(platformConfiguration: PlatformConfiguration) {

    actual fun createRoomDatabase(): AppDatabase {
        val dbFilePath = NSHomeDirectory() + dataBaseFileName
        return Room.databaseBuilder<AppDatabase>(
            name = dbFilePath,
            factory = { AppDatabase::class.instantiateImpl() }
        ).setQueryCoroutineContext(Dispatchers.Default)
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
}
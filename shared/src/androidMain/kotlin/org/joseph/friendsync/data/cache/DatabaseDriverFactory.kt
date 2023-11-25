package org.joseph.friendsync.data.cache

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import org.joseph.friendsync.PlatformConfiguration
import org.joseph.friendsync.database.AppDatabase


actual class DatabaseDriverFactory actual constructor(
    private val platformConfiguration: PlatformConfiguration
) {

    actual fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(
            AppDatabase.Schema,
            platformConfiguration.androidContext,
            "app.db"
        )
    }
}
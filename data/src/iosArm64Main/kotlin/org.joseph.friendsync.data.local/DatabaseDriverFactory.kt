package org.joseph.friendsync.data.local

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import org.joseph.friendsync.PlatformConfiguration
import org.joseph.friendsync.database.AppDatabase

actual class DatabaseDriverFactory actual constructor(
    private val platformConfiguration: PlatformConfiguration
) {
    actual fun createDriver(): SqlDriver {
        return NativeSqliteDriver(AppDatabase.Schema, "appdatase.db")
    }
}
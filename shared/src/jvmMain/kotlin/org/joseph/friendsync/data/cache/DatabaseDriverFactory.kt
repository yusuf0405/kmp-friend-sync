package org.joseph.friendsync.data.cache

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import org.joseph.friendsync.PlatformConfiguration
import org.joseph.friendsync.database.AppDatabase

actual class DatabaseDriverFactory actual constructor(platformConfiguration: PlatformConfiguration) {

    actual fun createDriver(): SqlDriver {
        val driver: SqlDriver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
        AppDatabase.Schema.create(driver)
        return driver
    }
}
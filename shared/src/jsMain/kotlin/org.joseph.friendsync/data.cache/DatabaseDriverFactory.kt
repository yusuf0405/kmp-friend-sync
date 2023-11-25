package org.joseph.friendsync.data.cache

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.worker.WebWorkerDriver
import org.joseph.friendsync.PlatformConfiguration
import org.w3c.dom.Worker

actual class DatabaseDriverFactory actual constructor(platformConfiguration: PlatformConfiguration) {

    actual fun createDriver(): SqlDriver {
        return WebWorkerDriver(
            Worker(
                js("""new URL("@cashapp/sqldelight-sqljs-worker/sqljs.worker.js", import.meta.url)""")
            )
        )
    }
}
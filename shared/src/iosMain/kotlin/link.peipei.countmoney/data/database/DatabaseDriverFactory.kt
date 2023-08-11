package link.peipei.countmoney.data.database


import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import co.touchlab.sqliter.DatabaseConfiguration
import link.peipei.countmoney.AppDatabase
import kotlin.native.concurrent.isFrozen

actual class DatabaseDriverFactory {
    actual fun createDriver(): SqlDriver {
        return NativeSqliteDriver(
            AppDatabase.Schema,
            "AppDatabase.db",
            onConfiguration = { config ->
                config.copy(
                    extendedConfig = DatabaseConfiguration.Extended(foreignKeyConstraints = true)
                )
            }

        )
    }
}

actual fun getDatabaseDriverFactory(): DatabaseDriverFactory {
    return DatabaseDriverFactory()
}
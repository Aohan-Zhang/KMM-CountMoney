package link.peipei.countmoney.data.database


import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import link.peipei.countmoney.AppDatabase

actual class DatabaseDriverFactory {
    actual fun createDriver(): SqlDriver {
        return NativeSqliteDriver(AppDatabase.Schema, "AppDatabase.db")
    }
}

actual fun getDatabaseDriverFactory(): DatabaseDriverFactory {
    return DatabaseDriverFactory()
}
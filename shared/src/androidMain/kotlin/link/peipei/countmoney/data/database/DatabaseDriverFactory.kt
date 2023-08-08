package link.peipei.countmoney.data.database


import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import link.peipei.countmoney.App
import link.peipei.countmoney.AppDatabase


actual class DatabaseDriverFactory {
    actual fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(AppDatabase.Schema, App.getInstance(), "AppDatabase.db")
    }
}
actual fun getDatabaseDriverFactory(): DatabaseDriverFactory {
    return DatabaseDriverFactory()
}
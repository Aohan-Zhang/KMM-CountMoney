import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import link.peipei.countmoney.App

actual fun dataStore(): DataStore<Preferences> = getDataStore(
    producePath = { App.getInstance().filesDir.resolve(dataStoreFileName).absolutePath }
)
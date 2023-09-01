import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import kotlinx.atomicfu.locks.SynchronizedObject
import kotlinx.atomicfu.locks.synchronized
import okio.Path.Companion.toPath


private val lock = SynchronizedObject()

expect fun dataStore(): DataStore<Preferences>
fun getDataStore(producePath: () -> String): DataStore<Preferences> =
    synchronized(lock) {
        PreferenceDataStoreFactory.createWithPath(produceFile = { producePath().toPath() })
    }

internal const val dataStoreFileName = "dice.preferences_pb"
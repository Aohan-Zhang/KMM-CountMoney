package link.peipei.countmoney.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import link.peipei.countmoney.data.entities.TokenItem
import link.peipei.countmoney.data.entities.UserResponse

class UserManager(private val dataStore: DataStore<Preferences>) {

    companion object {
        val USER_NAME_KEY = stringPreferencesKey("USER_NAME_KEY")
        val USER_NICKNAME_KEY = stringPreferencesKey("USER_NICKNAME_KEY")
        val USER_USERID_KEY = stringPreferencesKey("USER_USERID_KEY")
        val USER_GENDER_KEY = stringPreferencesKey("USER_GENDER_KEY")
        val USER_BIRTHDATE_KEY = stringPreferencesKey("USER_BIRTHDATE_KEY")
        val USER_PICTURE_KEY = stringPreferencesKey("USER_PICTURE_KEY")
        val USER_PHONE_KEY = stringPreferencesKey("USER_PHONE_KEY")
        val USER_TOKEN_KEY = stringPreferencesKey("USER_TOKEN_KEY")
        val USER_STORE_ID_KEY = stringPreferencesKey("USER_STORE_KEY")
    }

    fun getLoggingStatusFlow() = dataStore.data.map {
        it[USER_USERID_KEY] != null
    }

    fun getUserStore() = dataStore.data.map {
        it[USER_STORE_ID_KEY]
    }

    fun getUser(): Flow<UserResponse> {
        return dataStore.data.map {
            UserResponse(
                it[USER_NAME_KEY] ?: "",
                it[USER_NICKNAME_KEY] ?: "",
                it[USER_USERID_KEY] ?: "",
                it[USER_GENDER_KEY] ?: "",
                it[USER_BIRTHDATE_KEY] ?: "",
                it[USER_PICTURE_KEY] ?: "",
                it[USER_PHONE_KEY]
            )
        }
            .filter {
                it.userId.isNotEmpty()
            }
    }

    suspend fun getAccessToken(): String? {
        val data = dataStore.data.firstOrNull()
        return data?.get(USER_TOKEN_KEY)
    }


    suspend fun updateCurrentUserStore(storeId: String) {
        dataStore.edit {
            it[USER_STORE_ID_KEY] = storeId
        }
    }

    suspend fun updateUser(userResponse: UserResponse, tokenItem: TokenItem) {
        with(userResponse) {
            dataStore.edit {
                it[USER_NAME_KEY] = name
                it[USER_NICKNAME_KEY] = nickname
                it[USER_USERID_KEY] = userId
                it[USER_GENDER_KEY] = gender
                it[USER_BIRTHDATE_KEY] = birthdate
                it[USER_PICTURE_KEY] = picture
                it[USER_PHONE_KEY] = phone ?: ""
                it[USER_TOKEN_KEY] = tokenItem.access_token
            }
        }
    }


    suspend fun logout() {
        dataStore.edit {
            it.clear()
        }
    }
}
package link.peipei.countmoney.data.repository

import kotlinx.coroutines.delay
import link.peipei.countmoney.data.api.CountingMoneyApi
import link.peipei.countmoney.data.entities.LoginRequestBody

class AccountRepository(private val api: CountingMoneyApi) {
    suspend fun login(phoneNumber: String, password: String): Boolean {
        return try {
            api.login(LoginRequestBody(phoneNumber, password))
            true
        } catch (e: Exception) {
            false
        }
    }

    suspend fun sendCode(phoneNumber: String): Boolean {
        delay(1000)
        return true
    }
}
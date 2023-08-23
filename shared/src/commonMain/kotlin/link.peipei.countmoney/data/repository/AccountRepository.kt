package link.peipei.countmoney.data.repository

import link.peipei.countmoney.data.api.CountingMoneyApi
import link.peipei.countmoney.data.entities.LoginRequestBody

class AccountRepository(private val api: CountingMoneyApi) {
    suspend fun login(account: String, password: String): Boolean {
        return try {
            api.login(LoginRequestBody(account, password))
            true
        } catch (e: Exception) {
            false
        }
    }
}
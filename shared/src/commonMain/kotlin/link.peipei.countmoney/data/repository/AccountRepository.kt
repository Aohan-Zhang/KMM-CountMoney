package link.peipei.countmoney.data.repository

import kotlinx.coroutines.delay
import link.peipei.countmoney.data.api.CountingMoneyApi
import link.peipei.countmoney.data.entities.LoginRequestBody
import link.peipei.countmoney.data.entities.SendSmsRequestBody

class AccountRepository(private val api: CountingMoneyApi) {
    suspend fun login(phoneNumber: String, passcode: String): Boolean {
        return try {
            api.login(LoginRequestBody(phoneNumber, passcode))
            true
        } catch (e: Exception) {
            false
        }
    }

    suspend fun sendCode(phoneNumber: String): Boolean {
        return try {
            api.sendSms(SendSmsRequestBody(phoneNumber))
            true
        } catch (e: Exception) {
            false
        }
    }
}
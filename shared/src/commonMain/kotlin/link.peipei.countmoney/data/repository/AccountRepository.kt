package link.peipei.countmoney.data.repository

import co.touchlab.kermit.Logger
import link.peipei.countmoney.data.UserManager
import link.peipei.countmoney.data.api.CountingMoneyApi
import link.peipei.countmoney.data.entities.LoginRequestBody
import link.peipei.countmoney.data.entities.SendSmsRequestBody

class AccountRepository(private val api: CountingMoneyApi, private val userManager: UserManager) {

    suspend fun login(phoneNumber: String, passcode: String): Boolean {
        return try {
            val tokenItem = api.login(LoginRequestBody(phoneNumber, passcode))
            val userResponse = api.getUser("Bearer ${tokenItem.id_token}")
            userManager.updateUser(userResponse.copy(phone = phoneNumber), tokenItem)
            return true
        } catch (e: Exception) {
            false
        }
    }

    suspend fun sendCode(phoneNumber: String): Boolean {
        return try {
            api.sendSms(SendSmsRequestBody(phoneNumber))
            true
        } catch (e: Exception) {
            Logger.w(e) {
                "send sms error"
            }
            false
        }
    }
}
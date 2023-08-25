package link.peipei.countmoney.data.api

import de.jensklingenberg.ktorfit.http.Body
import de.jensklingenberg.ktorfit.http.Headers
import de.jensklingenberg.ktorfit.http.POST
import link.peipei.countmoney.data.entities.LoginRequestBody
import link.peipei.countmoney.data.entities.SendSmsRequestBody
import link.peipei.countmoney.data.entities.TokenItem

interface CountingMoneyApi {
    @Headers("Content-Type:application/json")
    @POST("login")
    suspend fun login(@Body body: LoginRequestBody): TokenItem

    @Headers("Content-Type:application/json")
    @POST("sendSms")
    suspend fun sendSms(@Body body: SendSmsRequestBody): String
}
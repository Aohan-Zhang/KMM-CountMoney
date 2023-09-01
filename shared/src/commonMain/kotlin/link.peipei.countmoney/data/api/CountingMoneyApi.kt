package link.peipei.countmoney.data.api

import de.jensklingenberg.ktorfit.http.Body
import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.Header
import de.jensklingenberg.ktorfit.http.Headers
import de.jensklingenberg.ktorfit.http.POST
import link.peipei.countmoney.data.api.interceptor.AppInterceptor.Companion.LOGIN_PATH
import link.peipei.countmoney.data.api.interceptor.AppInterceptor.Companion.SEND_SMS_PATH
import link.peipei.countmoney.data.api.interceptor.AppInterceptor.Companion.USER_PATH
import link.peipei.countmoney.data.entities.LoginRequestBody
import link.peipei.countmoney.data.entities.SendSmsRequestBody
import link.peipei.countmoney.data.entities.TokenItem
import link.peipei.countmoney.data.entities.UserResponse

interface CountingMoneyApi {
    @Headers("Content-Type:application/json")
    @POST(LOGIN_PATH)
    suspend fun login(@Body body: LoginRequestBody): TokenItem

    @Headers("Content-Type:application/json")
    @POST(SEND_SMS_PATH)
    suspend fun sendSms(@Body body: SendSmsRequestBody): String

    @Headers("Content-Type:application/json")
    @GET(USER_PATH)
    suspend fun getUser(
        @Header("Authorization") token: String,
    ): UserResponse


}
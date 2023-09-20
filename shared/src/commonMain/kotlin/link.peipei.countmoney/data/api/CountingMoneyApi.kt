package link.peipei.countmoney.data.api

import de.jensklingenberg.ktorfit.http.Body
import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.Header
import de.jensklingenberg.ktorfit.http.Headers
import de.jensklingenberg.ktorfit.http.Multipart
import de.jensklingenberg.ktorfit.http.POST
import de.jensklingenberg.ktorfit.http.Part
import de.jensklingenberg.ktorfit.http.Query
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.http.content.PartData
import link.peipei.countmoney.data.api.interceptor.AppInterceptor.Companion.LOGIN_PATH
import link.peipei.countmoney.data.api.interceptor.AppInterceptor.Companion.SEND_SMS_PATH
import link.peipei.countmoney.data.api.interceptor.AppInterceptor.Companion.USER_PATH
import link.peipei.countmoney.data.entities.CreateEmployRequest
import link.peipei.countmoney.data.entities.EmployEntity
import link.peipei.countmoney.data.entities.LoginRequestBody
import link.peipei.countmoney.data.entities.SendSmsRequestBody
import link.peipei.countmoney.data.entities.StoreRequest
import link.peipei.countmoney.data.entities.StoreResponse
import link.peipei.countmoney.data.entities.TokenItem
import link.peipei.countmoney.data.entities.UploadImageData
import link.peipei.countmoney.data.entities.UserResponse

interface CountingMoneyApi {
    @Headers("Content-Type:application/json")
    @POST(LOGIN_PATH)
    suspend fun login(@Body body: LoginRequestBody): TokenItem

    @Headers("Content-Type:application/json")
    @POST(SEND_SMS_PATH)
    suspend fun sendSms(@Body body: SendSmsRequestBody): String

    @Headers("Content-Type:application/json")
    @POST("store")
    suspend fun createStore(@Body body: StoreRequest): StoreResponse

    @Headers("Content-Type:application/json")
    @GET("stores")
    suspend fun getStores(): List<StoreResponse>

    @Headers("Content-Type:application/json")
    @GET("employee")
    suspend fun getEmployee(
        @Query("storeId") storeId: String
    ): List<EmployEntity>

    @Headers("Content-Type:application/json")
    @POST("employee")
    suspend fun addEmploy(
        @Body createEmployRequest: CreateEmployRequest
    ): EmployEntity

    @Headers("Content-Type:application/json")
    @GET(USER_PATH)
    suspend fun getUser(
        @Header("Authorization") token: String,
    ): UserResponse

    @POST("image/upload")
    suspend fun uploadImage(
        @Body map: MultiPartFormDataContent
    ): UploadImageData


}
package link.peipei.countmoney.data.api

import de.jensklingenberg.ktorfit.http.Body
import de.jensklingenberg.ktorfit.http.DELETE
import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.Header
import de.jensklingenberg.ktorfit.http.Headers
import de.jensklingenberg.ktorfit.http.POST
import de.jensklingenberg.ktorfit.http.PUT
import de.jensklingenberg.ktorfit.http.Query
import io.ktor.client.request.forms.MultiPartFormDataContent
import link.peipei.countmoney.data.api.interceptor.AppInterceptor.Companion.LOGIN_PATH
import link.peipei.countmoney.data.api.interceptor.AppInterceptor.Companion.SEND_SMS_PATH
import link.peipei.countmoney.data.api.interceptor.AppInterceptor.Companion.USER_PATH
import link.peipei.countmoney.data.entities.UpdateEmployRequest
import link.peipei.countmoney.data.entities.EmployWithSalary
import link.peipei.countmoney.data.entities.LoginRequestBody
import link.peipei.countmoney.data.entities.SalaryEntity
import link.peipei.countmoney.data.entities.SendSmsRequestBody
import link.peipei.countmoney.data.entities.SimpleResult
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
    ): List<EmployWithSalary>

    @Headers("Content-Type:application/json")
    @POST("employee")
    suspend fun addEmploy(
        @Body createEmployRequest: UpdateEmployRequest
    ): EmployWithSalary

    @Headers("Content-Type:application/json")
    @DELETE("employee")
    suspend fun deleteEmploy(
        @Query("employId") employId: String,
        @Query("storeId") storeId: String,
    ): SimpleResult

    @Headers("Content-Type:application/json")
    @PUT("employee")
    suspend fun updateEmploy(
        @Body createEmployRequest: UpdateEmployRequest,
        @Query("employId") employId: String
    ): SimpleResult

    @Headers("Content-Type:application/json")
    @GET(USER_PATH)
    suspend fun getUser(
        @Header("Authorization") token: String,
    ): UserResponse

    @POST("image/upload")
    suspend fun uploadImage(
        @Body map: MultiPartFormDataContent
    ): UploadImageData

    @GET("employee/salaries")
    @Headers("Content-Type:application/json")
    suspend fun getSalariesByEmployId(
        @Query("employId") employId: String
    ): List<SalaryEntity>
}
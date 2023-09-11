package link.peipei.countmoney.data.api.interceptor

import io.ktor.client.call.HttpClientCall
import io.ktor.client.plugins.Sender
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.http.encodedPath
import link.peipei.countmoney.data.UserManager
import link.peipei.countmoney.data.api.core.KtorInterceptor
import link.peipei.countmoney.data.exception.HttpException

class AppInterceptor(private val userManager: UserManager) : KtorInterceptor {

    companion object {
        const val SEND_SMS_PATH = "sendSms"
        const val USER_PATH = "user"
        const val LOGIN_PATH = "login"
    }

    override suspend fun process(
        httpRequestBuilder: HttpRequestBuilder,
        sender: Sender
    ): HttpClientCall {
        val pathList = listOf(SEND_SMS_PATH, USER_PATH, LOGIN_PATH)
        if (httpRequestBuilder.url.encodedPath.drop(1) !in pathList) {
            val token = userManager.getAccessToken()
            if (token == null) {
                userManager.logout()
                throw HttpException(401, "tokon is null")
            }

            httpRequestBuilder.headers.append(
                "Authorization", "Bearer $token"
            )
        }
        val originalCall = sender.execute(httpRequestBuilder)
        if (originalCall.response.status.value != 200) {
            if (originalCall.response.status.value==401){
                userManager.logout()
            }
            throw HttpException(originalCall.response.status.value, "error")
        }
        return originalCall
    }

}
package link.peipei.countmoney.data.api.core

import io.ktor.client.call.HttpClientCall
import io.ktor.client.plugins.Sender
import io.ktor.client.request.HttpRequestBuilder

interface KtorInterceptor {
    suspend fun process(httpRequestBuilder: HttpRequestBuilder,sender: Sender): HttpClientCall
}
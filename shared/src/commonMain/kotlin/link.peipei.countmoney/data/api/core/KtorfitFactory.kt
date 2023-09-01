package link.peipei.countmoney.data.api.core

import de.jensklingenberg.ktorfit.ktorfit
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpSend
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.plugin
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import link.peipei.countmoney.data.api.CountingMoneyApi

object KtorfitFactory {
    fun create(url: String, interceptor: KtorInterceptor): CountingMoneyApi {
        val client = HttpClient {
            install(ContentNegotiation) {
                json(
                    Json {
                        isLenient = true
                        ignoreUnknownKeys = true
                        explicitNulls = false
                    }
                )
            }
        }
        client.plugin(HttpSend).intercept { request ->
            interceptor.process(request, this)
        }

        return ktorfit {
            baseUrl(url)
            httpClient(client)
        }.create()
    }
}
package link.peipei.countmoney.data.repository

import co.touchlab.kermit.Logger
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.utils.io.core.buildPacket
import io.ktor.utils.io.core.writeFully
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import link.peipei.countmoney.data.UserManager
import link.peipei.countmoney.data.api.CountingMoneyApi
import link.peipei.countmoney.data.entities.StoreRequest
import link.peipei.countmoney.data.entities.StoreResponse

class StoreRepository(private val api: CountingMoneyApi, private val userManager: UserManager) {

    private val storeListFlow = MutableStateFlow(listOf<StoreResponse>())

    suspend fun changeUserCurrentStore(pos: Int) {
        userManager.updateCurrentUserStore(storeListFlow.value[pos].id)
    }

    fun getStoreListFLow(): Flow<List<StoreResponse>> {
        return storeListFlow.asStateFlow()
    }

    suspend fun refreshStoreList(): Boolean {
        return try {
            val result = api.getStores()
            storeListFlow.update {
                result
            }
            true
        } catch (_: Exception) {
            false
        }
    }

    suspend fun createStore(
        name: String,
        des: String,
        scale: Int,
        industry: String,
        byteArray: ByteArray?
    ): StoreResponse? {
        return try {
            val storeRequest = if (byteArray != null) {
                val body = MultiPartFormDataContent(
                    formData {
                        appendInput(
                            key = "file",
                            Headers.build {
                                append(HttpHeaders.ContentDisposition, "filename=\"*\"")
                            }
                        ) {
                            buildPacket { writeFully(byteArray) }
                        }
                    },
                )
                val pathname = api.uploadImage(body).pathname
                StoreRequest(name, des, pathname, scale, industry)

            } else {
                StoreRequest(name, des, null, scale, industry)

            }

            val result = api.createStore(storeRequest)
            storeListFlow.update {
                it + result
            }
            result
        } catch (e: Exception) {
            Logger.e(e.message.toString())
            null
        }
    }

    fun clean() {
        storeListFlow.update {
            listOf()
        }
    }


}
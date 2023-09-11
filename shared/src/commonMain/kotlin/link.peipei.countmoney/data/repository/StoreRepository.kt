package link.peipei.countmoney.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import link.peipei.countmoney.data.api.CountingMoneyApi
import link.peipei.countmoney.data.entities.StoreRequest
import link.peipei.countmoney.data.entities.StoreResponse

class StoreRepository(private val api: CountingMoneyApi) {

    private val storeListFlow = MutableStateFlow(listOf<StoreResponse>())

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
        imageUrl: String?
    ): StoreResponse? {
        return try {
            val storeRequest = StoreRequest(name, des, imageUrl, scale, industry)
            val result = api.createStore(storeRequest)
            storeListFlow.update {
                it + result
            }
            result
        } catch (e: Exception) {
            null
        }
    }


}
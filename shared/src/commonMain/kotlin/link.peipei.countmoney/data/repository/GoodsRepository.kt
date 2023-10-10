package link.peipei.countmoney.data.repository

import co.touchlab.kermit.Logger
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import link.peipei.countmoney.data.UserManager
import link.peipei.countmoney.data.api.CountingMoneyApi
import link.peipei.countmoney.data.entities.GoodsEntity

class GoodsRepository(private val api: CountingMoneyApi, private val userManager: UserManager) {

    private val goodsFlow = MutableStateFlow(listOf<GoodsEntity>())

    fun getGoodsFlowFlow() = goodsFlow.asStateFlow()

    suspend fun refreshGoods(): Boolean {
        val storeId = userManager.getUserStore().firstOrNull() ?: return false
        return try {
            val result = api.getGoods(storeId)
            goodsFlow.update {
                result
            }
            true
        } catch (_: Exception) {
            false
        }
    }
}
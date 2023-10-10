package link.peipei.countmoney.workflow.home.record

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import link.peipei.countmoney.data.repository.EmployRepository
import link.peipei.countmoney.data.repository.GoodsRepository

class RecordRefreshUseCase(
    private val employRepository: EmployRepository,
    private val goodsRepository: GoodsRepository
) {
    suspend operator fun invoke() = coroutineScope {
        val employDeferred = async {
            employRepository.refreshEmployee()
        }
        val goodsDeferred = async {
            goodsRepository.refreshGoods()
        }
        employDeferred.await() && goodsDeferred.await()
    }
}

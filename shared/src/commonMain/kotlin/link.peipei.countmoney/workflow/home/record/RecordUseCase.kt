package link.peipei.countmoney.workflow.home.record

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import link.peipei.countmoney.data.entities.EmployWithSalary
import link.peipei.countmoney.data.entities.GoodsEntity
import link.peipei.countmoney.data.repository.EmployRepository
import link.peipei.countmoney.data.repository.GoodsRepository

class RecordUseCase(
    private val employRepository: EmployRepository,
    private val goodsRepository: GoodsRepository
) {
    operator fun invoke(): Flow<Pair<List<EmployWithSalary>, List<GoodsEntity>>> {
        return combine(
            employRepository.getEmployeeFlow(),
            goodsRepository.getGoodsFlowFlow()
        ) { employWithSalaries, goodsEntities ->
            Pair(employWithSalaries, goodsEntities)
        }
    }
}

package link.peipei.countmoney.workflow.home.record.employee

import link.peipei.countmoney.data.entities.EmployWithSalary
import link.peipei.countmoney.data.entities.GoodsEntity

data class RecordUiState(
    val employee: List<EmployWithSalary>,
    val goods: List<GoodsEntity>,
    val employeeLoadingState: EmployeeLoadingState
)

data class EmployeeLoadingState(
    val isLoading: Boolean = true,
    val isError: Boolean = false,
)
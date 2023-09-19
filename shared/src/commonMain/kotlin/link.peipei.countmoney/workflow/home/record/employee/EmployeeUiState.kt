package link.peipei.countmoney.workflow.home.record.employee

import link.peipei.countmoney.data.entities.EmployEntity

data class EmployeeUiState(
    val employee: List<EmployEntity>,
    val employeeLoadingState: EmployeeLoadingState
)

data class EmployeeLoadingState(
    val isLoading: Boolean = true,
    val isError: Boolean = false,
)
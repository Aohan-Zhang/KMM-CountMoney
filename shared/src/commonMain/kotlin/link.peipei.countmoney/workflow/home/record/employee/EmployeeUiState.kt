package link.peipei.countmoney.workflow.home.record.employee

import link.peipei.countmoney.data.entities.EmployEntity
import link.peipei.countmoney.data.entities.EmployWithSalary

data class EmployeeUiState(
    val employee: List<EmployWithSalary>,
    val employeeLoadingState: EmployeeLoadingState
)

data class EmployeeLoadingState(
    val isLoading: Boolean = true,
    val isError: Boolean = false,
)
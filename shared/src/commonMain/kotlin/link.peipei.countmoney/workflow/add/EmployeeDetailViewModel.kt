package link.peipei.countmoney.workflow.add

import cafe.adriel.voyager.core.model.ScreenModel
import io.ktor.util.date.GMTDate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

abstract class EmployeeDetailViewModel : ScreenModel, EmployPageInteraction {

    private val _employeeUiState by lazy {
        MutableStateFlow(initUiState())
    }
    val employeeUiState by lazy {
        _employeeUiState.asStateFlow()
    }


    override fun onNameUpdate(name: String) {
        _employeeUiState.update {
            it.copy(name = it.name.copy(content = name))
        }
    }

    override fun onPhoneUpdate(phone: String) {
        if (phone.isNotEmpty() && phone.toLongOrNull() == null) return
        if (phone.length > 11) return
        _employeeUiState.update {
            it.copy(phone = it.phone.copy(content = phone))

        }
    }

    override fun onPositionUpdate(position: String) {
        _employeeUiState.update {
            it.copy(position = it.position.copy(content = position))
        }
    }

    override fun onHireDateUpdate(dateMillis: Long?) {
        if (dateMillis == null) return
        _employeeUiState.update {
            it.copy(hireDate = GMTDate(dateMillis))
        }
    }

    override fun onGenderUpdate(gender: Boolean) {
        _employeeUiState.update {
            it.copy(gender = if (gender) 1 else 0)
        }
    }

    override fun onSalaryUpdate(salary: Int) {
        _employeeUiState.update {
            it.copy(basicSalary = salary)
        }
    }

    override fun onAllowanceUpdate(allowance: Int) {
        _employeeUiState.update {
            it.copy(allowance = allowance)
        }
    }

    override fun onBonusUpdate(bonus: Int) {
        _employeeUiState.update {
            it.copy(bonus = bonus)
        }
    }


    abstract fun initUiState(): EmployeeDetailUiState

}
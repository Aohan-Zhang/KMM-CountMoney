package link.peipei.countmoney.workflow.add

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import io.ktor.util.date.GMTDate
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

sealed class EmployeeDetailEvent

data class SnackBarEvent(val message: String) : EmployeeDetailEvent()
data class UpdateResultEvent(val result: Boolean) : EmployeeDetailEvent()

abstract class EmployeeDetailViewModel : ScreenModel,
    EmployPageInteraction {

    protected val innerEmployeeUiState by lazy {
        MutableStateFlow(initUiState())
    }
    val employeeUiState by lazy {
        innerEmployeeUiState.asStateFlow()
    }

    protected val innerSnackbarEvent = MutableSharedFlow<EmployeeDetailEvent>()
    val snackbarEvent = innerSnackbarEvent.asSharedFlow()

    fun verifyInputDate(block: () -> Unit) {

        with(innerEmployeeUiState) {
            if (value.basicSalary == 0) {
                coroutineScope.launch {
                    innerSnackbarEvent.emit(SnackBarEvent("薪资不能为 0"))
                }
                return
            }
            val list = listOf(value.name, value.phone, value.position)
            val newList = list.map { textFieldContent ->
                textFieldContent.validate()
            }
            val showError = newList.any { textFieldContent ->
                textFieldContent.showError
            }

            if (showError) {
                update {
                    it.copy(
                        name = newList[0], phone = newList[1], position = newList[2]
                    )
                }

            } else {
                block()
            }
        }

    }

    override fun onNameUpdate(name: String) {
        innerEmployeeUiState.update {
            it.copy(name = it.name.copy(content = name, showError = false))
        }
    }

    override fun onPhoneUpdate(phone: String) {
        if (phone.isNotEmpty() && phone.toLongOrNull() == null) return
        if (phone.length > 11) return
        innerEmployeeUiState.update {
            it.copy(phone = it.phone.copy(content = phone, showError = false))

        }
    }

    override fun onPositionUpdate(position: String) {
        innerEmployeeUiState.update {
            it.copy(position = it.position.copy(content = position, showError = false))
        }
    }

    override fun onHireDateUpdate(dateMillis: Long?) {
        if (dateMillis == null) return
        innerEmployeeUiState.update {
            it.copy(date = GMTDate(dateMillis))
        }
    }

    override fun onGenderUpdate(gender: Boolean) {
        innerEmployeeUiState.update {
            it.copy(gender = if (gender) 1 else 0)
        }
    }

    override fun onSalaryUpdate(salary: Int) {
        innerEmployeeUiState.update {
            it.copy(basicSalary = salary)
        }
    }

    override fun onAllowanceUpdate(allowance: Int) {
        innerEmployeeUiState.update {
            it.copy(allowance = allowance)
        }
    }

    override fun onBonusUpdate(bonus: Int) {
        innerEmployeeUiState.update {
            it.copy(bonus = bonus)
        }
    }


    abstract fun initUiState(): EmployeeDetailUiState

    abstract fun update()
}
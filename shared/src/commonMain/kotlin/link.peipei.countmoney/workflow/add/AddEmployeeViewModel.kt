package link.peipei.countmoney.workflow.add

import cafe.adriel.voyager.core.model.coroutineScope
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import link.peipei.countmoney.data.repository.EmployRepository

class AddEmployeeViewModel(private val repo: EmployRepository) : EmployeeDetailViewModel() {
    override fun initUiState(): EmployeeDetailUiState {
        return EmployeeDetailUiState("新增员工", "保存", "入职时间")
    }

    override fun update() {
        verifyInputDate { request ->
            coroutineScope.launch {
                innerEmployeeUiState.update {
                    it.copy(isUpdating = true)
                }

                val result = repo.addEmploy(request)
                innerEmployeeUiState.update {
                    it.copy(isUpdating = false)
                }
                innerEvent.emit(UpdateResultEvent(result))
            }

        }
    }

}
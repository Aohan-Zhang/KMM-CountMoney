package link.peipei.countmoney.workflow.home.record

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import co.touchlab.kermit.Logger
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import link.peipei.countmoney.data.repository.EmployRepository
import link.peipei.countmoney.workflow.home.record.employee.EmployeeLoadingState
import link.peipei.countmoney.workflow.home.record.employee.EmployeeUiState


class RecordPageViewModel(private val repository: EmployRepository) : ScreenModel {
    private val _loadingUiState = MutableStateFlow(
        EmployeeLoadingState()
    )

    private val employee = repository.getEmployeeFlow()

    val uiState = combine(_loadingUiState, employee) { loadingUiState, list ->
        EmployeeUiState(list, loadingUiState)
    }.stateIn(
        coroutineScope, SharingStarted.WhileSubscribed(5000), EmployeeUiState(
            emptyList(),
            EmployeeLoadingState()
        )
    )


    init {
        refresh()
    }

    fun delete(id: String) {
        coroutineScope.launch {
            repository.deleteEmploy(id)
        }
    }

    fun refresh() {
        coroutineScope.launch {
            _loadingUiState.update {
                it.copy(isLoading = true)
            }
            val result = repository.refreshEmployee()
            if (!result) {
                _loadingUiState.update {
                    it.copy(isLoading = false, isError = true)
                }
            } else {
                _loadingUiState.update {
                    it.copy(isLoading = false)
                }
            }
        }
    }
}
package link.peipei.countmoney.workflow.home.record

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import link.peipei.countmoney.data.repository.EmployRepository
import link.peipei.countmoney.data.repository.GoodsRepository
import link.peipei.countmoney.workflow.home.record.employee.EmployeeLoadingState
import link.peipei.countmoney.workflow.home.record.employee.RecordUiState


class RecordPageViewModel(
    private val refreshRecord: RecordRefreshUseCase,
    private val getRecords: RecordUseCase,
    private val employRepository: EmployRepository,
    private val goodsRepository: GoodsRepository
) : ScreenModel {
    private val _loadingUiState = MutableStateFlow(
        EmployeeLoadingState()
    )


    val uiState = combine(_loadingUiState, getRecords()) { loadingUiState, list ->
        RecordUiState(list.first, list.second, loadingUiState)
    }.stateIn(
        coroutineScope, SharingStarted.WhileSubscribed(5000), RecordUiState(
            emptyList(),
            emptyList(),
            EmployeeLoadingState()
        )
    )


    init {
        refresh()
    }

    fun deleteEmploy(id: String) {
        coroutineScope.launch {
            employRepository.deleteEmploy(id)
        }
    }

    fun refresh() {
        coroutineScope.launch {
            _loadingUiState.update {
                it.copy(isLoading = true)
            }
            val result = refreshRecord()
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
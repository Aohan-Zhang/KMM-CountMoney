package link.peipei.countmoney.workflow.store.list

import androidx.compose.runtime.collectAsState
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import link.peipei.countmoney.data.repository.StoreRepository

class StoreSelectionViewModel(private val storeRepository: StoreRepository) : ScreenModel {
    private val loadingUiState = MutableStateFlow(StoreSelectionLoadingState())
    private val itemUiState = storeRepository.getStoreListFLow()

    private val _pageUiState =
        combine(loadingUiState, itemUiState) { storeSelectionLoadingState, item ->
            StoreSelectionUiState(
                storeSelectionLoadingState,
                item.map { StoreSelectionItem(it.name, it.des) })
        }
    val pageUiState = _pageUiState.stateIn(
        coroutineScope,
        SharingStarted.WhileSubscribed(5000),
        StoreSelectionUiState(
            StoreSelectionLoadingState(),
            listOf()
        )
    )

    init {
        coroutineScope.launch {
            loadingUiState.update {
                it.copy(isLoading = true)
            }
            val result = storeRepository.refreshStoreList()
            loadingUiState.update {
                it.copy(isLoading = false)
            }
            if (!result) {
                loadingUiState.update {
                    it.copy(isError = true)
                }
            }
        }
    }
}
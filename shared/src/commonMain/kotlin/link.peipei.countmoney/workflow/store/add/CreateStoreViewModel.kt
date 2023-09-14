package link.peipei.countmoney.workflow.store.add

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import link.peipei.countmoney.data.repository.StoreRepository

class CreateStoreViewModel(private val repository: StoreRepository) : ScreenModel {
    private val _uiState = MutableStateFlow(CreateStoreUiState(""))
    val uiState = _uiState.asStateFlow()

    private val _onCreateStoreEvent = MutableSharedFlow<Boolean>()
    val onCreateStoreEvent = _onCreateStoreEvent.asSharedFlow()

    fun updateTitle(title: String) {
        _uiState.update {
            it.copy(title = TextFieldContent(content = title))
        }
    }

    fun updateIndustry(industry: String) {
        _uiState.update {
            it.copy(industry = TextFieldContent(content = industry))
        }
    }

    fun updateScope(scope: String) {
        if (scope.isNotEmpty() && scope.toIntOrNull() == null) return
        _uiState.update {
            it.copy(scope = TextFieldContent(content = scope))
        }
    }

    fun updateDes(des: String) {
        _uiState.update {
            it.copy(
                des = TextFieldContent(content = des),
                desIndicatorText = "${des.length}/$MAX_DES_LENGTH"
            )
        }
    }

    fun create(byteArray: ByteArray?) {
        validateData {
            val uiState = _uiState.value
            coroutineScope.launch {
                _uiState.update {
                    it.copy(isLoading = true)
                }
                val result = repository.createStore(
                    uiState.title.content,
                    uiState.des.content,
                    uiState.scope.content.toInt(),
                    uiState.industry.content,
                    byteArray
                )
                _uiState.update {
                    it.copy(isLoading = false)
                }
                if (result == null) {
                    _onCreateStoreEvent.emit(false)
                } else {
                    _onCreateStoreEvent.emit(true)
                }
            }
        }


    }

    private fun validateData(block: () -> Unit) {
        val uiState = _uiState.value
        val list = listOf(uiState.des, uiState.title, uiState.industry, uiState.scope)
        val newList = list.map { textFieldContent ->
            textFieldContent.validate()
        }
        val showError = newList.any { textFieldContent ->
            textFieldContent.showError
        }

        if (showError) {
            _uiState.update {
                it.copy(
                    des = newList[0], title = newList[1], industry = newList[2], scope = newList[3]
                )
            }

        } else {
            block()
        }
    }
}
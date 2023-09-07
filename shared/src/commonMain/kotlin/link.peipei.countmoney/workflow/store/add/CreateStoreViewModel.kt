package link.peipei.countmoney.workflow.store.add

import cafe.adriel.voyager.core.model.ScreenModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CreateStoreViewModel : ScreenModel {
    private val _uiState = MutableStateFlow(CreateStoreUiState(""))
    val uiState = _uiState.asStateFlow()

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

    fun create() {
        _uiState.update {
            val list = listOf(it.des, it.title, it.industry, it.scope)
            val newList = list.map { textFieldContent ->
                textFieldContent.validate()
            }
            val showError = newList.any { textFieldContent ->
                textFieldContent.showError
            }
            if (showError) {
                it.copy(
                    des = newList[0], title = newList[1], industry = newList[2], scope = newList[3]
                )
            } else {
                it
            }
        }
    }
}
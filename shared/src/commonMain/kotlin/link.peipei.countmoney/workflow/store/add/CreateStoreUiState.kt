package link.peipei.countmoney.workflow.store.add

import link.peipei.countmoney.core_ui.view.TextFieldContent

const val MAX_DES_LENGTH = 50

data class CreateStoreUiState(
    val selectedImageUrl: String,
    val underSubmit: Boolean = false,
    val title: TextFieldContent = TextFieldContent(),
    val industry: TextFieldContent = TextFieldContent(),
    val des: TextFieldContent = TextFieldContent(),
    val scope: TextFieldContent = TextFieldContent(),
    val desIndicatorText: String = "0/$MAX_DES_LENGTH",
    val isLoading: Boolean = false

)


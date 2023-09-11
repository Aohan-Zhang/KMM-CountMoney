package link.peipei.countmoney.workflow.store.add

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

data class TextFieldContent(
    val nullable: Boolean = false,
    val content: String = "",
    val showError: Boolean = false,
    val message: String = "",
) {
    fun shouldShowCleanButton(): Boolean {
        return content.isNotBlank()
    }

    fun validate(): TextFieldContent {
        return if (!nullable && content.isBlank()) {
            this.copy(showError = true, message = "该字段不能为空")
        } else {
            this
        }
    }
}
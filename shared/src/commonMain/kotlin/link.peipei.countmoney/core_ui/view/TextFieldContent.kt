package link.peipei.countmoney.core_ui.view

interface TextFieldValidator {
    fun validate(textFieldContent: TextFieldContent): TextFieldContent
}

object DefaultTextFieldValidator : TextFieldValidator {
    override fun validate(textFieldContent: TextFieldContent): TextFieldContent {
        return if (!textFieldContent.nullable && textFieldContent.content.isBlank()) {
            textFieldContent.copy(showError = true, message = "该字段不能为空")
        } else {
            textFieldContent
        }
    }
}

object PhoneNumberTextFieldValidator : TextFieldValidator {
    override fun validate(textFieldContent: TextFieldContent): TextFieldContent {
        with(textFieldContent) {
            if (!nullable && content.isBlank()) {
                return copy(showError = true, message = "该字段不能为空")
            }
            if (content.toLongOrNull() == null || content.length != 11) {
                return copy(
                    showError = true, message = "请检查手机号格式" +
                            ""
                )
            }
            return this
        }

    }

}

data class TextFieldContent(
    val nullable: Boolean = false,
    val content: String = "",
    val showError: Boolean = false,
    val message: String = "",
    val validator: TextFieldValidator = DefaultTextFieldValidator
) {
    fun shouldShowCleanButton(): Boolean {
        return content.isNotBlank()
    }

    fun validate(): TextFieldContent {
        return validator.validate(this)
    }

    override fun toString(): String {
        return content
    }
}
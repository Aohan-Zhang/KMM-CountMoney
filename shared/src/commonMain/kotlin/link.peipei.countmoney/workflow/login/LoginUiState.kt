package link.peipei.countmoney.workflow.login

data class LoginUiState(
    val isPartlyLoading: Boolean = false,
    val sendCodeButtonIsLoading: Boolean = false,
    val sendCodeButtonCountDown: Int = 0,
    val phoneNumber: String = "",
    val code: String = "",
) {
    val loginButtonEnable: Boolean
        get() = phoneNumber.isNotEmpty()
                && phoneNumber.length == 11
                && code.isNotEmpty()
                && code.length == 6
                && !isPartlyLoading

    val sendCodeButtonEnable: Boolean
        get() = phoneNumber.isNotEmpty()
                && phoneNumber.length == 11
                && sendCodeButtonCountDown == 0
                && !sendCodeButtonIsLoading
}
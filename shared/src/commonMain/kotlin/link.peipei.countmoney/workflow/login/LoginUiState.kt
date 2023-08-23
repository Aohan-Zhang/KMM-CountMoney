package link.peipei.countmoney.workflow.login

data class LoginUiState(
    val isPartlyLoading: Boolean = false,
    val userName: String = "",
    val password: String = ""
)
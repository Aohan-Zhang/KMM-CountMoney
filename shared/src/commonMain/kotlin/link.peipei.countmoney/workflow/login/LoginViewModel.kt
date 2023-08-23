package link.peipei.countmoney.workflow.login

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import link.peipei.countmoney.data.repository.AccountRepository

class LoginViewModel(private val accountRepository: AccountRepository) : ScreenModel {
    private val _loginUiState = MutableStateFlow(LoginUiState())
    val loginUiState = _loginUiState.asStateFlow()

    private val _loginEvent = MutableSharedFlow<Boolean>()
    val loginEvent = _loginEvent.asSharedFlow()

    fun login() {
        coroutineScope.launch {
            _loginUiState.value = LoginUiState(isPartlyLoading = true)
            val result =
                accountRepository.login(_loginUiState.value.userName, _loginUiState.value.password)
            _loginUiState.value = LoginUiState(isPartlyLoading = false)
            _loginEvent.emit(result)
        }
    }

    fun onUserNameType(userName: String) {
        _loginUiState.value = _loginUiState.value.copy(userName = userName)
    }

    fun onPasswordType(password: String) {
        _loginUiState.value = _loginUiState.value.copy(password = password)
    }
}
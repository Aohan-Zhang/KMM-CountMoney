package link.peipei.countmoney.workflow.login

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import link.peipei.countmoney.data.repository.AccountRepository

class LoginViewModel(private val accountRepository: AccountRepository) : ScreenModel {
    private val _loginUiState = MutableStateFlow(LoginUiState())
    val loginUiState = _loginUiState.asStateFlow()

    private val _loginEvent = MutableSharedFlow<Boolean>()
    val loginEvent = _loginEvent.asSharedFlow()

    fun login() {
        coroutineScope.launch {
            _loginUiState.value = _loginUiState.value.copy(isPartlyLoading = true)
            val result =
                accountRepository.login(_loginUiState.value.phoneNumber, _loginUiState.value.code)
            _loginUiState.value = LoginUiState(isPartlyLoading = false)
            _loginEvent.emit(result)

        }
    }

    fun onPhoneNumberType(phoneNumber: String) {
        if (phoneNumber.length > 11) return
        _loginUiState.value = _loginUiState.value.copy(phoneNumber = phoneNumber)
    }

    fun onCodeType(code: String) {
        if (code.length > 6) return
        _loginUiState.value = _loginUiState.value.copy(code = code)
    }

    fun sendCode() {
        coroutineScope.launch(Dispatchers.IO) {
            _loginUiState.update {
                it.copy(sendCodeButtonIsLoading = true)
            }
            val result = accountRepository.sendCode(_loginUiState.value.phoneNumber)
            _loginUiState.update {
                it.copy(sendCodeButtonIsLoading = false)
            }
            if (result) {
                startCountDown()
            } else {
                _loginEvent.emit(result)
            }

        }
    }

    private fun startCountDown() {
        coroutineScope.launch(Dispatchers.IO) {
            val countDown = 60
            for (i in countDown downTo 0) {
                _loginUiState.update {
                    it.copy(sendCodeButtonCountDown = i)
                }
                kotlinx.coroutines.delay(1000)
            }
        }
    }
}
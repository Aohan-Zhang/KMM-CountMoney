package link.peipei.countmoney.workflow.login

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.kodein.rememberScreenModel

object LoginScreen : Screen {
    @Composable
    override fun Content() {
        val viewModel = rememberScreenModel<LoginViewModel>()
        val uiState by viewModel.loginUiState.collectAsState()
        LoginPage(
            uiState,
            viewModel.loginEvent,
            onLoginClick = {
                viewModel.login()
            },
            onCodeType = {
                viewModel.onCodeType(it)
            },
            onPhoneNumberType = {
                viewModel.onPhoneNumberType(it)
            },
            sendCode = {
                viewModel.sendCode()
            }
        )
    }
}
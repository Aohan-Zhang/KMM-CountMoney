package link.peipei.countmoney.workflow.login

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
            onPasswordType = {
                viewModel.onPasswordType(it)
            },
            onUserNameType = {
                viewModel.onUserNameType(it)
            }
        )
    }
}
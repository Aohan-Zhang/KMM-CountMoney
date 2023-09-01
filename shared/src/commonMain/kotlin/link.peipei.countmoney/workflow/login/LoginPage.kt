package link.peipei.countmoney.workflow.login

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import link.peipei.countmoney.core_ui.digitalListener
import link.peipei.countmoney.core_ui.view.LoadingButton
import link.peipei.countmoney.workflow.home.HomeScreen

@Composable
fun LoginPage(
    loginUiState: LoginUiState,
    loginResult: SharedFlow<Boolean>,
    onLoginClick: () -> Unit,
    onPhoneNumberType: (String) -> Unit,
    onCodeType: (String) -> Unit,
    sendCode: () -> Unit
) {
    val navigator = LocalNavigator.currentOrThrow
    val scope = rememberCoroutineScope()
    val focusManager: FocusManager = LocalFocusManager.current
    val interactionSource = remember { MutableInteractionSource() }

    val snackbarHostState = remember { SnackbarHostState() }
    LaunchedEffect(loginResult) {
        loginResult.collect {
            if (it) {
                navigator.replace(HomeScreen)
            } else {
                scope.launch {
                    snackbarHostState.showSnackbar("网络出问题了，请重试")
                }
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        modifier = Modifier.clickable(
            interactionSource = interactionSource,
            indication = null
        ) {
            focusManager.clearFocus()
        }
    ) {
        Box(
            modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Column(modifier = Modifier.fillMaxWidth().align(Alignment.Center)) {
                Text(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    text = "登录或注册",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.ExtraBold
                )
                Text(
                    modifier = Modifier.align(Alignment.CenterHorizontally).padding(16.dp),
                    text = "请用手机号登录，如果没有注册，在登录后会自动注册",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center

                )
                OutlinedTextField(
                    enabled = !loginUiState.isPartlyLoading,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    value = loginUiState.phoneNumber,
                    onValueChange = { s ->
                        digitalListener(s) {
                            onPhoneNumberType(it)
                        }
                    },
                    label = { Text("手机号") },
                    modifier = Modifier.fillMaxWidth()
                        .padding(top = 24.dp, start = 16.dp, end = 16.dp)
                )
                Row(
                    modifier = Modifier.fillMaxWidth()
                        .padding(top = 8.dp, start = 16.dp, end = 16.dp)
                ) {
                    OutlinedTextField(
                        enabled = !loginUiState.isPartlyLoading,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.weight(1f),
                        value = loginUiState.code,
                        onValueChange = { s ->
                            digitalListener(s) {
                                onCodeType(it)
                            }
                        },
                        label = { Text("短信验证码") },
                    )
                    LoadingButton(
                        text = if (loginUiState.sendCodeButtonCountDown == 0) {
                            "发送验证码"
                        } else {
                            "${loginUiState.sendCodeButtonCountDown}秒后重试"
                        },
                        modifier = Modifier
                            .padding(start = 16.dp)
                            .align(Alignment.CenterVertically),
                        enable = loginUiState.sendCodeButtonEnable,
                        isLoading = loginUiState.sendCodeButtonIsLoading,
                    ) {
                        focusManager.clearFocus()
                        sendCode()
                    }
                }

                LoadingButton(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    text = "登录",
                    enable = loginUiState.loginButtonEnable,
                    isLoading = loginUiState.isPartlyLoading
                ) {
                    focusManager.clearFocus()
                    onLoginClick()
                }
            }
        }
    }
}
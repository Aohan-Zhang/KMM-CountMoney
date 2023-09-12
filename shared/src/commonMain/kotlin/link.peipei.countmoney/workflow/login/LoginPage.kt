package link.peipei.countmoney.workflow.login

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.moriatsushi.insetsx.navigationBarsPadding
import com.moriatsushi.insetsx.statusBarsPadding
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import link.peipei.countmoney.core_ui.digitalListener
import link.peipei.countmoney.core_ui.view.LoadingButton
import link.peipei.countmoney.workflow.store.list.StoreSelectionScreen
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalResourceApi::class)
@Composable
fun LoginPage(
    loginUiState: LoginUiState,
    loginResult: SharedFlow<Boolean>,
    onLoginClick: () -> Unit,
    onPhoneNumberType: (String) -> Unit,
    onCodeType: (String) -> Unit,
    sendCode: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val focusManager: FocusManager = LocalFocusManager.current
    val interactionSource = remember { MutableInteractionSource() }

    val snackbarHostState = remember { SnackbarHostState() }
    LaunchedEffect(loginResult) {
        loginResult.collect {
            if (!it) {
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
        },
    ) {
        Box(
            modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.surface)
        ) {
            Column(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .statusBarsPadding()
                    .padding(start = 24.dp, top = 24.dp, end = 24.dp)
            ) {
                Text(
                    "Login",
                    fontSize = 46.sp,
                    fontWeight = FontWeight.Normal
                )
                Box(
                    modifier = Modifier
                        .padding(top = 24.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.tertiaryContainer)
                        .padding(24.dp),

                    ) {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        val icon = painterResource("icons/ic_lightbulb_fill_24.xml")
                        Icon(icon, null, tint = MaterialTheme.colorScheme.onTertiaryContainer)
                        Spacer(modifier = Modifier.size(8.dp))
                        Text(
                            "请用手机号登录，如果没有注册，在登录后会自动注册",
                            color = MaterialTheme.colorScheme.onTertiaryContainer
                        )
                    }
                }
                Box(modifier = Modifier.fillMaxSize().padding(top = 48.dp)) {
                    Column(modifier = Modifier.fillMaxWidth()) {
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
                                .padding(top = 24.dp),
                            trailingIcon = {
                                if (loginUiState.phoneNumber.isNotEmpty() && !loginUiState.isPartlyLoading) {
                                    val icon = painterResource("icons/ic_cancel_24.xml")
                                    Icon(
                                        icon,
                                        null,
                                        tint = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier.clickable {
                                            onPhoneNumberType("")
                                        }
                                    )
                                }
                            }
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth()
                                .padding(top = 8.dp)
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
                                trailingIcon = {
                                    if (loginUiState.code.isNotEmpty() && !loginUiState.isPartlyLoading) {
                                        val icon = painterResource("icons/ic_cancel_24.xml")
                                        Icon(
                                            icon,
                                            null,
                                            tint = MaterialTheme.colorScheme.primary,
                                            modifier = Modifier.clickable {
                                                onCodeType("")
                                            }
                                        )
                                    }
                                }
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
                    }
                    LoadingButton(
                        modifier = Modifier.width(184.dp)
                            .navigationBarsPadding()
                            .padding(bottom = 24.dp)
                            .align(Alignment.BottomEnd),
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
}
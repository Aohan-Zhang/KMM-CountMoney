package link.peipei.countmoney.workflow.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import link.peipei.countmoney.workflow.home.HomeScreen

@Composable
fun LoginPage(
    loginUiState: LoginUiState,
    loginResult: SharedFlow<Boolean>,
    onLoginClick: () -> Unit,
    onUserNameType: (String) -> Unit,
    onPasswordType: (String) -> Unit
) {
    val navigator = LocalNavigator.currentOrThrow
    val scope = rememberCoroutineScope()

    val snackbarHostState = remember { SnackbarHostState() }
    LaunchedEffect(loginResult) {
        loginResult.collect {
            if (it) {
                navigator.replace(HomeScreen)
            } else {
                scope.launch {
                    snackbarHostState.showSnackbar("登录失败")
                }
            }
        }
    }

    Scaffold(snackbarHost = { SnackbarHost(snackbarHostState) }) {
        Box(
            modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Column(modifier = Modifier.fillMaxWidth().align(Alignment.Center)) {
                Text(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    text = "请先登录",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.ExtraBold
                )
                Text(
                    modifier = Modifier.align(Alignment.CenterHorizontally).padding(16.dp),
                    text = "请登录你的账号，如果没有账号请联系系统管理员创建账号",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center

                )
                OutlinedTextField(
                    value = loginUiState.userName,
                    onValueChange = {
                        onUserNameType(it)
                    },
                    label = { Text("账号") },
                    modifier = Modifier.fillMaxWidth()
                        .padding(top = 24.dp, start = 16.dp, end = 16.dp)
                )
                OutlinedTextField(
                    value = loginUiState.password,
                    onValueChange = {
                        onPasswordType(it)
                    },
                    label = { Text(" 密码") },
                    modifier = Modifier.fillMaxWidth()
                        .padding(top = 8.dp, start = 16.dp, end = 16.dp)
                )
                Button(onClick = {
                    onLoginClick()
                }, modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                    Text("登录")
                }
            }
            if (loginUiState.isPartlyLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center).size(64.dp))
            }
        }
    }
}
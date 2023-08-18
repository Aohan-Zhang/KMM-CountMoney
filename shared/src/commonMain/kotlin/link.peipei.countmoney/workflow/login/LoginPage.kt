package link.peipei.countmoney.workflow.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import link.peipei.countmoney.workflow.home.HomeScreen

@Composable
fun LoginPage() {
    val navigator = LocalNavigator.currentOrThrow
    Box(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.surfaceVariant)) {
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
                value = "",
                onValueChange = {},
                label = { Text("账号") },
                modifier = Modifier.fillMaxWidth().padding(top = 24.dp, start = 16.dp, end = 16.dp)
            )
            OutlinedTextField(
                value = "",
                onValueChange = {},
                label = { Text(" 密码") },
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp, start = 16.dp, end = 16.dp)
            )
            Button(onClick = { navigator.push(HomeScreen) }, modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                Text("登录")
            }
        }
    }

}
package link.peipei.countmoney.workflow.home.event

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.currentOrThrow
import link.peipei.countmoney.workflow.add.AddEmployeeScreen
import link.peipei.countmoney.workflow.home.LocalGlobalNavigator

@Composable
fun EventPage(eventPageViewModel: EventPageViewModel, modifier: Modifier = Modifier) {
    val isLogging by eventPageViewModel.isLogin.collectAsState()
    val userResponse by eventPageViewModel.userResponse.collectAsState()
    val navigator = LocalGlobalNavigator.currentOrThrow
    Column {
        Text(text = isLogging.toString())

        Text(text = userResponse.toString())
        Button(onClick = {
            eventPageViewModel.logout()
        }) {
            Text(text = "登出")
        }

        Button(onClick = {
            navigator.push(AddEmployeeScreen())
        }) {
            Text(text = "测试")
        }
    }

}
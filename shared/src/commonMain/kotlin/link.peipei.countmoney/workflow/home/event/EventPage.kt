package link.peipei.countmoney.workflow.home.event

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier

@Composable
fun EventPage(eventPageViewModel: EventPageViewModel, modifier: Modifier = Modifier) {
    val isLogging by eventPageViewModel.isLogin.collectAsState()
    val userResponse by eventPageViewModel.userResponse.collectAsState()
    Column {
        Text(text = isLogging.toString())

        Text(text = userResponse.toString())
        Button(onClick = {
            eventPageViewModel.logout()
        }) {
            Text(text = "登出")
        }
    }

}
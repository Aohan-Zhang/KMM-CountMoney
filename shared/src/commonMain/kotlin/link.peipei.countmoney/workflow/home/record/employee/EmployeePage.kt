package link.peipei.countmoney.workflow.home.record.employee

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun EmployeePage(uiState: EmployeeUiState, onRetryClick: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        if (uiState.employeeLoadingState.isLoading) {
            CircularProgressIndicator(Modifier.align(Alignment.Center).size(32.dp).clickable {
                onRetryClick()
            })
        } else if (uiState.employeeLoadingState.isError) {
            Button(
                modifier = Modifier.align(Alignment.Center),
                onClick = {
                    onRetryClick()
                }
            ) {
                Text("重试")
            }
        } else {
            Text(uiState.toString(), Modifier.fillMaxSize())
        }
    }


}
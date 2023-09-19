package link.peipei.countmoney.workflow.home.record.employee

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun EmployeePage(uiState: EmployeeUiState) {
    Text(uiState.toString(), Modifier.fillMaxSize())
}
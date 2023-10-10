package link.peipei.countmoney.workflow.add.employ

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.kodein.rememberScreenModel
import link.peipei.countmoney.data.entities.EmployWithSalary

class EmployeeDetailScreen(private val employWithSalary: EmployWithSalary) : Screen {
    @Composable
    override fun Content() {
        val viewModel =
            rememberScreenModel<EmployWithSalary, EmployeeDetailViewModel>(arg = employWithSalary)
        val uiState by viewModel.employeeUiState.collectAsState()
        EmployeeDetailPage(uiState, viewModel.snackbarEvent, viewModel) {
            viewModel.update()
        }
    }
}
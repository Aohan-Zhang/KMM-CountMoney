package link.peipei.countmoney.workflow.add

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.kodein.rememberScreenModel
import link.peipei.countmoney.data.entities.EmployEntity
import link.peipei.countmoney.data.entities.EmployWithSalary

class AddEmployeeScreen(private val employWithSalary: EmployWithSalary) : Screen {
    @Composable
    override fun Content() {
        val viewModel =
            rememberScreenModel<EmployWithSalary, AddEmployeeViewModel>(arg = employWithSalary)
        AddEmployeePage()
    }
}
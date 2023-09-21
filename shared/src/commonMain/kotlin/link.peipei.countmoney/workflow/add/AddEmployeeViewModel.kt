package link.peipei.countmoney.workflow.add

import cafe.adriel.voyager.core.model.ScreenModel
import co.touchlab.kermit.Logger
import link.peipei.countmoney.data.entities.EmployWithSalary

class AddEmployeeViewModel(private val employWithSalary: EmployWithSalary) : ScreenModel {
    init {
        Logger.d("dududu$employWithSalary")
    }
}
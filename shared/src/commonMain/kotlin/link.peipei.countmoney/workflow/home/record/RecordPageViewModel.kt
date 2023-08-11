package link.peipei.countmoney.workflow.home.record

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import link.peipei.countmoney.data.database.EmployeeDao

class RecordPageViewModel(private val employeeDao: EmployeeDao) : ScreenModel {
    val data = employeeDao.getAllEmployees()
        .stateIn(coroutineScope, SharingStarted.WhileSubscribed(500), listOf())

}
package link.peipei.countmoney.home.record

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import link.peipei.countmoney.data.database.EmployeeDao

class RecordPageViewModel(private val employeeDao: EmployeeDao) : ScreenModel {
    val data = employeeDao.getAllEmployees()
        .stateIn(coroutineScope, SharingStarted.WhileSubscribed(500), listOf())

    fun insert() {
        employeeDao.insert("name", 1, 1, 1, "date", "phone")
    }
}
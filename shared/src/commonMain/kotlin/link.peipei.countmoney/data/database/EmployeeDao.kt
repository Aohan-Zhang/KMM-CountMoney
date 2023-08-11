package link.peipei.countmoney.data.database

import app.cash.sqldelight.TransactionWithoutReturn
import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import link.peipei.countmoney.AppDatabase
import linkpeipeicountmoney.Employee
import linkpeipeicountmoney.EmployeeQueries

class EmployeeDao(
    private val employeeQueries: EmployeeQueries,
    private val appDatabase: AppDatabase
) {
    fun clean() {
        employeeQueries.transaction {
            employeeQueries.deleteAllEmployees()
        }
    }

    fun getAllEmployees() = employeeQueries.selectAllEmployees().asFlow().mapToList(Dispatchers.IO)

    fun deleteById(id: Long) {
        employeeQueries.deleteEmployee(id)
    }

    fun insert(
        employee: Employee
    ) {
        employeeQueries.insertEmployee(
            employee
        )
    }

    fun lastEmployeeId() = employeeQueries.lastInsertRowId().executeAsOne()
    fun transaction(
        body: TransactionWithoutReturn.() -> Unit,
    ) {
        appDatabase.transaction(body = body)
    }

}
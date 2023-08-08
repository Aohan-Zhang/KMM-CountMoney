package link.peipei.countmoney.data.database

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import linkpeipeicountmoney.EmployeeQueries

class EmployeeDao(private val employeeQueries: EmployeeQueries) {
    fun clean() {
        employeeQueries.transaction {
            employeeQueries.deleteAllEmployees()
        }

    }

    fun getAllEmployees() = employeeQueries.selectAllEmployees().asFlow().mapToList(Dispatchers.IO)

    fun insert(
        name: String?,
        baseSalary: Long?,
        housingSalary: Long?,
        performanceSalary: Long?,
        dateOfJoining: String?,
        phoneNumber: String?,
    ) {
        employeeQueries.insertEmployee(
            name,
            baseSalary,
            housingSalary,
            performanceSalary,
            dateOfJoining,
            phoneNumber
        )
    }
}
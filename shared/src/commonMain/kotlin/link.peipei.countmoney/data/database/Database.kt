package link.peipei.countmoney.data.database

import link.peipei.countmoney.AppDatabase

internal class Database(databaseDriverFactory: DatabaseDriverFactory) {
    private val database = AppDatabase(databaseDriverFactory.createDriver())
    val employeeDao = EmployeeDao(database.employeeQueries, database)
    val salaryTimeLineDao = SalaryTimeLineDao(database.salaryTimeLineQueries)
}
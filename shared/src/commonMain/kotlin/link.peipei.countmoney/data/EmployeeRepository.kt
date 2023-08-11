package link.peipei.countmoney.data


import link.peipei.countmoney.data.database.EmployeeDao
import link.peipei.countmoney.data.database.SalaryTimeLineDao
import linkpeipeicountmoney.Employee
import linkpeipeicountmoney.SalaryTimeLine

class EmployeeRepository(
    private val employeeDao: EmployeeDao,
    private val salaryTimeLineDao: SalaryTimeLineDao
) {
    fun insertEmployeeAndSalary(employee: Employee, salaryTimeLine: SalaryTimeLine) {
        employeeDao.transaction {
            employeeDao.insert(employee)
            val employeeId = employeeDao.lastEmployeeId()
            salaryTimeLineDao.insert(salaryTimeLine.copy(employeeId = employeeId))
        }
    }

    fun deleteEmployee(id: Long) {
        employeeDao.transaction {
            salaryTimeLineDao.deleteByEmployeeId(id)
        }
        employeeDao.deleteById(id)
    }
}
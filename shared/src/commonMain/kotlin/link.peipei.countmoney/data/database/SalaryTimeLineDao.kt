package link.peipei.countmoney.data.database

import linkpeipeicountmoney.SalaryTimeLine
import linkpeipeicountmoney.SalaryTimeLineQueries


class SalaryTimeLineDao(private val salaryTimeLineQueries: SalaryTimeLineQueries) {
    fun insert(
        salaryTimeLine: SalaryTimeLine
    ) {
        salaryTimeLineQueries.insertSalaryTimeLine(
            salaryTimeLine
        )
    }

    fun deleteByEmployeeId(employeeId: Long) {
        salaryTimeLineQueries.deleteSalaryByEmployeeId(employeeId)
    }
}
package link.peipei.countmoney.workflow.home.event

import cafe.adriel.voyager.core.model.ScreenModel
import link.peipei.countmoney.data.EmployeeRepository
import linkpeipeicountmoney.Employee
import linkpeipeicountmoney.SalaryTimeLine

class EventPageViewModel(private val employeeRepository: EmployeeRepository) : ScreenModel {
    fun insert() {
        employeeRepository.insertEmployeeAndSalary(
            Employee(
                -1,
                "test",
                "男",
                "2011-1-1",
                "122222222",
                "厨师"

            ),
            SalaryTimeLine(
                -1,
                -1,
                "2011-1-1",
                1000,
                1000,
                1000
            )
        )
    }

    fun delete(){
        employeeRepository.deleteEmployee(1)
    }

}
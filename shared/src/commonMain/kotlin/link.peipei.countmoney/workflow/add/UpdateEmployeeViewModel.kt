package link.peipei.countmoney.workflow.add

import io.ktor.util.date.GMTDate
import io.ktor.util.date.GMTDateParser
import io.ktor.util.date.Month
import io.ktor.util.date.getTimeMillis
import kotlinx.coroutines.flow.update
import link.peipei.countmoney.core_common.parse
import link.peipei.countmoney.core_ui.view.TextFieldContent
import link.peipei.countmoney.data.entities.UpdateEmployRequest
import link.peipei.countmoney.data.entities.EmployWithSalary
import link.peipei.countmoney.data.repository.EmployRepository

class UpdateEmployeeViewModel(
    private val employWithSalary: EmployWithSalary,
    private val repo: EmployRepository
) :
    EmployeeDetailViewModel() {
    override fun initUiState(): EmployeeDetailUiState {
        val employ = employWithSalary.employ
        val latestSalary = employWithSalary.salaries.maxBy { getGMTDateByString(it.date) }
        return EmployeeDetailUiState(
            title = "更改员工信息",
            actionName = "修改",
            dateDes = "工资变化时间",
            name = TextFieldContent(content = employ.name),
            phone = TextFieldContent(content = employ.phoneNumber.toString()),
            position = TextFieldContent(content = employ.position),
            date = getGMTDateByString(employ.hireDate),
            gender = employ.gender,
            basicSalary = latestSalary.basicSalary,
            allowance = latestSalary.allowance,
            bonus = latestSalary.bonus
        )
    }

    override fun update() {
        val uiState = innerEmployeeUiState.value
        verifyInputDate {
            val updateEmployRequest = UpdateEmployRequest(
                -1,
                uiState.name.content,
                uiState.phone.content.toLong(),
                uiState.position.content,
                uiState.gender,
                uiState.basicSalary,
                uiState.allowance,
                uiState.bonus,
                uiState.date.parse()
            )
        }
    }

    override fun onSalaryUpdate(salary: Int) {
        super.onSalaryUpdate(salary)
        changeDateToToday()
    }

    override fun onAllowanceUpdate(allowance: Int) {
        super.onAllowanceUpdate(allowance)
        changeDateToToday()
    }

    override fun onBonusUpdate(bonus: Int) {
        super.onBonusUpdate(bonus)
        changeDateToToday()
    }

    private fun changeDateToToday() {
        innerEmployeeUiState.update {
            it.copy(date = GMTDate(getTimeMillis()))
        }
    }

    private fun getGMTDateByString(timeString: String): GMTDate {
        val dateList = timeString.split(" ")[0].split("-").map { it.toInt() }
        val time = timeString.split(" ")[1].split(":").map { it.toInt() }
        return GMTDate(
            time[2],
            time[1],
            time[0],
            dateList[2],
            Month.Companion.from(dateList[1]),
            dateList[0]
        )
    }

}
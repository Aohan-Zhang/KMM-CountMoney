package link.peipei.countmoney.workflow.add

import io.ktor.util.date.GMTDate
import io.ktor.util.date.Month
import link.peipei.countmoney.core_ui.view.TextFieldContent
import link.peipei.countmoney.data.entities.EmployWithSalary

class UpdateEmployeeViewModel(private val employWithSalary: EmployWithSalary) :
    EmployeeDetailViewModel() {
    override fun initUiState(): EmployeeDetailUiState {
        val employ = employWithSalary.employ
        val latestSalary = employWithSalary.salaries.maxBy { getGMTDateByString(it.date) }
        return EmployeeDetailUiState(
            title = "更改员工信息",
            actionName = "修改",
            name = TextFieldContent(content = employ.name),
            phone = TextFieldContent(content = employ.phoneNumber.toString()),
            position = TextFieldContent(content = employ.position),
            hireDate = getGMTDateByString(employ.hireDate),
            hireDateEnable = false,
            gender = employ.gender,
            basicSalary = latestSalary.basicSalary,
            allowance = latestSalary.allowance,
            bonus = latestSalary.bonus
        )
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
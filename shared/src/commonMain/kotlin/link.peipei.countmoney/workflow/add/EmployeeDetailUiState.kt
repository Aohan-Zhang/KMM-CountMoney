package link.peipei.countmoney.workflow.add

import io.ktor.util.date.GMTDate
import io.ktor.util.date.getTimeMillis
import link.peipei.countmoney.core_ui.view.PhoneNumberTextFieldValidator
import link.peipei.countmoney.core_ui.view.TextFieldContent

data class EmployeeDetailUiState(
    val title: String,
    val actionName: String,
    val dateDes:String,
    val name: TextFieldContent = TextFieldContent(),
    val phone: TextFieldContent = TextFieldContent(validator = PhoneNumberTextFieldValidator),
    val position: TextFieldContent = TextFieldContent(),
    val date: GMTDate = GMTDate(getTimeMillis()),
    val gender: Int = 0,
    val basicSalary: Int = 0,
    val allowance: Int = 0,
    val bonus: Int = 0,
    val isUpdating: Boolean = false
)
package link.peipei.countmoney.data.entities

import kotlinx.serialization.Serializable

@Serializable
data class EmployWithSalary(
    val employ: EmployEntity,
    val salaries: List<SalaryEntity>
) {
    companion object {
        fun empty(): EmployWithSalary {
            val employ = EmployEntity("", -1, -1, "", "", -1, "")
            return EmployWithSalary(employ, listOf())
        }
    }
}
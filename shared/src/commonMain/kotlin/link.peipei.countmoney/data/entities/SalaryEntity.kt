package link.peipei.countmoney.data.entities

import kotlinx.serialization.Serializable

@Serializable
data class SalaryEntity(
    val id: String,
    val employId: String,
    val basicSalary: Int,
    val allowance: Int,
    val bonus: Int,
    val date: String,
)
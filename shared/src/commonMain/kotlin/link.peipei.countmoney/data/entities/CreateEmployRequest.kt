package link.peipei.countmoney.data.entities

import kotlinx.serialization.Serializable


@Serializable
data class CreateEmployRequest(
    val storeId: Long,
    val name: String,
    val phoneNumber: Long,
    val position: String,
    val gender: Int,
    val basicSalary: Int,
    val allowance: Int,
    val bonus: Int,
)
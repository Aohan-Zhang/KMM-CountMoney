package link.peipei.countmoney.data.entities

import kotlinx.serialization.Serializable

@Serializable
data class EmployEntity(
    val id: String,
    val storeId: Long,
    val phoneNumber: Long,
    val position: String,
    val hireDate: String,
    val gender: Int,
    val name: String
)

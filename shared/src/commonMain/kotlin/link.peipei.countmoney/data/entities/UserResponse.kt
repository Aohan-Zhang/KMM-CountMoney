package link.peipei.countmoney.data.entities

import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
    val name: String,
    val nickname: String,
    val userId: String,
    val gender: String,
    val birthdate: String,
    val picture: String,
    val phone: String?
)
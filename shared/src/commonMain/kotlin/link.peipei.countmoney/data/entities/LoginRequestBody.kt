package link.peipei.countmoney.data.entities

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequestBody(val phone: String, val smsCode: String)
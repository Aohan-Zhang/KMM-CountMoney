package link.peipei.countmoney.data.entities

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequestBody(val account: String, val password: String)
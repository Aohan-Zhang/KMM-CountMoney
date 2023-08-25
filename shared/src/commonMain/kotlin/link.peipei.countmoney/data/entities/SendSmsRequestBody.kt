package link.peipei.countmoney.data.entities

import kotlinx.serialization.Serializable

@Serializable
data class SendSmsRequestBody(val phoneNumber: String)
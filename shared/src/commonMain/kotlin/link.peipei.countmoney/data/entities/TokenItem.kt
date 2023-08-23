package link.peipei.countmoney.data.entities

import kotlinx.serialization.Serializable

@Serializable
data class TokenItem(
    val scope: String,
    val token_type: String,
    val access_token: String,
    val expires_in: String,
    val id_token: String
)
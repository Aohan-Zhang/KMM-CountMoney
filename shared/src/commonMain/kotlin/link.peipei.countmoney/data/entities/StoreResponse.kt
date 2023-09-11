package link.peipei.countmoney.data.entities

import kotlinx.serialization.Serializable


@Serializable
data class StoreResponse(
    val name: String,
    val des: String,
    val image: String?,
    val scale: Int,
    val industry: String,
    val enable: Boolean,
    val id: String,
    val owner: String
)

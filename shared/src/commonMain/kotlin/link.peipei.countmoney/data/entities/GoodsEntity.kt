package link.peipei.countmoney.data.entities

import kotlinx.serialization.Serializable

@Serializable
data class GoodsEntity(
    val id: String,
    val storeId: Long,
    val name: String,
    val price: Int,
    val specification:String,
    val unit:String,
    val ratio:Float
)


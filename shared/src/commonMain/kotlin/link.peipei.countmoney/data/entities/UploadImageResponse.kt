package link.peipei.countmoney.data.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class UploadImageData(
    val links: UploadImageLinks,
    val pathname: String
)

@Serializable
data class UploadImageLinks(
    val url: String,
    @SerialName("thumbnail_url")
    val thumbnailUrl: String
)
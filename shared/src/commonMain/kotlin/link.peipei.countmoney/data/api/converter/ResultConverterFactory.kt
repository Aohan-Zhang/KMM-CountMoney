package link.peipei.countmoney.data.api.converter

import de.jensklingenberg.ktorfit.Ktorfit
import de.jensklingenberg.ktorfit.converter.Converter
import de.jensklingenberg.ktorfit.internal.TypeData
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import link.peipei.countmoney.data.exception.HttpException

class ResultConverterFactory : Converter.Factory {
    override fun suspendResponseConverter(
        typeData: TypeData,
        ktorfit: Ktorfit
    ): Converter.SuspendResponseConverter<HttpResponse, *> {
        return object : Converter.SuspendResponseConverter<HttpResponse, Any> {
            override suspend fun convert(response: HttpResponse): Any {
                if (!response.status.value.toString().startsWith("2")) {
                    throw HttpException(response.status.value,response.body())
                }
                return response.body(typeData.typeInfo)
            }
        }
    }
}
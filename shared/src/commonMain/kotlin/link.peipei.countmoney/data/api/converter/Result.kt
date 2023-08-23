package link.peipei.countmoney.data.api.converter

sealed class Result<T> {
    data class Success<T>(val data: T) : Result<T>()
    class Error(val ex: Throwable) : Result<Nothing>()

    companion object {
        fun <T> success(data: T) = Success(data)
        fun error(ex: Throwable) = Error(ex)
    }
}
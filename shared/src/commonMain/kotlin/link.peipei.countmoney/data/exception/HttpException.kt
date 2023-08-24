package link.peipei.countmoney.data.exception

class HttpException(val httpCode: Int, val error: String) : Exception()
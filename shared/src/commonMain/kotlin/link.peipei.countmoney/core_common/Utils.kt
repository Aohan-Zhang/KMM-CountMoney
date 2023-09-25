package link.peipei.countmoney.core_common

import io.ktor.util.date.GMTDate

fun GMTDate.parse():String{
    return "$year-${month.ordinal}-$dayOfMonth $hours:$minutes:$seconds"
}
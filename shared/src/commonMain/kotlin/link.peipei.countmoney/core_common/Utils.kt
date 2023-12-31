package link.peipei.countmoney.core_common

import io.ktor.util.date.GMTDate

fun GMTDate.parse(): String {
    var monthIndex = "0${month.ordinal}"
    monthIndex = if (monthIndex.length == 3) {
        monthIndex.drop(1)
    } else {
        monthIndex
    }
    var dayIndex = "0${dayOfMonth}"
    dayIndex = if (dayIndex.length == 3) {
        dayIndex.drop(1)
    } else {
        dayIndex
    }
    val h = if (hours.toString().length == 2) hours else "0$hours"
    val m = if (minutes.toString().length == 2) minutes else "0$minutes"
    val s = if (seconds.toString().length == 2) seconds else "0$seconds"
    return "$year-$monthIndex-$dayIndex $h:$m:$s"
}
package link.peipei.countmoney.core_ui

inline fun digitalListener(s: String, block: (String) -> Unit) {
    if (s.isEmpty()) {
        block(s)
        return
    }
    val number = s.toLongOrNull() ?: return
    block(number.toString())

}
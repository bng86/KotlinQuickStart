package tw.andyang.kotlinquickstart.extension

import java.text.SimpleDateFormat
import java.util.*

object DateExtension {
    const val ISO8601 = "yyyy-MM-dd'T'HH:mm:ss.SSSZ"
}

fun Date.toDateString(pattern: String = "yyyy-MM-dd"): String {
    val format = SimpleDateFormat(pattern, Locale.getDefault())
    return format.format(this)
}

fun String.toDate(): Date =
        SimpleDateFormat(DateExtension.ISO8601, Locale.getDefault()).parse(this)
package dev.dprice.productivity.todo.platform.util

import kotlinx.datetime.LocalDateTime
import java.util.*

fun LocalDateTime.asTaskDateString(): String {
    return listOf(
        dayOfMonth.withDaySuffix(),
        month.name.lowercase()
            .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() },
        "-",
        "$hour:$minute"
    ).joinToString(" ")
}

fun Int.withDaySuffix() : String = toString() + when {
    toString().last() == '1' -> "st"
    toString().last() == '2' -> "nd"
    toString().last() == '3' -> "rd"
    else -> "th"
}
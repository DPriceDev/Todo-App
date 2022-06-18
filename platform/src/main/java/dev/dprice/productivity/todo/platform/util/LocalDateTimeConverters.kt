package dev.dprice.productivity.todo.platform.util

import kotlinx.datetime.LocalDateTime

fun LocalDateTime.asTaskDateString(): String = "$dayOfMonth $month $year"
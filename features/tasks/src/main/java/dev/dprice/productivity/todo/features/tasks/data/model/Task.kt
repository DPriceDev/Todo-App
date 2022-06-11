package dev.dprice.productivity.todo.features.tasks.data.model

import kotlinx.datetime.LocalDateTime

data class Task(
    val title: String,
    val description: String,
    val isComplete: Boolean,
    val dateTime: LocalDateTime
)

package dev.dprice.productivity.todo.features.task.model

import kotlinx.datetime.LocalDateTime

data class Task(
    val title: String,
    val description: String,
    val isComplete: Boolean,
    val dateTime: LocalDateTime
)

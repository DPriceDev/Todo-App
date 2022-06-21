package dev.dprice.productivity.todo.features.tasks.data.model

import kotlinx.datetime.LocalDateTime

data class Task(
    val id: String,
    val title: String,
    val description: String,
    val isComplete: Boolean = false,
    val dateTime: LocalDateTime
)

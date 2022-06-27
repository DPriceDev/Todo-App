package dev.dprice.productivity.todo.features.tasks.data.model

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

data class Task(
    val id: String,
    val title: String,
    val details: String,
    val finishDate: LocalDateTime,
    val creationDate: LocalDateTime = Clock.System.now().toLocalDateTime(
        TimeZone.currentSystemDefault()
    ),
    val deletionDate: LocalDateTime? = null,
    val isDeleted: Boolean = false,
    val isCompleted: Boolean = false,
    val groupId: String? = null
)

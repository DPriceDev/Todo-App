package dev.dprice.productivity.todo.features.tasks.data.model

data class Group(
    val id: String,
    val name: String,
    val description: String? = null,
    val colour: Int? = null
)

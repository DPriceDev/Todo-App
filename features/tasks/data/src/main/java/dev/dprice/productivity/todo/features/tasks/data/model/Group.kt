package dev.dprice.productivity.todo.features.tasks.data.model

data class Colour(
    val red: Int,
    val green: Int,
    val blue: Int
)

data class Group(
    val id: String,
    val name: String,
    val colour: Colour? = null
)

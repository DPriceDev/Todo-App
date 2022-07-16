package dev.dprice.productivity.todo.features.tasks.data.model

data class Group(
    val id: String,
    val name: String,
    val colour: Colour = Colour.DEFAULT,
    val icon: Icon = Icon.NONE,
    val isDeleted: Boolean = false
)
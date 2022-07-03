package dev.dprice.productivity.todo.features.tasks.screens.add.model

sealed interface ContentForm : Form<NewContentEntry> {
    val displayName: String
}
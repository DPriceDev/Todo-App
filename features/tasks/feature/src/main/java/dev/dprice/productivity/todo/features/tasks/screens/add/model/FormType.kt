package dev.dprice.productivity.todo.features.tasks.screens.add.model

enum class FormType(val displayName: String, val route: String) {
    TASK("New task", "new-task"),
    HABIT("New habit", "new-habit"),
    GROUP("New group", "new-group"),
}
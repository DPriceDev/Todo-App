package dev.dprice.productivity.todo.features.tasks.screens.add.model

data class NewContentState(
    val currentForm: ContentForm = NewTaskForm(),
    val forms: List<ContentForm> = listOf(
        NewTaskForm(),
        NewHabitForm(),
        NewGroupForm(),
    )
)
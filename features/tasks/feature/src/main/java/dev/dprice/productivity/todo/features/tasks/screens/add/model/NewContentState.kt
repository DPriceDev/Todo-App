package dev.dprice.productivity.todo.features.tasks.screens.add.model

data class NewContentState(
    val forms: List<ContentForm> = listOf(
        NewTaskForm(),
        NewHabitForm(),
        NewGroupForm()
    ),
    val selectedForm: Int = 0,
    val currentForm: ContentForm = forms[selectedForm]
)
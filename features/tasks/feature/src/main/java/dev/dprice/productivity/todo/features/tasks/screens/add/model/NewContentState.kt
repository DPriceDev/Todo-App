package dev.dprice.productivity.todo.features.tasks.screens.add.model

data class NewContentState(
    val taskForm: NewTaskForm = NewTaskForm(),
    val habitForm: NewHabitForm = NewHabitForm(),
    val groupForm: NewGroupForm = NewGroupForm(),
    val selectedContentType: NewContentType = NewContentType.TASK,
    val contentTypes: List<NewContentType> = listOf(
        NewContentType.TASK,
        NewContentType.HABIT,
        NewContentType.GROUP
    )
)
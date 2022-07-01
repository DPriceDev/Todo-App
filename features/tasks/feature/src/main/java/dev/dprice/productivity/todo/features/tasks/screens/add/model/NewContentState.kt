package dev.dprice.productivity.todo.features.tasks.screens.add.model

import dev.dprice.productivity.todo.ui.components.ButtonState

data class NewContentState(
    val taskForm: NewTaskForm = NewTaskForm(),
    val habitForm: NewTaskForm = NewTaskForm(),
    val groupForm: NewTaskForm = NewTaskForm(),
    val buttonState: ButtonState = ButtonState.DISABLED,
    val selectedContentType: NewContentType = NewContentType.TASK,
    val contentTypes: List<NewContentType> = listOf(
        NewContentType.TASK,
        NewContentType.HABIT,
        NewContentType.GROUP
    )
)
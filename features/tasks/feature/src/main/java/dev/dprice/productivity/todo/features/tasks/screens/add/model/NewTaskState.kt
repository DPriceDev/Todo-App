package dev.dprice.productivity.todo.features.tasks.screens.add.model

import dev.dprice.productivity.todo.ui.components.ButtonState

data class NewTaskState(
    val form: NewTaskForm = NewTaskForm(),
    val buttonState: ButtonState = ButtonState.DISABLED
)
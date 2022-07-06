package dev.dprice.productivity.todo.features.tasks.screens.add.model

import androidx.compose.runtime.Immutable

@Immutable
data class NewContentState(
    val forms: List<ContentForm> = listOf(
        NewTaskForm(),
        NewHabitForm(),
        NewGroupForm()
    ),
    val selectedForm: Int = 0,
    val currentForm: ContentForm = forms[selectedForm],
    val isDismissed: Boolean = false
)
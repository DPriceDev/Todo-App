package dev.dprice.productivity.todo.features.tasks.screens.add.model

import androidx.compose.runtime.Immutable

@Immutable
data class NewContentState(
    val forms: List<FormType> = listOf(
        FormType.TASK,
        FormType.HABIT,
        FormType.GROUP
    ),
    val selectedForm: FormType = FormType.TASK,
    val isDismissed: Boolean = false
)
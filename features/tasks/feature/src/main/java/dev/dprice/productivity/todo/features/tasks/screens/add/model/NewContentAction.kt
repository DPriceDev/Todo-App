package dev.dprice.productivity.todo.features.tasks.screens.add.model

import dev.dprice.productivity.todo.ui.components.FormAction

sealed interface NewContentAction {

    data class SelectContentType(val index: Int) : NewContentAction

    data class UpdateTaskForm(val action: FormAction<NewTaskForm.Type>) : NewContentAction
    data class UpdateGroupForm(val action: FormAction<NewGroupForm.Type>) : NewContentAction
    data class UpdateHabitForm(val action: FormAction<NewHabitForm.Type>) : NewContentAction
}


package dev.dprice.productivity.todo.features.tasks.screens.add.model

import dev.dprice.productivity.todo.ui.components.FormAction

enum class ContentType {
    TASK,
    HABIT,
    GROUP
}

sealed interface NewContentAction {

    data class SelectContentType(val index: Int) : NewContentAction

    data class UpdateForm(val action: FormAction<ContentForm.Type>) : NewContentAction
    object ShowGroupOnly : NewContentAction
}


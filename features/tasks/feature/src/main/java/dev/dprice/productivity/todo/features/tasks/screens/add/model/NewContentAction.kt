package dev.dprice.productivity.todo.features.tasks.screens.add.model


sealed interface NewContentAction {

    data class SelectContentType(val index: Int) : NewContentAction

    object ShowGroupOnly : NewContentAction
}


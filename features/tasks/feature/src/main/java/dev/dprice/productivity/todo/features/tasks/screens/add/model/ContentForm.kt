package dev.dprice.productivity.todo.features.tasks.screens.add.model

import dev.dprice.productivity.todo.ui.components.FormEntry

sealed interface ContentForm {
    val displayName: String

    val isValid: Boolean
    val entries: List<FormEntry<Type>>

    fun withEnablement(enabled: Boolean): ContentForm

    enum class Type {
        TITLE,
        DETAILS,
        SUBMIT
    }
}
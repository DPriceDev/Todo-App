package dev.dprice.productivity.todo.features.tasks.screens.add.model

import dev.dprice.productivity.todo.ui.components.FormEntry

interface Form<T> {
    val isValid: Boolean
    val entries: List<FormEntry<T>>

    fun withEnablement(enabled: Boolean): Form<T>
}
package dev.dprice.productivity.todo.ui.usecase

import dev.dprice.productivity.todo.ui.components.EntryField

interface UpdateEntryUseCase {
    operator fun invoke(
        entry: EntryField,
        newValue: String,
        newFocus: Boolean,
        trimEnd: Boolean = true,
        validator: (String) -> Boolean = { true },
    ): EntryField
}

class UpdateEntryUseCaseImpl : UpdateEntryUseCase {

    override operator fun invoke(
        entry: EntryField,
        newValue: String,
        newFocus: Boolean,
        trimEnd: Boolean,
        validator: (String) -> Boolean
    ): EntryField {
        val shortenedValue = newValue.take(entry.maxLength).let { if (trimEnd) it.trim() else it }
        val lostFocus = entry.hasFocus && !newFocus
        val isValid = validator(shortenedValue)
        val shouldValidate = entry.hasFocus && !newFocus && !isValid

        return entry.copy(
            value = shortenedValue,
            hasFocus = newFocus,
            isValid = isValid,
            shouldValidate = if (lostFocus) shouldValidate else entry.shouldValidate
        )
    }
}

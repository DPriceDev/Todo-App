package dev.dprice.productivity.todo.auth.usecases.updater

import dev.dprice.productivity.todo.ui.components.EntryField
import javax.inject.Inject

interface UpdateEntryUseCase {
    operator fun invoke(
        entry: EntryField,
        newValue: String,
        newFocus: Boolean,
        validator: (String) -> Boolean = { true }
    ): EntryField
}

class UpdateEntryUseCaseImpl @Inject constructor() : UpdateEntryUseCase {

    override operator fun invoke(
        entry: EntryField,
        newValue: String,
        newFocus: Boolean,
        validator: (String) -> Boolean
    ): EntryField {
        val shortenedValue = newValue.take(entry.maxLength).trim()
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

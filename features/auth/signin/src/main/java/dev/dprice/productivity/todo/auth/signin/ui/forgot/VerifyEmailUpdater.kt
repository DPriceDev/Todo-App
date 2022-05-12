package dev.dprice.productivity.todo.auth.signin.ui.forgot

import dev.dprice.productivity.todo.ui.components.EntryField
import javax.inject.Inject

interface VerifyEmailEntryUseCase {
    operator fun invoke(
        entry: EntryField,
        newEmail: String,
        newFocus: Boolean
    ): EntryField
}

// todo: create base use case and extend for each validator field
class VerifyEmailEntryUseCaseImpl @Inject constructor() : VerifyEmailEntryUseCase {

    override operator fun invoke(
        entry: EntryField,
        newEmail: String,
        newFocus: Boolean
    ): EntryField {
        val shortenedEmail = newEmail.trim().take(entry.maxLength)
        val lostFocus = entry.hasFocus && !newFocus
        val isValid = emailRegex.matches(shortenedEmail)
        val shouldValidate = entry.hasFocus && !newFocus && !isValid

        return entry.copy(
            value = shortenedEmail,
            hasFocus = newFocus,
            isValid = isValid,
            shouldValidate = if (lostFocus) shouldValidate else entry.shouldValidate
        )
    }

    companion object {
        private val emailRegex = """[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,8}""".toRegex()
    }
}

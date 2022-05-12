package dev.dprice.productivity.todo.auth.signin.ui.signin

import dev.dprice.productivity.todo.auth.signin.model.SignInAction
import dev.dprice.productivity.todo.auth.signin.model.SignInForm
import dev.dprice.productivity.todo.ui.components.EntryField
import javax.inject.Inject

interface SignInFormUpdater {
    fun updateEntry(form: SignInForm, action: SignInAction) : SignInForm
}

class SignInFormUpdaterImpl @Inject constructor() : SignInFormUpdater {

    override fun updateEntry(form: SignInForm, action: SignInAction): SignInForm {
        return when (action.type) {
            SignInAction.Type.UPDATE_USERNAME -> form.copy(
                username = updateEntry(form.username, action.value, action.focus)
            )
            SignInAction.Type.UPDATE_PASSWORD -> form.copy(
                password = updateEntry(form.password, action.value, action.focus)
            )
        }
    }

    private fun updateEntry(
        entry: EntryField,
        newValue: String,
        newFocus: Boolean,
        validator: (String) -> Boolean = { true }
    ): EntryField {
        val shortenedValue = newValue.take(entry.maxLength).trim()
        val lostFocus = entry.hasFocus && !newFocus
        val isValid = validator(shortenedValue)
        val shouldValidate = entry.hasFocus && !newFocus && !isValid

        return entry.copy(
            value = shortenedValue,
            hasFocus = newFocus,
            isValid = isValid,
            shouldValidate = if(lostFocus) shouldValidate else entry.shouldValidate
        )
    }
}
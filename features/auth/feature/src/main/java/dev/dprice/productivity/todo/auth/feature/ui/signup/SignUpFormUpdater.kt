package dev.dprice.productivity.todo.auth.feature.ui.signup

import dev.dprice.productivity.todo.auth.feature.model.signup.SignUpAction
import dev.dprice.productivity.todo.auth.feature.model.signup.SignUpForm
import dev.dprice.productivity.todo.ui.components.EntryField
import javax.inject.Inject

interface SignUpFormUpdater {
    fun updateEntry(form: SignUpForm, action: SignUpAction) : SignUpForm
}

class SignUpFormUpdaterImpl @Inject constructor() : SignUpFormUpdater {

    override fun updateEntry(form: SignUpForm, action: SignUpAction): SignUpForm {
        return when (action.type) {
            SignUpAction.Type.UPDATE_EMAIL -> form.copy(
                email = updateEntry(form.email, action.value, action.focus) {
                    validate(it, emailRegex)
                }
            )
            SignUpAction.Type.UPDATE_USERNAME -> form.copy(
                username = updateEntry(form.username, action.value, action.focus) {
                    validate(it, usernameRegex)
                }
            )
            SignUpAction.Type.UPDATE_PASSWORD -> form.copy(
                password = updateEntry(form.password, action.value, action.focus) {
                    validate(it, passwordRegex, minimumPasswordLength)
                }
            )
        }
    }

    private fun validate(value: String, regex: Regex, minLength: Int = 0) : Boolean {
        return value.length >= minLength && value.matches(regex)
    }

    private fun updateEntry(
        entry: EntryField,
        newValue: String,
        newFocus: Boolean,
        validator: (String) -> Boolean
    ): EntryField {
        val shortenedValue = newValue.take(entry.maxLength)
        val lostFocus = entry.hasFocus && !newFocus
        val isValid = validator(shortenedValue)
        val shouldValidate = entry.hasFocus && !newFocus && !isValid

        return entry.copy(
            value = shortenedValue,
            hasFocus = newFocus,
            isValid = if(shouldValidate || entry.shouldValidate) isValid else entry.isValid,
            shouldValidate = if(lostFocus) shouldValidate else entry.shouldValidate
        )
    }

    companion object {
        // todo: Add valid regex
        private val emailRegex = """""".toRegex()
        private val usernameRegex = """([A-Z])\w+""".toRegex()
        private val passwordRegex = """""".toRegex()
        private const val minimumPasswordLength = 8
    }
}
package dev.dprice.productivity.todo.auth.feature.screens.signup.model

sealed class SignUpEvent {
    data class UpdateUsernameValue(val value: String) : SignUpEvent()
    data class UpdateUsernameFocus(val focus: Boolean) : SignUpEvent()

    data class UpdateEmailValue(val value: String) : SignUpEvent()
    data class UpdateEmailFocus(val focus: Boolean) : SignUpEvent()

    data class UpdatePasswordValue(val value: String) : SignUpEvent()
    data class UpdatePasswordFocus(val focus: Boolean) : SignUpEvent()
}

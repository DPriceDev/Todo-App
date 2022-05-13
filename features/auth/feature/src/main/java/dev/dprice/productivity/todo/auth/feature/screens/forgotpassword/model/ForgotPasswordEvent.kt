package dev.dprice.productivity.todo.auth.feature.screens.forgotpassword.model

sealed class ForgotPasswordEvent {
    data class UpdateUsernameFocus(val focus: Boolean) : ForgotPasswordEvent()
    data class UpdateUsernameValue(val value: String) : ForgotPasswordEvent()
}

package dev.dprice.productivity.todo.auth.feature.screens.verify.model

sealed class VerifyCodeEvent {
    data class UpdateCodeValue(val value: String) : VerifyCodeEvent()
    data class UpdateCodeFocus(val focus: Boolean) : VerifyCodeEvent()
}
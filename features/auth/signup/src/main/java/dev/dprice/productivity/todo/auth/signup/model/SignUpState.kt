package dev.dprice.productivity.todo.auth.signup.model

import dev.dprice.productivity.todo.ui.components.ButtonEnablement

sealed class ErrorState {
    object None: ErrorState()
    data class Message(val message: String) : ErrorState()
}

data class SignUpState(
    val form: SignUpForm = SignUpForm(),
    val buttonEnablement: ButtonEnablement = ButtonEnablement.DISABLED,
    val error: ErrorState = ErrorState.None
)
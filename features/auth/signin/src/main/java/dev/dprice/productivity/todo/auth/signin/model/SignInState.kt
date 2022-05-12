package dev.dprice.productivity.todo.auth.signin.model

import dev.dprice.productivity.todo.ui.components.ButtonState

sealed class ErrorState {
    object None : ErrorState()
    data class Message(val message: String) : ErrorState()
}

data class SignInState(
    val form: SignInForm = SignInForm(),
    val buttonState: ButtonState = ButtonState.DISABLED,
    val error: ErrorState = ErrorState.None
)

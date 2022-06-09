package dev.dprice.productivity.todo.auth.feature.screens.signup.model

import dev.dprice.productivity.todo.auth.feature.model.ErrorState
import dev.dprice.productivity.todo.ui.components.ButtonState

data class SignUpState(
    val form: SignUpForm = SignUpForm(),
    val buttonState: ButtonState = ButtonState.DISABLED,
    val errorState: ErrorState = ErrorState.None
)

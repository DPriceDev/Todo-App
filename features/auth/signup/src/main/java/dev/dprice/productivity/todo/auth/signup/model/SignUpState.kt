package dev.dprice.productivity.todo.auth.signup.model

import dev.dprice.productivity.todo.ui.components.ButtonEnablement

data class SignUpState(
    val form: SignUpForm = SignUpForm(),
    val buttonEnablement: ButtonEnablement = ButtonEnablement.DISABLED
)
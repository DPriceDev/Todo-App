package dev.dprice.productivity.todo.auth.signup.model

data class SignUpState(
    val form: SignUpForm = SignUpForm(),
    val canSubmit: Boolean = false
)
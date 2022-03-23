package dev.dprice.productivity.todo.auth.signin.model

data class SignInState(
    val form: SignInForm = SignInForm(),
    val canSubmit: Boolean = false
)
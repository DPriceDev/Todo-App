package dev.dprice.productivity.todo.auth.feature.model.signin

data class SignInState(
    val form: SignInForm = SignInForm(),
    val canSubmit: Boolean = false
)
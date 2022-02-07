package dev.dprice.productivity.todo.auth.feature.model.signup

data class SignUpState(
    val form: SignUpForm = SignUpForm(),
    val canSubmit: Boolean = false
)
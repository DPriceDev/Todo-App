package dev.dprice.productivity.todo.auth.feature.screens.signup.model

data class SignUpAction(
    val value: String,
    val focus: Boolean,
    val type: Type
) {
    enum class Type {
        UPDATE_EMAIL,
        UPDATE_USERNAME,
        UPDATE_PASSWORD
    }
}

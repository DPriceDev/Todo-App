package dev.dprice.productivity.todo.auth.feature.model.signin

data class SignInAction(
    val value: String,
    val focus: Boolean,
    val type: Type
) {
    enum class Type {
        UPDATE_USERNAME,
        UPDATE_PASSWORD
    }
}

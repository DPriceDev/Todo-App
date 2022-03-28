package dev.dprice.productivity.todo.auth.verify.model


sealed class VerifyErrorState {
    object None : VerifyErrorState()
    data class Error(val message: String) : VerifyErrorState()
}

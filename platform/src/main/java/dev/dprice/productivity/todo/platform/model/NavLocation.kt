package dev.dprice.productivity.todo.platform.model

// todo make generic
// todo better to define this as an interface and pass the locations around?
sealed class NavLocation(
    val route: String
) {
    object Notes : NavLocation("Notes")

    sealed class Auth(route: String) : NavLocation(route) {
        object SignUp : Auth("auth-sign-up")
    }
}

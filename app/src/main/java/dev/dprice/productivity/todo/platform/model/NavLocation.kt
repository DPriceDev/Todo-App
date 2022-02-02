package dev.dprice.productivity.todo.platform.model

sealed class NavLocation(
    val route: String
) {
    object Notes : NavLocation("Notes")
}

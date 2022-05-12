package dev.dprice.productivity.todo.platform.model

import androidx.navigation.NamedNavArgument

// todo make generic
// todo better to define this as an interface and pass the locations around?
sealed class NavLocation(
    override val route: String,
    override val navArguments: List<NamedNavArgument> = emptyList()
) : NavDestination {
    object Notes : NavLocation("Notes")
    object Auth : NavLocation("Auth")
}

interface NavDestination {
    val route: String
    val navArguments: List<NamedNavArgument>
}

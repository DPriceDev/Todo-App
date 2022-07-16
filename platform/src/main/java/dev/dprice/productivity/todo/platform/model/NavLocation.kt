package dev.dprice.productivity.todo.platform.model

import androidx.navigation.NamedNavArgument

// todo make generic
// todo better to define this as an interface and pass the locations around?
sealed class NavLocation(
    override val route: String,
    override val navArguments: List<NamedNavArgument> = emptyList()
) : NavDestination {
    object Main : NavLocation("Main")
    object Tasks : NavLocation("tasks")
    object TasksList : NavLocation("tasks/list")
    object TasksDetail : NavLocation("tasks/detail")
    object TasksNewContent : NavLocation("tasks/new?groupOnly={groupOnly}") {
        fun withArguments(isGroupOnly: Boolean) = "tasks/new?groupOnly=$isGroupOnly"
    }
    object Auth : NavLocation("Auth")
    object Settings : NavLocation("Settings")
}

interface NavDestination {
    val route: String
    val navArguments: List<NamedNavArgument>
}

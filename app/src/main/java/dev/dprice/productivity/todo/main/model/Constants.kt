package dev.dprice.productivity.todo.main.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Task
import androidx.compose.ui.graphics.vector.ImageVector
import dev.dprice.productivity.todo.platform.model.NavLocation

object Constants {
    data class BottomNavItem(
        val label: String,
        val icon: ImageVector,
        val route: String,
    )

    val BottomNavItems = listOf(
        BottomNavItem(
            label = "Tasks",
            icon = Icons.Filled.Task,
            route = NavLocation.TasksList.route
        ),
        BottomNavItem(
            label = "Settings",
            icon = Icons.Filled.Settings,
            route = NavLocation.Settings.route
        )
    )
}
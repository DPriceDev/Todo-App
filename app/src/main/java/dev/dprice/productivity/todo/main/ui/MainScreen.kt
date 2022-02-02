package dev.dprice.productivity.todo.main.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.dprice.productivity.todo.features.task.ui.list.TaskListUi
import dev.dprice.productivity.todo.platform.model.NavLocation
import dev.dprice.productivity.todo.platform.theme.TodoAppTheme

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = NavLocation.Notes.route
    ) {
        composable(NavLocation.Notes.route) {
            TaskListUi()
        }
    }
}


/**
 * Previews
 */

@Preview
@Composable
private fun PreviewLayout() {
    TodoAppTheme {
        MainScreen()
    }
}
package dev.dprice.productivity.todo.main.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.dprice.productivity.todo.auth.feature.ui.signup.SignUp
import dev.dprice.productivity.todo.features.tasks.ui.list.TaskListUi
import dev.dprice.productivity.todo.platform.model.NavLocation
import dev.dprice.productivity.todo.ui.theme.TodoAppTheme

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = NavLocation.Auth.SignUp.route
    ) {
        composable(NavLocation.Auth.SignUp.route) {
            SignUp()
        }

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
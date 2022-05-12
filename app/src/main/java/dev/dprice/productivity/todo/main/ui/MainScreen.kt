package dev.dprice.productivity.todo.main.ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.navigation.animation.navigation
import dev.dprice.productivity.todo.auth.feature.screens.base.AuthScreen
import dev.dprice.productivity.todo.features.tasks.ui.list.TaskListUi
import dev.dprice.productivity.todo.platform.model.NavLocation
import dev.dprice.productivity.todo.ui.theme.TodoAppTheme

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "Auth",
    ) {
        composable("Auth") {
            AuthScreen(navController = navController)
        }

        navigation(route = "MainApp", startDestination = NavLocation.Notes.route) {
            composable(NavLocation.Notes.route) {
                TaskListUi()
            }
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
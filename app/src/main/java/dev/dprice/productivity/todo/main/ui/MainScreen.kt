package dev.dprice.productivity.todo.main.ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.navigation.animation.navigation
import dev.dprice.productivity.todo.auth.feature.screens.base.AuthScreen
import dev.dprice.productivity.todo.features.settings.ui.SettingsScreen
import dev.dprice.productivity.todo.features.settings.ui.SettingsViewModelImpl
import dev.dprice.productivity.todo.features.tasks.ui.list.TaskListScreen
import dev.dprice.productivity.todo.main.model.MainState
import dev.dprice.productivity.todo.platform.model.NavLocation
import dev.dprice.productivity.todo.ui.theme.TodoAppTheme

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MainScreen(
    state: MainState,
    navController: NavHostController
) {
    LaunchedEffect(key1 = state.userSession) {
        state.userSession?.let { session ->
            if (!session.isSignedIn) {
                navController.navigate("Auth")
            }
        }
    }

    when (state.isLoading) {
        true -> {
            Box(
                modifier = Modifier.fillMaxSize()
            ) { }
        }
        else -> {
            NavHost(
                navController = navController,
                startDestination = if (state.userSession?.isSignedIn == true) "MainApp" else "Auth",
            ) {
                composable("Auth") {
                    AuthScreen(navController = navController)
                }

                navigation(route = "MainApp", startDestination = NavLocation.Notes.route) {
                    composable(NavLocation.Notes.route) {
                        TaskListScreen()
                    }

                    composable(NavLocation.Settings.route) {
                        val viewModel = hiltViewModel<SettingsViewModelImpl>()
                        SettingsScreen(
                            viewModel.viewState,
                            navController
                        )
                    }
                }
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
        MainScreen(
            MainState(),
            rememberNavController()
        )
    }
}

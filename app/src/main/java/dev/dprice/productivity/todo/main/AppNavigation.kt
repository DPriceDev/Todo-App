package dev.dprice.productivity.todo.main

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.accompanist.navigation.animation.navigation
import dev.dprice.productivity.todo.auth.feature.navigation.authNavigation
import dev.dprice.productivity.todo.features.settings.ui.SettingsScreen
import dev.dprice.productivity.todo.features.settings.ui.SettingsViewModelImpl
import dev.dprice.productivity.todo.features.tasks.navigation.tasksNavigation
import dev.dprice.productivity.todo.platform.model.NavLocation
import dev.dprice.productivity.todo.ui.components.scaffold.WavyScaffoldState

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AppNavigation(
    wavyState: WavyScaffoldState,
    isSignedIn: Boolean?,
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = if (isSignedIn == true) NavLocation.Main.route else NavLocation.Auth.route,
    ) {
        authNavigation(navController)

        navigation(
            route = NavLocation.Main.route,
            startDestination = NavLocation.Tasks.route
        ) {
            tasksNavigation(
                navController = navController,
                modifier = modifier,
                wavyState = wavyState
            )

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
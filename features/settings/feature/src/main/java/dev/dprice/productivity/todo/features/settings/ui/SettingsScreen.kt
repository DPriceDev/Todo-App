package dev.dprice.productivity.todo.features.settings.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import dev.dprice.productivity.todo.features.settings.model.SettingsState

@Composable
fun SettingsScreen(
    state: SettingsState,
    navHostController: NavHostController
) {
    state.components.forEach {
        it.Composable(navController = navHostController)
    }
}

package dev.dprice.productivity.todo.features.settings.data.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

interface SettingsComponent {
    val name: String

    @Composable
    fun Composable(navController: NavHostController)
}

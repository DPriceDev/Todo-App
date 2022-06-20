package dev.dprice.productivity.todo.auth.feature.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import dev.dprice.productivity.todo.auth.feature.screens.base.AuthScreen
import dev.dprice.productivity.todo.platform.model.NavLocation

fun NavGraphBuilder.authNavigation(navController: NavHostController) {
    composable(NavLocation.Auth.route) {
        AuthScreen(navController = navController)
    }
}
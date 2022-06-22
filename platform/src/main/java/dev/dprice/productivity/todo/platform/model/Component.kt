package dev.dprice.productivity.todo.platform.model

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

interface Component {
    val name: String

    @Composable
    fun Composable(navController: NavHostController)
}
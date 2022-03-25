package dev.dprice.productivity.todo.ui.navigation

import androidx.compose.ui.unit.Dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import dev.dprice.productivity.todo.ui.components.WavyScaffoldState

interface AuthNavigationComponent {

    val navLocation: AuthNavLocation

    fun navigationContent(
        builder: NavGraphBuilder,
        authNavHostController: NavHostController,
        appNavHostController: NavHostController,
        state: WavyScaffoldState,
        maxHeight: Dp,
        maxWidth: Dp,
    )

    suspend fun updateWavyState(
        state: WavyScaffoldState,
        maxHeight: Dp,
        maxWidth: Dp,
    )
}
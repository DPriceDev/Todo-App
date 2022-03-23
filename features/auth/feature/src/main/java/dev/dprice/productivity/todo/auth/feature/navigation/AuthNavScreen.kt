package dev.dprice.productivity.todo.auth.feature.navigation

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import dev.dprice.productivity.todo.auth.feature.model.AuthNavLocation
import dev.dprice.productivity.todo.ui.components.WavyScaffoldState

interface AuthNavScreen {

    val navLocation: AuthNavLocation

    @Composable
    fun TopContent(
        maxHeight: Dp,
        maxWidth: Dp,
        scope: AnimatedVisibilityScope
    )

    fun bottomNavigationContent(
        builder: NavGraphBuilder,
        authNavHostController: NavHostController,
        appNavHostController: NavHostController,
        state: WavyScaffoldState,
        config: WavyScaffoldState.Config,
        maxHeight: Dp,
        maxWidth: Dp,
    )

    fun updateWavyState(
        state: WavyScaffoldState,
        maxHeight: Dp,
        maxWidth: Dp,
    )
}
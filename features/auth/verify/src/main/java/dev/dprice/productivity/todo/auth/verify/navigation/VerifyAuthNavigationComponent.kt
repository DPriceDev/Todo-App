package dev.dprice.productivity.todo.auth.verify.navigation

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import dev.dprice.productivity.todo.auth.verify.ui.VerifyCodeBottomContent
import dev.dprice.productivity.todo.auth.verify.ui.VerifyCodeTopContent
import dev.dprice.productivity.todo.ui.components.WavyScaffoldState
import dev.dprice.productivity.todo.ui.navigation.AuthNavLocation
import dev.dprice.productivity.todo.ui.navigation.AuthNavigationComponent

class VerifyAuthNavigationComponent : AuthNavigationComponent {
    override val navLocation: AuthNavLocation = AuthNavLocation.VerifySignUp

    @Composable
    override fun TopContent(maxHeight: Dp, maxWidth: Dp, scope: AnimatedVisibilityScope) {
        VerifyCodeTopContent()
    }

    override fun bottomNavigationContent(
        builder: NavGraphBuilder,
        authNavHostController: NavHostController,
        appNavHostController: NavHostController,
        state: WavyScaffoldState,
        config: WavyScaffoldState.Config,
        maxHeight: Dp,
        maxWidth: Dp
    ) {
        builder.composable(route = AuthNavLocation.VerifySignUp.route) {
            updateWavyState(state, maxHeight, maxWidth)
            VerifyCodeBottomContent()
        }
    }

    override fun updateWavyState(state: WavyScaffoldState, maxHeight: Dp, maxWidth: Dp) {
        state.targetPosition.value = 128.dp
        state.targetFrequency.value = 0.3f
        state.targetHeight.value = 128.dp
        state.waveDuration.value = 15_000
    }
}
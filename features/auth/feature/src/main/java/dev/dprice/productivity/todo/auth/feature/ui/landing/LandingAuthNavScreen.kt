package dev.dprice.productivity.todo.auth.feature.ui.landing

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.composable
import dev.dprice.productivity.todo.auth.feature.model.AuthNavLocation
import dev.dprice.productivity.todo.auth.feature.navigation.AuthNavScreen
import dev.dprice.productivity.todo.ui.components.WavyScaffoldState

@OptIn(ExperimentalAnimationApi::class)
class LandingAuthNavScreen : AuthNavScreen {
    override val navLocation: AuthNavLocation = AuthNavLocation.Landing

    @Composable
    override fun TopContent(
        maxHeight: Dp,
        maxWidth: Dp,
        scope: AnimatedVisibilityScope
    ) {
        AuthLandingTopContent(backDropHeight = maxHeight - 264.dp)
    }

    override fun bottomNavigationContent(
        builder: NavGraphBuilder,
        authNavHostController: NavHostController,
        appNavHostController: NavHostController,
        state: WavyScaffoldState,
        config: WavyScaffoldState.Config,
        maxHeight: Dp,
        maxWidth: Dp,
    ) {
        builder.composable(route = AuthNavLocation.Landing.route) {
            updateWavyState(state, maxHeight, maxWidth)
            AuthLandingBottomContent(
                state,
                goToSignUp = { authNavHostController.navigate(AuthNavLocation.SignUp.route) },
                goToSignIn = { authNavHostController.navigate(AuthNavLocation.SignIn.route) }
            )
        }
    }

    override fun updateWavyState(
        state: WavyScaffoldState,
        maxHeight: Dp,
        maxWidth: Dp,
    ) {
        state.targetPosition.value = maxHeight - 264.dp
        state.targetFrequency.value = 0.3f
        state.targetHeight.value = 48.dp
        state.waveDuration.value = 15_000
    }
}
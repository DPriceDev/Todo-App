package dev.dprice.productivity.todo.auth.feature.ui.signup

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
class SignUpAuthNavScreen : AuthNavScreen {
    override val navLocation: AuthNavLocation = AuthNavLocation.SignUp

    @Composable
    override fun TopContent(
        maxHeight: Dp,
        maxWidth: Dp,
        scope: AnimatedVisibilityScope
    ) {
        SignUpTopContent()
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
        builder.composable(route = AuthNavLocation.SignUp.route) {
            updateWavyState(state, maxHeight, maxWidth)
            SignUp(
                goToVerifyCode = { authNavHostController.navigate(AuthNavLocation.VerifySignUp.route) },
                goToSignIn = { authNavHostController.navigate(AuthNavLocation.SignIn.route) }
            )
        }
    }

    override fun updateWavyState(
        state: WavyScaffoldState,
        maxHeight: Dp,
        maxWidth: Dp
    ) {
        state.targetPosition.value = 128.dp
        state.targetFrequency.value = 0.3f
        state.targetHeight.value = 128.dp
        state.waveDuration.value = 15_000
    }

}
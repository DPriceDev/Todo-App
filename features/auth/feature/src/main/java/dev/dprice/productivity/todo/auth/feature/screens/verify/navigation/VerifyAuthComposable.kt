package dev.dprice.productivity.todo.auth.feature.screens.verify.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.composable
import dev.dprice.productivity.todo.auth.feature.model.AuthNavLocation
import dev.dprice.productivity.todo.auth.feature.screens.verify.ui.VerifyCode
import dev.dprice.productivity.todo.ui.components.WavyScaffoldState

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.verifyCodeComposable(
    authNavHostController: NavHostController,
    appNavHostController: NavHostController,
    state: WavyScaffoldState
) {
    composable(
        route = AuthNavLocation.VerifySignUp.route,
        enterTransition = {
            slideIntoContainer(
                AnimatedContentScope.SlideDirection.Left,
                animationSpec = tween(durationMillis = 700)
            )
        },
        exitTransition = {
            slideOutOfContainer(
                AnimatedContentScope.SlideDirection.Right,
                animationSpec = tween(durationMillis = 700)
            )
        }
    ) { backStackEntry ->
        LaunchedEffect(key1 = true) {
            state.animate(
                backDropHeight = 128.dp,
                frequency = 0.3f,
                waveHeight = 128.dp,
                duration = 15_000
            )
        }

        VerifyCode(
            state = state,
            username = backStackEntry
                .arguments
                ?.getString(AuthNavLocation.VerifySignUp.USERNAME)
                ?: throw Throwable("Missing username provided to verify code"),
            goToMainApp = { appNavHostController.navigate("MainApp") },
            goBack = {
                authNavHostController.popBackStack(
                    route = AuthNavLocation.Landing.route,
                    inclusive = false
                )
            }
        )
    }
}

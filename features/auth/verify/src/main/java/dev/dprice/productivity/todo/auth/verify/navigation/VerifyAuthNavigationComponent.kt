package dev.dprice.productivity.todo.auth.verify.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.composable
import dev.dprice.productivity.todo.auth.verify.ui.VerifyCode
import dev.dprice.productivity.todo.ui.components.WavyScaffoldState
import dev.dprice.productivity.todo.ui.navigation.AuthNavLocation
import dev.dprice.productivity.todo.ui.navigation.AuthNavigationComponent

@OptIn(ExperimentalAnimationApi::class)
class VerifyAuthNavigationComponent : AuthNavigationComponent {
    override val navLocation: AuthNavLocation = AuthNavLocation.VerifySignUp

    override fun navigationContent(
        builder: NavGraphBuilder,
        authNavHostController: NavHostController,
        appNavHostController: NavHostController,
        state: WavyScaffoldState,
        maxHeight: Dp,
        maxWidth: Dp
    ) {
        builder.composable(
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
        ) {
            VerifyCode(state)
        }
    }

    override suspend fun updateWavyState(state: WavyScaffoldState, maxHeight: Dp, maxWidth: Dp) {
        state.animate(
            backDropHeight = 128.dp,
            frequency = 0.3f,
            waveHeight = 128.dp,
            duration = 15_000
        )
    }
}
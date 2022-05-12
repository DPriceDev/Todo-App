package dev.dprice.productivity.todo.auth.feature.screens.forgotpassword.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.composable
import dev.dprice.productivity.todo.auth.feature.screens.forgotpassword.ui.ForgotPassword
import dev.dprice.productivity.todo.auth.feature.model.AuthNavLocation
import dev.dprice.productivity.todo.auth.feature.ui.navigation.AuthNavigationComponent
import dev.dprice.productivity.todo.ui.components.WavyScaffoldState
import javax.inject.Inject

class ForgotPasswordNavigationComponent @Inject constructor() : AuthNavigationComponent {

    override val navLocation: AuthNavLocation = AuthNavLocation.ForgotPassword

    @OptIn(ExperimentalAnimationApi::class)
    override fun navigationContent(
        builder: NavGraphBuilder,
        authNavHostController: NavHostController,
        appNavHostController: NavHostController,
        state: WavyScaffoldState,
        maxHeight: Dp,
        maxWidth: Dp
    ) {
        builder.composable(
            route = AuthNavLocation.ForgotPassword.route,
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
            ForgotPassword(
                state = state,
                goToResetPassword = {
                    authNavHostController.navigate(AuthNavLocation.ResetPassword.route)
                }
            )
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

package dev.dprice.productivity.todo.auth.signin.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.composable
import dev.dprice.productivity.todo.auth.signin.ui.SignIn
import dev.dprice.productivity.todo.ui.components.WavyScaffoldState
import dev.dprice.productivity.todo.ui.navigation.AuthNavLocation
import dev.dprice.productivity.todo.ui.navigation.AuthNavigationComponent
import javax.inject.Inject

@OptIn(ExperimentalAnimationApi::class)
class SignInAuthNavigationComponent @Inject constructor() : AuthNavigationComponent {

    override val navLocation: AuthNavLocation = AuthNavLocation.SignIn

    override fun navigationContent(
        builder: NavGraphBuilder,
        authNavHostController: NavHostController,
        appNavHostController: NavHostController,
        state: WavyScaffoldState,
        maxHeight: Dp,
        maxWidth: Dp
    ) {
        builder.composable(
            route = AuthNavLocation.SignIn.route,
            enterTransition = {
                when (initialState.destination.route) {
                    AuthNavLocation.ForgotPassword.route -> slideIntoContainer(
                        AnimatedContentScope.SlideDirection.Right,
                        animationSpec = tween(durationMillis = 700)
                    )
                    else -> null
                }
            },
            exitTransition = {
                when (targetState.destination.route) {
                    AuthNavLocation.ForgotPassword.route -> slideOutOfContainer(
                        AnimatedContentScope.SlideDirection.Left,
                        animationSpec = tween(durationMillis = 700)
                    )
                    else -> null
                }
            }
        ) {
            SignIn(
                state = state,
                goToMainApp = { appNavHostController.navigate("MainApp") },
                goToVerifyCode = { authNavHostController.navigate(AuthNavLocation.VerifySignUp.location(it)) },
                goToSignUp = {
                    authNavHostController.navigate(AuthNavLocation.SignUp.route) {
                        popUpTo(AuthNavLocation.SignIn.route) { inclusive = true }
                    }
                },
                goToForgotPassword = {
                    authNavHostController.navigate(AuthNavLocation.ForgotPassword.route)
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
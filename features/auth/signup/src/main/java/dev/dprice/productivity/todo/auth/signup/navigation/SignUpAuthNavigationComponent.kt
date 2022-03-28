package dev.dprice.productivity.todo.auth.signup.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.composable
import dev.dprice.productivity.todo.auth.signup.ui.SignUp
import dev.dprice.productivity.todo.ui.components.WavyScaffoldState
import dev.dprice.productivity.todo.ui.navigation.AuthNavLocation
import dev.dprice.productivity.todo.ui.navigation.AuthNavigationComponent
import javax.inject.Inject

@OptIn(ExperimentalAnimationApi::class)
class SignUpAuthNavigationComponent @Inject constructor() : AuthNavigationComponent {
    override val navLocation: AuthNavLocation = AuthNavLocation.SignUp

    override fun navigationContent(
        builder: NavGraphBuilder,
        authNavHostController: NavHostController,
        appNavHostController: NavHostController,
        state: WavyScaffoldState,
        maxHeight: Dp,
        maxWidth: Dp
    ) {
        builder.composable(
            route = AuthNavLocation.SignUp.route,
            enterTransition = {
                when(initialState.destination.route) {
                    AuthNavLocation.VerifySignUp.route -> slideIntoContainer(
                        AnimatedContentScope.SlideDirection.Right,
                        animationSpec = tween(durationMillis = 700)
                    )
                    else -> null
                }
            },
            exitTransition = {
                when(targetState.destination.route) {
                    AuthNavLocation.VerifySignUp.route -> slideOutOfContainer(
                        AnimatedContentScope.SlideDirection.Left,
                        animationSpec = tween(durationMillis = 700)
                    )
                    else -> null
                }
            }
        ) {
            SignUp(
                state = state,
                goToVerifyCode = { authNavHostController.navigate(AuthNavLocation.VerifySignUp.location(it)) },
                goToSignIn = { authNavHostController.navigate(AuthNavLocation.SignIn.route) },
                goToMainApp = { appNavHostController.navigate("MainApp") }
            )
        }
    }

    override suspend fun updateWavyState(
        state: WavyScaffoldState,
        maxHeight: Dp,
        maxWidth: Dp
    ) {
        state.animate(
            backDropHeight = 128.dp,
            frequency = 0.3f,
            waveHeight = 128.dp,
            duration = 15_000
        )
    }

}
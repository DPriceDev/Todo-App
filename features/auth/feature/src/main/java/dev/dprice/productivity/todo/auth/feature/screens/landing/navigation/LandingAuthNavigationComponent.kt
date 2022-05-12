package dev.dprice.productivity.todo.auth.feature.screens.landing.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.composable
import dev.dprice.productivity.todo.auth.feature.screens.landing.ui.AuthLanding
import dev.dprice.productivity.todo.auth.feature.model.AuthNavLocation
import dev.dprice.productivity.todo.auth.feature.ui.navigation.AuthNavigationComponent
import dev.dprice.productivity.todo.ui.components.WavyScaffoldState
import javax.inject.Inject

@OptIn(ExperimentalAnimationApi::class)
class LandingAuthNavigationComponent @Inject constructor() : AuthNavigationComponent {
    override val navLocation: AuthNavLocation = AuthNavLocation.Landing

    override fun navigationContent(
        builder: NavGraphBuilder,
        authNavHostController: NavHostController,
        appNavHostController: NavHostController,
        state: WavyScaffoldState,
        maxHeight: Dp,
        maxWidth: Dp,
    ) {
        builder.composable(
            route = AuthNavLocation.Landing.route,
            enterTransition = {
                when (initialState.destination.route) {
                    AuthNavLocation.VerifySignUp.route -> slideIntoContainer(
                        AnimatedContentScope.SlideDirection.Right,
                        animationSpec = tween(durationMillis = 700)
                    )
                    else -> null
                }
            }
        ) {
            AuthLanding(
                state = state,
                goToSignUp = { authNavHostController.navigate(AuthNavLocation.SignUp.route) },
                goToSignIn = { authNavHostController.navigate(AuthNavLocation.SignIn.route) }
            )
        }
    }

    override suspend fun updateWavyState(
        state: WavyScaffoldState,
        maxHeight: Dp,
        maxWidth: Dp,
    ) {
        state.animate(
            backDropHeight = maxHeight - 248.dp,
            frequency = 0.3f,
            waveHeight = 43.dp,
            duration = 15_000
        )
    }
}

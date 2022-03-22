package dev.dprice.productivity.todo.auth.feature.navigation

import android.os.Bundle
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.runtime.*
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.navigation
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import dev.dprice.productivity.todo.auth.feature.model.AuthNavLocation
import dev.dprice.productivity.todo.auth.feature.ui.landing.AuthLanding
import dev.dprice.productivity.todo.auth.feature.ui.signin.SignIn
import dev.dprice.productivity.todo.auth.feature.ui.signup.SignUp
import dev.dprice.productivity.todo.ui.components.WavyBackdropScaffold
import dev.dprice.productivity.todo.ui.components.WavyScaffoldState

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AuthNavGraph(
    navController: NavHostController
) {
    val state = remember { WavyScaffoldState() }

    val transition = rememberInfiniteTransition()
    val offset = transition.animateFloat(
        initialValue = 0.0f,
        targetValue = 1.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = state.waveDuration.value,
                easing = LinearEasing
            )
        )
    )

    val animatedPosition = animateDpAsState(
        targetValue = state.targetPosition.value,
        animationSpec = tween(durationMillis = state.duration)
    )

    val animatedFrequency = animateFloatAsState(
        targetValue = state.targetFrequency.value,
        animationSpec = tween(durationMillis = state.duration)
    )

    val animatedHeight = animateDpAsState(
        targetValue = state.targetHeight.value,
        animationSpec = tween(durationMillis = state.duration)
    )

    BoxWithConstraints {
        // This backdrop exists here to provide the correct background colour
        // whilst fading between nav locations.
        WavyBackdropScaffold(
            backRevealHeight = animatedPosition.value,
            waveHeight = animatedHeight.value,
            waveFrequency = animatedFrequency.value,
            waveOffsetPercent = offset.value,
            backContent = { },
            frontContent = { }
        )

        val authNavController = rememberAnimatedNavController()

         DisposableEffect(key1 = authNavController) {
             val listener = NavController.OnDestinationChangedListener { controller, destination, arguments ->
                 when(destination.route) {
                     AuthNavLocation.Landing.route -> {
                         val position = maxHeight - 264.dp
                         state.targetPosition.value = position
                         state.targetFrequency.value = 0.3f
                         state.targetHeight.value = 48.dp
                     }
                     AuthNavLocation.SignUp.route -> {
                         state.targetPosition.value = 128.dp
                         state.targetFrequency.value = 0.3f
                         state.targetHeight.value = 128.dp
                     }
                     else -> { /* do not update*/ }
                 }
             }

            authNavController.addOnDestinationChangedListener(listener)

             onDispose {
                 authNavController.removeOnDestinationChangedListener(listener)
             }
        }

        // todo move route to class
        AnimatedNavHost(
            authNavController,
            startDestination = AuthNavLocation.Landing.route
        ) {

            composable(route = AuthNavLocation.Landing.route) {
                AuthLanding(
                    state,
                    offset.value,
                    animatedPosition.value,
                    animatedFrequency.value,
                    animatedHeight.value,
                    goToSignUp = { authNavController.navigate(AuthNavLocation.SignUp.route) },
                    goToSignIn = { authNavController.navigate(AuthNavLocation.SignIn.route) }
                )
            }

            composable(route = AuthNavLocation.SignUp.route) {
                SignUp(
                    state,
                    offset.value,
                    animatedPosition.value,
                    animatedFrequency.value,
                    animatedHeight.value,
                )
            }

            composable(route = AuthNavLocation.VerifySignUp.route) {

            }

            composable(route = AuthNavLocation.SignIn.route) {
                SignIn()
            }
        }
    }
}
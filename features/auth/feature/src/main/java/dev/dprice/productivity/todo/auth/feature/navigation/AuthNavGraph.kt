package dev.dprice.productivity.todo.auth.feature.navigation

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import dev.dprice.productivity.todo.auth.feature.model.AuthNavLocation
import dev.dprice.productivity.todo.auth.feature.ui.landing.AuthLanding
import dev.dprice.productivity.todo.auth.feature.ui.signin.SignIn
import dev.dprice.productivity.todo.auth.feature.ui.signup.SignUp
import dev.dprice.productivity.todo.auth.feature.ui.verifycode.VerifyCode
import dev.dprice.productivity.todo.ui.components.WavyBackdropScaffold
import dev.dprice.productivity.todo.ui.components.WavyScaffoldConfig
import dev.dprice.productivity.todo.ui.components.WavyScaffoldState
import kotlin.math.floor

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AuthNavGraph(
    navController: NavHostController
) {
    val state = remember { WavyScaffoldState() }

    val target = remember { mutableStateOf(0.0f) }
    val offset = animateFloatAsState(
        targetValue = target.value,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = state.waveDuration.value,
                easing = LinearEasing
            )
        )
    )
    val correctedOffset = offset.value - floor(offset.value)

    LaunchedEffect(key1 = state.waveDuration.value) {
        target.value = offset.value + 1f
    }

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
            waveOffsetPercent = correctedOffset,
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
                         state.waveDuration.value = 15_000
                     }
                     AuthNavLocation.SignUp.route -> {
                         state.targetPosition.value = 128.dp
                         state.targetFrequency.value = 0.3f
                         state.targetHeight.value = 128.dp
                         state.waveDuration.value = 15_000
                     }
                     else -> { /* do not update*/ }
                 }
             }

            authNavController.addOnDestinationChangedListener(listener)

             onDispose {
                 authNavController.removeOnDestinationChangedListener(listener)
             }
        }

        val config = WavyScaffoldConfig(
            animatedPosition.value,
            animatedHeight.value,
            animatedFrequency.value,
            correctedOffset,
        )

        // todo move route to class
        AnimatedNavHost(
            authNavController,
            startDestination = AuthNavLocation.Landing.route
        ) {

            composable(route = AuthNavLocation.Landing.route) {
                AuthLanding(
                    state,
                    correctedOffset,
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
                    correctedOffset,
                    animatedPosition.value,
                    animatedFrequency.value,
                    animatedHeight.value,
                    goToVerifyCode = { authNavController.navigate(AuthNavLocation.VerifySignUp.route) },
                    goToSignIn = { authNavController.navigate(AuthNavLocation.SignIn.route) }
                )
            }

            composable(route = AuthNavLocation.VerifySignUp.route) {
                VerifyCode(
                    config = config,

                )
            }

            composable(route = AuthNavLocation.SignIn.route) {
                SignIn()
            }
        }
    }
}
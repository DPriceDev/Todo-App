package dev.dprice.productivity.todo.auth.feature.ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import dev.dprice.productivity.todo.auth.landing.navigation.LandingAuthNavigationComponent
import dev.dprice.productivity.todo.auth.signin.navigation.SignInAuthNavigationComponent
import dev.dprice.productivity.todo.auth.signup.navigation.SignUpAuthNavigationComponent
import dev.dprice.productivity.todo.auth.verify.navigation.VerifyAuthNavigationComponent
import dev.dprice.productivity.todo.ui.components.WavyBackdropScaffold
import dev.dprice.productivity.todo.ui.components.WavyScaffoldState
import dev.dprice.productivity.todo.ui.navigation.AuthNavLocation
import dev.dprice.productivity.todo.ui.navigation.AuthNavigationComponent
import kotlinx.coroutines.launch

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AuthScreen(
    navController: NavHostController
) {
    val screens : List<AuthNavigationComponent> = listOf(
        LandingAuthNavigationComponent(),
        SignUpAuthNavigationComponent(),
        SignInAuthNavigationComponent(),
        VerifyAuthNavigationComponent()
    )

    val authNavController = rememberAnimatedNavController()
    val state = remember { WavyScaffoldState() }
    val scope = rememberCoroutineScope()

    BoxWithConstraints {
        DisposableEffect(key1 = true) {
            val listener = NavController.OnDestinationChangedListener { controller, destination, arguments ->
                scope.launch {
                    screens.find { it.navLocation.route == destination.route }?.updateWavyState(
                        state,
                        maxHeight,
                        maxWidth
                    )
                }
            }

            authNavController.addOnDestinationChangedListener(listener)
            onDispose { authNavController.removeOnDestinationChangedListener(listener) }
        }

        WavyBackdropScaffold(
            state = state,
            backContent = { },
            frontContent = { }
        )

        AnimatedNavHost(
            authNavController,
            startDestination = AuthNavLocation.Landing.route,
            modifier = Modifier.fillMaxSize()
        ) {
            screens.forEach {
                it.navigationContent(
                    this,
                    authNavController,
                    navController,
                    state,
                    this@BoxWithConstraints.maxHeight,
                    this@BoxWithConstraints.maxWidth,
                )
            }
        }
    }
}
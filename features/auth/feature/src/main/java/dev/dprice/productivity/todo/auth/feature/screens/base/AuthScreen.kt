package dev.dprice.productivity.todo.auth.feature.screens.base

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import dev.dprice.productivity.todo.auth.feature.model.AuthNavLocation
import dev.dprice.productivity.todo.auth.feature.screens.forgotpassword.navigation.forgotPasswordComposable
import dev.dprice.productivity.todo.auth.feature.screens.landing.navigation.landingComposable
import dev.dprice.productivity.todo.auth.feature.screens.resetpassword.navigation.resetPasswordComposable
import dev.dprice.productivity.todo.auth.feature.screens.signin.navigation.signInComposable
import dev.dprice.productivity.todo.auth.feature.screens.signup.navigation.signUpComposable
import dev.dprice.productivity.todo.auth.feature.screens.verify.navigation.verifyCodeComposable
import dev.dprice.productivity.todo.ui.components.WavyBackdropScaffold
import dev.dprice.productivity.todo.ui.components.WavyScaffoldState

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AuthScreen(navController: NavHostController) {
    val authNavController = rememberAnimatedNavController()
    val state = remember { WavyScaffoldState() }

    BoxWithConstraints {
        WavyBackdropScaffold(state = state)

        AnimatedNavHost(
            authNavController,
            startDestination = AuthNavLocation.Landing.route,
            modifier = Modifier.fillMaxSize()
        ) {
            landingComposable(authNavController, state, maxHeight)

            signInComposable(authNavController, navController, state)

            signUpComposable(authNavController, navController, state)

            verifyCodeComposable(authNavController, navController, state)

            forgotPasswordComposable(authNavController, state)

            resetPasswordComposable(authNavController, state)
        }
    }
}

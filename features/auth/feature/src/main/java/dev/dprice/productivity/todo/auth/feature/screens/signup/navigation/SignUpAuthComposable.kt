package dev.dprice.productivity.todo.auth.feature.screens.signup.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.composable
import dev.dprice.productivity.todo.auth.feature.model.AuthNavLocation
import dev.dprice.productivity.todo.auth.feature.screens.signup.ui.SignUp
import dev.dprice.productivity.todo.auth.feature.screens.signup.ui.SignUpViewModelImpl
import dev.dprice.productivity.todo.ui.components.scaffold.WavyScaffoldState

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.signUpComposable(
    authNavHostController: NavHostController,
    appNavHostController: NavHostController,
    state: WavyScaffoldState
) {
    composable(
        route = AuthNavLocation.SignUp.route,
        enterTransition = {
            when (initialState.destination.route) {
                AuthNavLocation.VerifySignUp.route -> slideIntoContainer(
                    AnimatedContentScope.SlideDirection.Right,
                    animationSpec = tween(durationMillis = 700)
                )
                else -> null
            }
        },
        exitTransition = {
            when (targetState.destination.route) {
                AuthNavLocation.VerifySignUp.route -> slideOutOfContainer(
                    AnimatedContentScope.SlideDirection.Left,
                    animationSpec = tween(durationMillis = 700)
                )
                else -> null
            }
        }
    ) {
        LaunchedEffect(key1 = true) {
            state.animate(
                backDropHeight = 128.dp,
                frequency = 0.3f,
                waveHeight = 128.dp,
                duration = 15_000
            )
        }

        val viewModel = hiltViewModel<SignUpViewModelImpl>()
        SignUp(
            viewModel.viewState,
            wavyScaffoldState = state,
            updateEntry = viewModel::updateEntry,
            goToSignIn = {
                authNavHostController.navigate(AuthNavLocation.SignIn.route) {
                    popUpTo(AuthNavLocation.SignUp.route) { inclusive = true }
                }
            },
            submitForm = {
                viewModel.submitForm(
                    goToVerifyCode = { code ->
                        authNavHostController.navigate(
                            AuthNavLocation.VerifySignUp.withParameters(code)
                        )
                    },
                    goToMainApp = {
                        appNavHostController.navigate("MainApp") {
                            popUpTo("MainApp") { inclusive = true }
                        }
                    }
                )
            }
        )
    }
}

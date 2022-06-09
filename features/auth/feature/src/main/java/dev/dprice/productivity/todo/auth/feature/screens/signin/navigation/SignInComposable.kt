package dev.dprice.productivity.todo.auth.feature.screens.signin.navigation

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
import dev.dprice.productivity.todo.auth.feature.screens.signin.ui.SignIn
import dev.dprice.productivity.todo.auth.feature.screens.signin.ui.SignInViewModelImpl
import dev.dprice.productivity.todo.ui.components.WavyScaffoldState

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.signInComposable(
    authNavHostController: NavHostController,
    appNavHostController: NavHostController,
    state: WavyScaffoldState
) {
    composable(
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
        LaunchedEffect(key1 = true) {
            state.animate(
                backDropHeight = 128.dp,
                frequency = 0.3f,
                waveHeight = 128.dp,
                duration = 15_000
            )
        }

        val viewModel = hiltViewModel<SignInViewModelImpl>()
        SignIn(
            state = viewModel.viewState,
            wavyScaffoldState = state,
            goToSignUp = {
                authNavHostController.navigate(AuthNavLocation.SignUp.route) {
                    popUpTo(AuthNavLocation.SignIn.route) { inclusive = true }
                }
            },
            goToForgotPassword = {
                authNavHostController.navigate(AuthNavLocation.ForgotPassword.route)
            },
            onFormUpdated = viewModel::updateEntry,
            onSubmitForm = {
                viewModel.submitForm(
                    goToMainApp = {
                        appNavHostController.navigate("MainApp") {
                            popUpTo("MainApp") { inclusive = true }
                        }
                    },
                    goToVerifyCode = { username ->
                        authNavHostController.navigate(
                            AuthNavLocation.VerifySignUp.withParameters(username)
                        )
                    }
                )
            }
        )
    }
}

package dev.dprice.productivity.todo.auth.feature.screens.forgotpassword.navigation

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
import dev.dprice.productivity.todo.auth.feature.screens.forgotpassword.ui.ForgotPassword
import dev.dprice.productivity.todo.auth.feature.screens.forgotpassword.ui.ForgotPasswordViewModelImpl
import dev.dprice.productivity.todo.ui.components.WavyScaffoldState

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.forgotPasswordComposable(
    authNavHostController: NavHostController,
    state: WavyScaffoldState
) {
    composable(
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
        LaunchedEffect(key1 = true) {
            state.animate(
                backDropHeight = 128.dp,
                frequency = 0.3f,
                waveHeight = 128.dp,
                duration = 15_000
            )
        }

        val viewModel = hiltViewModel<ForgotPasswordViewModelImpl>()
        ForgotPassword(
            state = viewModel.viewState,
            wavyScaffoldState = state,
            onEvent = viewModel::updateEntry,
            goToResetPassword = {
                authNavHostController.navigate(AuthNavLocation.ResetPassword.route)
            },
            onSubmit = {
                viewModel.submit {
                    authNavHostController.navigate(AuthNavLocation.ResetPassword.route)
                }
            }
        )
    }
}

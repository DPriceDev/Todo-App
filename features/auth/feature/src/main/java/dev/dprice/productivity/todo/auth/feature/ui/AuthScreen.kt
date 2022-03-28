package dev.dprice.productivity.todo.auth.feature.ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import dev.dprice.productivity.todo.ui.components.WavyBackdropScaffold
import dev.dprice.productivity.todo.ui.components.WavyScaffoldState
import dev.dprice.productivity.todo.ui.navigation.AuthNavLocation
import kotlinx.coroutines.launch

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AuthScreen(
    navController: NavHostController,
    viewModel: AuthViewModel = hiltViewModel<AuthViewModelImpl>()
) {
    val authNavController = rememberAnimatedNavController()
    val state = remember { WavyScaffoldState() }
    val scope = rememberCoroutineScope()

    BoxWithConstraints {
        DisposableEffect(key1 = true) {
            val listener = NavController.OnDestinationChangedListener { controller, destination, arguments ->
                scope.launch {
                    viewModel.navigationComponents
                        .find { it.navLocation.route == destination.route }
                        ?.updateWavyState(
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
            viewModel.navigationComponents.forEach {
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
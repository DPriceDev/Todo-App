package dev.dprice.productivity.todo.auth.feature.navigation

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import dev.dprice.productivity.todo.auth.feature.model.AuthNavLocation
import dev.dprice.productivity.todo.auth.feature.ui.landing.LandingAuthNavScreen
import dev.dprice.productivity.todo.auth.feature.ui.signup.SignUpAuthNavScreen
import dev.dprice.productivity.todo.ui.components.WavyBackdropScaffold
import dev.dprice.productivity.todo.ui.components.WavyScaffoldState

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AuthNavGraph(
    navController: NavHostController
) {
    val screens : List<AuthNavScreen> = listOf(
        LandingAuthNavScreen(),
        SignUpAuthNavScreen()
    )

    val authNavController = rememberAnimatedNavController()
    val backStackState = authNavController.currentBackStackEntryAsState()
    val state = remember { WavyScaffoldState() }
    val config = state.animatedConfig()

    BoxWithConstraints {
        WavyBackdropScaffold(
            config = config,
            backContent = {
                AnimatedContent(
                    targetState = backStackState.value?.destination?.route,
                    transitionSpec = {
                        fadeIn(
                            animationSpec = tween(700)
                        ) with fadeOut(
                            animationSpec = tween(700)
                        )
                    },
                    modifier = Modifier.fillMaxSize()
                ) { state ->
                    screens.find { it.navLocation.route == state }?.TopContent(
                        this@BoxWithConstraints.maxHeight,
                        this@BoxWithConstraints.maxWidth,
                        this
                    )
                }
            },
            frontContent = {
                AnimatedNavHost(
                    authNavController,
                    startDestination = AuthNavLocation.Landing.route,
                    modifier = Modifier.fillMaxSize()
                ) {
                    screens.forEach {
                        it.bottomNavigationContent(
                            this,
                            authNavController,
                            navController,
                            state,
                            config,
                            this@BoxWithConstraints.maxHeight,
                            this@BoxWithConstraints.maxWidth,
                        )
                    }
                }
            }
        )
    }
}
package dev.dprice.productivity.todo.auth.feature.ui

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
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
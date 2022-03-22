package dev.dprice.productivity.todo.auth.feature.navigation

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import dev.dprice.productivity.todo.auth.feature.model.AuthNavLocation
import dev.dprice.productivity.todo.auth.feature.ui.landing.AuthLanding
import dev.dprice.productivity.todo.auth.feature.ui.signin.SignIn
import dev.dprice.productivity.todo.auth.feature.ui.signup.SignUp
import dev.dprice.productivity.todo.ui.components.WavePosition

data class WavyScaffoldState(
    val height: Dp = 0.dp,
    val frequency: Float = 1f,
    val speed: Long = 1L,
    val position: WavePosition = WavePosition.Top(0.dp)
)

fun NavGraphBuilder.authNavGraph(
    navController: NavHostController
) {

    // todo move route to class
    navigation(route = "auth", startDestination = AuthNavLocation.Landing.route) {

        composable(route = AuthNavLocation.Landing.route) {
            AuthLanding(
                goToSignUp = { navController.navigate(AuthNavLocation.SignUp.route) },
                goToSignIn = { navController.navigate(AuthNavLocation.SignIn.route) }
            )
        }

        composable(route = AuthNavLocation.SignUp.route) {
            SignUp()
        }

        composable(route = AuthNavLocation.VerifySignUp.route) {

        }

        composable(route = AuthNavLocation.SignIn.route) {
            SignIn()
        }
    }
}
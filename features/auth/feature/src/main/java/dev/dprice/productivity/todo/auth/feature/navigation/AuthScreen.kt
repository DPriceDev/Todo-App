package dev.dprice.productivity.todo.auth.feature.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import dev.dprice.productivity.todo.auth.feature.model.AuthNavLocation
import dev.dprice.productivity.todo.auth.feature.ui.signin.SignIn
import dev.dprice.productivity.todo.auth.feature.ui.signup.SignUp

fun NavGraphBuilder.authNavGraph(
    navController: NavHostController
) {
    // todo move route to class
    navigation(route = "auth", startDestination = AuthNavLocation.SignUp.route) {

        composable(route = AuthNavLocation.Landing.route) {

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
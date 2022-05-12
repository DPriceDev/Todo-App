package dev.dprice.productivity.todo.auth.feature.model

import androidx.navigation.NamedNavArgument
import androidx.navigation.navArgument
import dev.dprice.productivity.todo.platform.model.NavDestination

sealed class AuthNavLocation(
    override val route: String,
    override val navArguments: List<NamedNavArgument> = WAVE_OFFSET_ARGUMENT
) : NavDestination {

    object Landing : AuthNavLocation("auth-landing?$WAVE_OFFSET_QUERY")

    object SignUp : AuthNavLocation("auth-sign-up?$WAVE_OFFSET_QUERY")

    object VerifySignUp : AuthNavLocation(
        "auth-sign-up-verify/{username}?$WAVE_OFFSET_QUERY"
    ) {
        fun location(username: String) = "auth-sign-up-verify/$username"

        const val USERNAME = "username"
    }

    object SignIn : AuthNavLocation("auth-sign-in?$WAVE_OFFSET_QUERY")

    object MultiFactor : AuthNavLocation("auth-mfa?$WAVE_OFFSET_QUERY")

    object ForgotPassword : AuthNavLocation("auth-forgot?$WAVE_OFFSET_QUERY")

    object ResetPassword : AuthNavLocation("auth-reset?$WAVE_OFFSET_QUERY")

    companion object {
        const val WAVE_OFFSET = "waveOffset"

        private const val WAVE_OFFSET_QUERY = "$WAVE_OFFSET={$WAVE_OFFSET}"
        private val WAVE_OFFSET_ARGUMENT = listOf(
            navArgument(WAVE_OFFSET) { nullable = true }
        )
    }
}

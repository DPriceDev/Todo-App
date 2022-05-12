package dev.dprice.productivity.todo.ui.navigation

sealed class AuthNavLocation(
    val route: String
) {
    object Landing : AuthNavLocation("auth-landing")
    object SignUp : AuthNavLocation("auth-sign-up")
    object VerifySignUp : AuthNavLocation("auth-sign-up-verify/{username}") {
        const val username = "username"
        fun location(username: String) = "auth-sign-up-verify/$username"
    }
    object SignIn : AuthNavLocation("auth-sign-in")
    object MultiFactor : AuthNavLocation("auth-mfa")
    object ForgotPassword : AuthNavLocation("auth-forgot")
    object ResetPassword : AuthNavLocation("auth-reset")
}

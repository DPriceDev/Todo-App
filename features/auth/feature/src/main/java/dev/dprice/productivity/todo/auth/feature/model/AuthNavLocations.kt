package dev.dprice.productivity.todo.auth.feature.model

sealed class AuthNavLocation(
    val route: String
) {
    object Landing : AuthNavLocation("auth-landing")
    object SignUp : AuthNavLocation("auth-sign-up")
    object VerifySignUp : AuthNavLocation("auth-sign-up-verify")
    object SignIn : AuthNavLocation("auth-sign-in")
    object MultiFactor : AuthNavLocation("auth-mfa")
    object ForgotPassword : AuthNavLocation("auth-forgot")
}

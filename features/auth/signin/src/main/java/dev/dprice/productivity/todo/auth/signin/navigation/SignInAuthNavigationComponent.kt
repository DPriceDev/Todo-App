package dev.dprice.productivity.todo.auth.signin.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.composable
import dev.dprice.productivity.todo.auth.signin.ui.SignInBottomContent
import dev.dprice.productivity.todo.ui.components.WavyScaffoldState
import dev.dprice.productivity.todo.ui.navigation.AuthNavLocation
import dev.dprice.productivity.todo.ui.navigation.AuthNavigationComponent

@OptIn(ExperimentalAnimationApi::class)
class SignInAuthNavigationComponent : AuthNavigationComponent {

    override val navLocation: AuthNavLocation = AuthNavLocation.SignIn

    override fun navigationContent(
        builder: NavGraphBuilder,
        authNavHostController: NavHostController,
        appNavHostController: NavHostController,
        state: WavyScaffoldState,
        maxHeight: Dp,
        maxWidth: Dp
    ) {
        builder.composable(route = AuthNavLocation.SignIn.route) {
            SignInBottomContent()
        }
    }

    override suspend fun updateWavyState(state: WavyScaffoldState, maxHeight: Dp, maxWidth: Dp) {
        state.animate(
            backDropHeight = 128.dp,
            frequency = 0.3f,
            waveHeight = 128.dp,
            duration = 15_000
        )
    }
}
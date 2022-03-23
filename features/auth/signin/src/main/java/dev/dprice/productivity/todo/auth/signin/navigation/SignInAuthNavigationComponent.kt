package dev.dprice.productivity.todo.auth.signin.navigation

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.composable
import dev.dprice.productivity.todo.auth.signin.ui.SignInBottomContent
import dev.dprice.productivity.todo.auth.signin.ui.SignInTopContent
import dev.dprice.productivity.todo.ui.components.WavyScaffoldState
import dev.dprice.productivity.todo.ui.navigation.AuthNavLocation
import dev.dprice.productivity.todo.ui.navigation.AuthNavigationComponent

@OptIn(ExperimentalAnimationApi::class)
class SignInAuthNavigationComponent : AuthNavigationComponent {

    override val navLocation: AuthNavLocation = AuthNavLocation.SignIn

    @Composable
    override fun TopContent(maxHeight: Dp, maxWidth: Dp, scope: AnimatedVisibilityScope) {
        SignInTopContent()
    }

    override fun bottomNavigationContent(builder: NavGraphBuilder,
                                         authNavHostController: NavHostController,
                                         appNavHostController: NavHostController,
                                         state: WavyScaffoldState,
                                         config: WavyScaffoldState.Config,
                                         maxHeight: Dp,
                                         maxWidth: Dp) {
        builder.composable(route = AuthNavLocation.SignIn.route) {
            updateWavyState(state, maxHeight, maxWidth)
            SignInBottomContent()
        }
    }

    override fun updateWavyState(state: WavyScaffoldState, maxHeight: Dp, maxWidth: Dp) {
        state.targetPosition.value = 128.dp
        state.targetFrequency.value = 0.3f
        state.targetHeight.value = 128.dp
        state.waveDuration.value = 15_000
    }
}
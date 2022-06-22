package dev.dprice.productivity.todo.auth.feature.components.signout

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import dev.dprice.productivity.todo.auth.feature.components.signout.ui.SignOutButton
import dev.dprice.productivity.todo.auth.feature.components.signout.ui.SignOutViewModelImpl
import dev.dprice.productivity.todo.platform.model.Component
import javax.inject.Inject

class SignOutComponent @Inject constructor() : Component {
    override val name: String = "sign-out-settings-component"

    @Composable
    override fun Composable(navController: NavHostController) {
        val viewModel = hiltViewModel<SignOutViewModelImpl>()

        SignOutButton(
            onSignOut = viewModel::logOutUser,
            buttonState = viewModel.buttonState
        )
    }
}

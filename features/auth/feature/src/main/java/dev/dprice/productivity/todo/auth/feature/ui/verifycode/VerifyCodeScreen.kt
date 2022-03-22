package dev.dprice.productivity.todo.auth.feature.ui.verifycode

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dev.dprice.productivity.todo.auth.feature.model.signin.SignInAction
import dev.dprice.productivity.todo.auth.feature.model.signin.SignInForm
import dev.dprice.productivity.todo.auth.feature.ui.components.TitleBlock
import dev.dprice.productivity.todo.auth.feature.ui.signin.SignInViewModel
import dev.dprice.productivity.todo.auth.feature.ui.signin.SignInViewModelImpl
import dev.dprice.productivity.todo.ui.components.WavyBackdropScaffold
import dev.dprice.productivity.todo.ui.components.WavyScaffoldConfig

@Composable
fun VerifyCode(
    config: WavyScaffoldConfig,
    viewModel: SignInViewModel = hiltViewModel<SignInViewModelImpl>()
) {
    WavyBackdropScaffold(
        config = config,
        backContent = {
            Box(modifier = Modifier.padding(top = 16.dp)) {
                TitleBlock(colour = MaterialTheme.colors.background)
            }
        },
        frontContent = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TitleBlock(colour = MaterialTheme.colors.primary)

                Text(text = "Verify Sign Up")
            }
        }
    )
}

/**
 * Preview
 */

@Preview
@Composable
private fun PreviewVerifyCode() {

}
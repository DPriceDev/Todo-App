package dev.dprice.productivity.todo.auth.feature.ui.verifycode

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
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

@Composable
fun VerifyCode(
    viewModel: SignInViewModel = hiltViewModel<SignInViewModelImpl>()
) {
    WavyBackdropScaffold(
        backRevealHeight = 16.dp,
        backContent = {
            Box(modifier = Modifier.padding(top = 16.dp)) {
                TitleBlock(colour = MaterialTheme.colors.background)
            }
        }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TitleBlock(colour = MaterialTheme.colors.primary)

            Spacer(modifier = Modifier.height(32.dp))

            Form(
                signInForm = viewModel.viewState.form,
                canSubmit = viewModel.viewState.canSubmit,
                onEntryChanged = viewModel::onFormChanged,
                onSignUpClicked = viewModel::goToSignUp,
                onSubmitForm = viewModel::submitForm
            )
        }
    }
}

@Composable
private fun Form(
    signInForm: SignInForm,
    canSubmit: Boolean,
    onEntryChanged: (SignInAction) -> Unit,
    onSubmitForm: () -> Unit,
    onSignUpClicked: () -> Unit
) {

}

/**
 * Preview
 */

@Preview
@Composable
private fun PreviewVerifyCode() {

}
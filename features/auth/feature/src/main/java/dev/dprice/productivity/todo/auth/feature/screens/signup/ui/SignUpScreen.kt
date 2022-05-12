package dev.dprice.productivity.todo.auth.feature.screens.signup.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dev.dprice.productivity.todo.auth.feature.screens.signup.model.SignUpAction
import dev.dprice.productivity.todo.auth.feature.screens.signup.model.SignUpAction.Type
import dev.dprice.productivity.todo.auth.feature.screens.signup.model.SignUpForm
import dev.dprice.productivity.todo.auth.feature.screens.signup.model.SignUpState
import dev.dprice.productivity.todo.auth.feature.ui.components.TitleBlock
import dev.dprice.productivity.todo.ui.components.*
import dev.dprice.productivity.todo.ui.theme.TodoAppTheme

@Composable
fun SignUp(
    state: WavyScaffoldState,
    goToVerifyCode: (String) -> Unit,
    goToSignIn: () -> Unit,
    goToMainApp: () -> Unit,
    viewModel: SignUpViewModel = hiltViewModel<SignUpViewModelImpl>()
) {
    WavyBackdropScaffold(
        state = state,
        backContent = {
            TitleBlock(colour = MaterialTheme.colors.background)
        },
        frontContent = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TitleBlock(colour = MaterialTheme.colors.primary)
                Text(
                    text = "Sign up for a free account today!",
                    modifier = Modifier.padding(top = 16.dp),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.body1,
                )
                Form(
                    signUpForm = viewModel.viewState.form,
                    buttonState = viewModel.viewState.buttonState,
                    onEntryChanged = viewModel::onFormChanged,
                    onSignInClicked = goToSignIn,
                    onSubmitForm = {
                        viewModel.submitForm(
                            goToVerifyCode,
                            goToMainApp
                        )
                    }
                )
            }
        }
    )
}

@Composable
private fun Form(
    signUpForm: SignUpForm,
    buttonState: ButtonState,
    onEntryChanged: (SignUpAction) -> Unit,
    onSubmitForm: () -> Unit,
    onSignInClicked: () -> Unit
) {
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .padding(horizontal = 32.dp, vertical = 24.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        RoundedEntryCard(
            entry = signUpForm.email,
            modifier = Modifier.onFocusChanged {
                onEntryChanged(
                    SignUpAction(
                        signUpForm.email.value,
                        it.hasFocus,
                        Type.UPDATE_EMAIL
                    )
                )
            },
            onImeAction = { focusManager.moveFocus(FocusDirection.Down) },
            onTextChanged = {
                onEntryChanged(
                    SignUpAction(
                        it,
                        signUpForm.email.hasFocus,
                        Type.UPDATE_EMAIL
                    )
                )
            }
        )
        RoundedEntryCard(
            entry = signUpForm.username,
            modifier = Modifier.onFocusChanged {
                onEntryChanged(
                    SignUpAction(
                        signUpForm.username.value,
                        it.hasFocus,
                        Type.UPDATE_USERNAME
                    )
                )
            },
            onImeAction = { focusManager.moveFocus(FocusDirection.Down) },
            onTextChanged = {
                onEntryChanged(
                    SignUpAction(
                        it,
                        signUpForm.username.hasFocus,
                        Type.UPDATE_USERNAME
                    )
                )
            }
        )
        RoundedEntryCard(
            entry = signUpForm.password,
            modifier = Modifier.onFocusChanged {
                onEntryChanged(
                    SignUpAction(
                        signUpForm.password.value,
                        it.hasFocus,
                        Type.UPDATE_PASSWORD
                    )
                )
            },
            onImeAction = { focusManager.clearFocus() },
            onTextChanged = {
                onEntryChanged(
                    SignUpAction(
                        it,
                        signUpForm.password.hasFocus,
                        Type.UPDATE_PASSWORD
                    )
                )
            }
        )

        RoundedButton(
            text = "Create Account",
            onClick = onSubmitForm,
            modifier = Modifier.padding(top = 8.dp, bottom = 24.dp),
            contentPadding = PaddingValues(horizontal = 80.dp, vertical = 16.dp),
            buttonState = buttonState,
        )

        TextWithClickableSuffix(
            text = "Already have an account? ",
            suffixText = "Sign in",
            onClick = onSignInClicked
        )
    }
}

/**
 * Preview
 */

private val previewViewModel = object : SignUpViewModel {
    override val viewState: SignUpState =
        SignUpState()

    override fun onFormChanged(action: SignUpAction) {
        /* Stub */
    }

    override fun submitForm(goToVerifyCode: (String) -> Unit, goToMainApp: () -> Unit) {
        /* Stub */
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewSignUp() {
    TodoAppTheme {
        val state = WavyScaffoldState(
            initialBackDropHeight = 128.dp,
            initialWaveHeight = 128.dp,
            initialFrequency = 0.3f
        )

        SignUp(
            state,
            { },
            { },
            { },
            previewViewModel
        )
    }
}
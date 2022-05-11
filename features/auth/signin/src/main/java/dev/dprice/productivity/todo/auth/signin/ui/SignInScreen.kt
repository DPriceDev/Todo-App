package dev.dprice.productivity.todo.auth.signin.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dev.dprice.productivity.todo.auth.signin.model.SignInAction
import dev.dprice.productivity.todo.auth.signin.model.SignInAction.Type
import dev.dprice.productivity.todo.auth.signin.model.SignInForm
import dev.dprice.productivity.todo.auth.signin.viewmodel.SignInViewModel
import dev.dprice.productivity.todo.auth.signin.viewmodel.SignInViewModelImpl
import dev.dprice.productivity.todo.ui.components.*

@Composable
fun SignIn(
    state: WavyScaffoldState,
    goToMainApp: () -> Unit,
    goToVerifyCode: (String) -> Unit,
    goToSignUp: () -> Unit,
    goToForgotPassword: () -> Unit,
    viewModel: SignInViewModel = hiltViewModel<SignInViewModelImpl>()
) {
    WavyBackdropScaffold(
        state = state,
        backContent = {
            TitleBlock(
                colour = MaterialTheme.colors.background,
                title = "Sign in"
            )
        },
        frontContent = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TitleBlock(
                    colour = MaterialTheme.colors.primary,
                    title = "Sign in"
                )

                // todo: Show password in entry field
                Form(
                    signInForm = viewModel.viewState.form,
                    buttonEnablement = viewModel.viewState.buttonEnablement,
                    onEntryChanged = viewModel::onFormChanged,
                    onSignUpClicked = goToSignUp,
                    onForgotPasswordClicked = goToForgotPassword,
                    onSubmitForm = {
                        viewModel.submitForm(
                            goToMainApp = goToMainApp,
                            goToVerifyCode = goToVerifyCode
                        )
                    }
                )

                // todo forgot password
            }
        }
    )
}

@Composable
private fun Form(
    signInForm: SignInForm,
    buttonEnablement: ButtonEnablement,
    onEntryChanged: (SignInAction) -> Unit,
    onSubmitForm: () -> Unit,
    onSignUpClicked: () -> Unit,
    onForgotPasswordClicked: () -> Unit
) {
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .padding(horizontal = 32.dp, vertical = 24.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        RoundedEntryCard(
            entry = signInForm.username,
            modifier = Modifier.onFocusChanged {
                onEntryChanged(
                    SignInAction(signInForm.username.value, it.hasFocus, Type.UPDATE_USERNAME)
                )
            },
            onImeAction = { focusManager.moveFocus(FocusDirection.Down) },
            onTextChanged = {
                onEntryChanged(
                    SignInAction(it, signInForm.username.hasFocus, Type.UPDATE_USERNAME)
                )
            }
        )
        RoundedEntryCard(
            entry = signInForm.password,
            modifier = Modifier.onFocusChanged {
                onEntryChanged(
                    SignInAction(signInForm.password.value, it.hasFocus, Type.UPDATE_PASSWORD)
                )
            },
            onImeAction = { focusManager.clearFocus() },
            onTextChanged = {
                onEntryChanged(
                    SignInAction(it, signInForm.password.hasFocus, Type.UPDATE_PASSWORD)
                )
            }
        )

        RoundedButton(
            text = "Sign in",
            onClick = onSubmitForm,
            modifier = Modifier.padding(top = 8.dp, bottom = 24.dp),
            contentPadding = PaddingValues(horizontal = 80.dp, vertical = 16.dp),
            buttonEnablement = buttonEnablement,
        )

        TextWithClickableSuffix(
            text = "",
            modifier = Modifier.padding(bottom = 24.dp),
            suffixText = "Forgot password?",
            onClick = onForgotPasswordClicked
        )

        TextWithClickableSuffix(
            text = "Don't have an account yet? ",
            suffixText = "Create account",
            onClick = onSignUpClicked
        )
    }
}
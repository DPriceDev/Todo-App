package dev.dprice.productivity.todo.auth.feature.screens.signin.ui

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import dev.dprice.productivity.todo.auth.feature.model.ErrorState
import dev.dprice.productivity.todo.auth.feature.screens.signin.model.SignInEvent
import dev.dprice.productivity.todo.auth.feature.screens.signin.model.SignInForm
import dev.dprice.productivity.todo.auth.feature.screens.signin.model.SignInState
import dev.dprice.productivity.todo.auth.feature.ui.AuthWavyScaffold
import dev.dprice.productivity.todo.features.auth.feature.R
import dev.dprice.productivity.todo.ui.components.*
import dev.dprice.productivity.todo.ui.theme.TodoAppTheme

@Composable
fun SignIn(
    state: SignInState,
    wavyScaffoldState: WavyScaffoldState,
    goToSignUp: () -> Unit = { },
    goToForgotPassword: () -> Unit = { },
    onSubmitForm: () -> Unit = { },
    onFormUpdated: (SignInEvent) -> Unit = { }
) {
    AuthWavyScaffold(
        state = wavyScaffoldState,
        title = stringResource(id = R.string.sign_in),
        description = stringResource(id = R.string.sign_in_description),
        errorMessage = (state.errorState as? ErrorState.Message)?.let { stringResource(id = it.messageId) }
    ) {
        // todo: Show password in entry field
        Form(
            signInForm = state.form,
            updateEntry = onFormUpdated
        )

        RoundedButton(
            text = stringResource(id = R.string.sign_in),
            onClick = onSubmitForm,
            modifier = Modifier.padding(top = 8.dp, bottom = 24.dp),
            contentPadding = PaddingValues(horizontal = 80.dp, vertical = 16.dp),
            buttonState = state.buttonState,
        )

        TextWithClickableSuffix(
            text = "",
            modifier = Modifier.padding(bottom = 24.dp),
            suffixText = stringResource(id = R.string.forgot_password_question),
            onClick = goToForgotPassword
        )

        TextWithClickableSuffix(
            text = stringResource(id = R.string.no_account_question) + " ",
            suffixText = stringResource(id = R.string.create_account_button),
            onClick = goToSignUp
        )

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
private fun Form(
    signInForm: SignInForm,
    updateEntry: (SignInEvent) -> Unit
) {
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .padding(start = 32.dp, end = 32.dp, top = 24.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        RoundedEntryCard(
            entry = signInForm.username,
            modifier = Modifier.onFocusChanged {
                updateEntry(SignInEvent.UpdateUsernameFocus(it.hasFocus))
            },
            onImeAction = { focusManager.moveFocus(FocusDirection.Down) },
            onTextChanged = {
                updateEntry(SignInEvent.UpdateUsernameValue(it))
            }
        )

        RoundedEntryCard(
            entry = signInForm.password,
            modifier = Modifier.onFocusChanged {
                updateEntry(SignInEvent.UpdatePasswordFocus(it.hasFocus))
            },
            onImeAction = { focusManager.clearFocus() },
            onTextChanged = {
                updateEntry(SignInEvent.UpdatePasswordValue(it))
            }
        )
    }
}

@Preview
@Composable
private fun PreviewSignInScreen() {
    TodoAppTheme {
        SignIn(
            state = SignInState(),
            wavyScaffoldState = WavyScaffoldState(
                initialBackDropHeight = 128.dp,
                initialWaveHeight = 128.dp,
                initialFrequency = 0.3f
            )
        )
    }
}

@Preview
@Composable
private fun PreviewSignInScreenWithError(
    @PreviewParameter(WavyScaffoldStateProvider::class) state: WavyScaffoldState
) {
    TodoAppTheme {
        SignIn(
            wavyScaffoldState = state,
            state = SignInState(
                errorState = ErrorState.Message(
                    messageId = R.string.error_no_internet
                )
            )
        )
    }
}

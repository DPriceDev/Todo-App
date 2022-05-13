package dev.dprice.productivity.todo.auth.feature.screens.signup.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import dev.dprice.productivity.todo.auth.feature.screens.signup.model.SignUpEvent
import dev.dprice.productivity.todo.auth.feature.screens.signup.model.SignUpForm
import dev.dprice.productivity.todo.auth.feature.screens.signup.model.SignUpState
import dev.dprice.productivity.todo.auth.feature.ui.AuthWavyScaffold
import dev.dprice.productivity.todo.features.auth.feature.R
import dev.dprice.productivity.todo.ui.components.*
import dev.dprice.productivity.todo.ui.theme.TodoAppTheme

@Composable
fun SignUp(
    state: SignUpState,
    wavyScaffoldState: WavyScaffoldState,
    goToSignIn: () -> Unit,
    updateEntry: (SignUpEvent) -> Unit,
    submitForm: () -> Unit
) {
    AuthWavyScaffold(
        state = wavyScaffoldState,
        title = stringResource(id = R.string.create_account),
        description = stringResource(id = R.string.create_account_description),
        errorMessage = (state.errorState as? ErrorState.Message)?.let { stringResource(id = it.messageId) },
        frontContent = {
            Form(
                signUpForm = state.form,
                buttonState = state.buttonState,
                onEvent = updateEntry,
                onSignInClicked = goToSignIn,
                onSubmitForm = submitForm
            )
        }
    )
}

@Composable
private fun Form(
    signUpForm: SignUpForm,
    buttonState: ButtonState,
    onEvent: (SignUpEvent) -> Unit,
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
                onEvent(SignUpEvent.UpdateEmailFocus(it.hasFocus))
            },
            onImeAction = { focusManager.moveFocus(FocusDirection.Down) },
            onTextChanged = {
                onEvent(SignUpEvent.UpdateEmailValue(it))
            }
        )
        RoundedEntryCard(
            entry = signUpForm.username,
            modifier = Modifier.onFocusChanged {
                onEvent(SignUpEvent.UpdateUsernameFocus(it.hasFocus))
            },
            onImeAction = { focusManager.moveFocus(FocusDirection.Down) },
            onTextChanged = {
                onEvent(SignUpEvent.UpdateUsernameValue(it))
            }
        )
        RoundedEntryCard(
            entry = signUpForm.password,
            modifier = Modifier.onFocusChanged {
                onEvent(SignUpEvent.UpdatePasswordFocus(it.hasFocus))
            },
            onImeAction = { focusManager.clearFocus() },
            onTextChanged = {
                onEvent(SignUpEvent.UpdatePasswordValue(it))
            }
        )

        RoundedButton(
            text = stringResource(id = R.string.create_account_button),
            onClick = onSubmitForm,
            modifier = Modifier.padding(top = 8.dp, bottom = 24.dp),
            contentPadding = PaddingValues(horizontal = 80.dp, vertical = 16.dp),
            buttonState = buttonState,
        )

        TextWithClickableSuffix(
            text = stringResource(id = R.string.existing_account_question) + " ",
            suffixText = stringResource(id = R.string.sign_in),
            onClick = onSignInClicked
        )
    }
}

/**
 * Preview
 */

@Preview(showBackground = true)
@Composable
private fun PreviewSignUp(
    @PreviewParameter(WavyScaffoldStateProvider::class) state: WavyScaffoldState
) {
    TodoAppTheme {
        SignUp(
            SignUpState(),
            state,
            { },
            { },
            { }
        )
    }
}

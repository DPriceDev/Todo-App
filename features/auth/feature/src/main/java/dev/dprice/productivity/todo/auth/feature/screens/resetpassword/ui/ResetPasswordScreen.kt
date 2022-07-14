package dev.dprice.productivity.todo.auth.feature.screens.resetpassword.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.dprice.productivity.todo.auth.feature.model.ErrorState
import dev.dprice.productivity.todo.auth.feature.screens.resetpassword.model.ResetPasswordEvent
import dev.dprice.productivity.todo.auth.feature.screens.resetpassword.model.ResetPasswordEvent.*
import dev.dprice.productivity.todo.auth.feature.screens.resetpassword.model.ResetPasswordForm
import dev.dprice.productivity.todo.auth.feature.screens.resetpassword.model.ResetPasswordState
import dev.dprice.productivity.todo.auth.feature.ui.AuthWavyScaffold
import dev.dprice.productivity.todo.features.auth.feature.R
import dev.dprice.productivity.todo.ui.components.buttons.RoundedButton
import dev.dprice.productivity.todo.ui.components.scaffold.WavyScaffoldState
import dev.dprice.productivity.todo.ui.components.text.EntryField
import dev.dprice.productivity.todo.ui.components.text.RoundedEntryCard
import dev.dprice.productivity.todo.ui.theme.TodoAppTheme

@Composable
fun ResetPassword(
    state: ResetPasswordState,
    wavyScaffoldState: WavyScaffoldState,
    submitForm: () -> Unit,
    updateEntry: (ResetPasswordEvent) -> Unit
) {
    AuthWavyScaffold(
        state = wavyScaffoldState,
        title = stringResource(id = R.string.reset_password),
        description = stringResource(id = R.string.reset_password_description),
        errorMessage = (state.errorState as? ErrorState.Message)?.let { stringResource(id = it.messageId) },
        phaseOffset = 0.6f, // todo: Pass this into composable from previous or next nav location
        frontContent = {
            Form(
                state.form.code,
                state.form.password,
                updateEntry,
                submitForm
            )

            RoundedButton(
                text = stringResource(id = R.string.reset_password_button),
                onClick = submitForm,
                modifier = Modifier.padding(top = 8.dp, bottom = 24.dp),
                contentPadding = PaddingValues(horizontal = 80.dp, vertical = 16.dp),
                buttonState = state.buttonState,
            )
        }
    )
}

@Composable
private fun Form(
    codeEntry: EntryField,
    passwordEntry: EntryField,
    updateEntry: (ResetPasswordEvent) -> Unit,
    submitForm: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(start = 32.dp, end = 32.dp, top = 24.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val focusManager = LocalFocusManager.current
        RoundedEntryCard(
            entry = codeEntry,
            textStyle = LocalTextStyle.current.copy(
                textAlign = TextAlign.Center
            ),
            modifier = Modifier
                .onFocusChanged {
                    updateEntry(UpdateCodeFocus(it.hasFocus))
                },
            onImeAction = {
                focusManager.moveFocus(FocusDirection.Down)
            },
            onTextChanged = {
                updateEntry(UpdateCodeValue(it))
            }
        )

        RoundedEntryCard(
            entry = passwordEntry,
            modifier = Modifier
                .onFocusChanged {
                    updateEntry(UpdatePasswordFocus(it.hasFocus))
                },
            onImeAction = {
                focusManager.clearFocus()
                submitForm()
            },
            onTextChanged = {
                updateEntry(UpdatePasswordValue(it))
            }
        )
    }
}

@Preview
@Composable
fun PreviewResetPasswordScreen() {
    TodoAppTheme {
        val wavyScaffoldState = remember { WavyScaffoldState() }
        ResetPassword(
            state = ResetPasswordState(
                form = ResetPasswordForm(
                    code = ResetPasswordForm().code.copy(
                        value = "111"
                    )
                )
            ),
            wavyScaffoldState = wavyScaffoldState,
            { },
            { }
        )
    }
}

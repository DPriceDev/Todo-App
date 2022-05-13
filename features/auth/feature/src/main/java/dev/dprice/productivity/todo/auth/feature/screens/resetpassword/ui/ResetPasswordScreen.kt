package dev.dprice.productivity.todo.auth.feature.screens.resetpassword.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.dprice.productivity.todo.auth.feature.screens.resetpassword.ui.ResetPasswordEvent.*
import dev.dprice.productivity.todo.auth.feature.ui.TitleBlock
import dev.dprice.productivity.todo.ui.components.*
import dev.dprice.productivity.todo.ui.theme.TodoAppTheme

sealed class ResetPasswordEvent {
    data class UpdatePasswordValue(val value: String) : ResetPasswordEvent()
    data class UpdatePasswordFocus(val focus: Boolean) : ResetPasswordEvent()
    data class UpdateCodeValue(val value: String) : ResetPasswordEvent()
    data class UpdateCodeFocus(val focus: Boolean) : ResetPasswordEvent()
}

@Composable
fun ResetPassword(
    state: ResetPasswordState,
    wavyScaffoldState: WavyScaffoldState,
    submitForm: () -> Unit,
    updateEntry: (ResetPasswordEvent) -> Unit
) {
    WavyBackdropScaffold(
        state = wavyScaffoldState,
        phaseOffset = 0.6f, // todo: Pass this into composable from previous or next nav location
        backContent = {
            TitleBlock(
                colour = MaterialTheme.colors.background,
                title = "Reset Password"
            )
        },
        frontContent = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TitleBlock(colour = MaterialTheme.colors.primary, title = "Reset Password")

                Text(
                    text = "Please enter the verification code we sent to your email, and enter a new password for your account.",
                    modifier = Modifier.padding(top = 16.dp, start = 32.dp, end = 32.dp),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colors.onBackground
                )

                ResetPasswordForm(
                    state.form.code,
                    state.form.password,
                    updateEntry,
                    submitForm
                )

                RoundedButton(
                    text = "Reset password",
                    onClick = submitForm,
                    modifier = Modifier.padding(top = 8.dp, bottom = 24.dp),
                    contentPadding = PaddingValues(horizontal = 80.dp, vertical = 16.dp),
                    buttonState = state.buttonState,
                )
            }
        }
    )
}

// todo: might be worth moving the state passthrough to viewModel
@Composable
private fun ResetPasswordForm(
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
                    code = ResetPasswordForm().code.copy(value = "111")
                )
            ),
            wavyScaffoldState = wavyScaffoldState,
            { },
            { }
        )
    }
}

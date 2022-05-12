package dev.dprice.productivity.todo.auth.signin.ui.reset

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dev.dprice.productivity.todo.auth.signin.ui.reset.ResetPasswordEvent.UPDATE_CODE
import dev.dprice.productivity.todo.auth.signin.ui.reset.ResetPasswordEvent.UPDATE_PASSWORD
import dev.dprice.productivity.todo.ui.components.*

enum class ResetPasswordEvent {
    UPDATE_PASSWORD,
    UPDATE_CODE
}

@Composable
fun ResetPassword(
    state: WavyScaffoldState,
    returnToSignIn: () -> Unit,
    viewModel: ResetPasswordViewModel = hiltViewModel<ResetPasswordViewModelImpl>()
) {
    WavyBackdropScaffold(
        state = state,
        phaseOffset = 0.6f,
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
                    text = "Please enter the reset code we sent to your email and a new password for your account",
                    modifier = Modifier.padding(top = 16.dp, start = 32.dp, end = 32.dp),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colors.onBackground
                )

                ResetPasswordForm(
                    viewModel.viewState.code,
                    viewModel.viewState.password,
                    viewModel::update
                ) {
                    viewModel.submit(returnToSignIn)
                }

                RoundedButton(
                    text = "Reset password",
                    onClick = {
                        viewModel.submit(goBackToSignIn = returnToSignIn)
                    },
                    modifier = Modifier.padding(top = 8.dp, bottom = 24.dp),
                    contentPadding = PaddingValues(horizontal = 80.dp, vertical = 16.dp),
                    buttonState = viewModel.viewState.buttonState,
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
    updateEntry: (ResetPasswordEvent, String, Boolean) -> Unit,
    submitForm: () -> Unit
) {
    val focusManager = LocalFocusManager.current
    RoundedEntryCard(
        entry = codeEntry,
        modifier = Modifier
            .padding(top = 24.dp, start = 32.dp, end = 32.dp)
            .onFocusChanged {
                updateEntry(UPDATE_CODE, codeEntry.value, it.hasFocus)
            },
        onImeAction = {
            focusManager.moveFocus(FocusDirection.Down)
        },
        onTextChanged = {
            updateEntry(UPDATE_CODE, it, codeEntry.hasFocus)
        }
    )

    RoundedEntryCard(
        entry = passwordEntry,
        modifier = Modifier
            .padding(top = 24.dp, start = 32.dp, end = 32.dp)
            .onFocusChanged {
                updateEntry(UPDATE_PASSWORD, passwordEntry.value, it.hasFocus)
            },
        onImeAction = {
            focusManager.clearFocus()
            submitForm()
        },
        onTextChanged = {
            updateEntry(UPDATE_PASSWORD, it, passwordEntry.hasFocus)
        }
    )
}

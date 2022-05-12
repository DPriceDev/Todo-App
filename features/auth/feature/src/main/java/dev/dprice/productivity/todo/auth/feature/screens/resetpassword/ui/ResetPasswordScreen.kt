package dev.dprice.productivity.todo.auth.feature.screens.resetpassword.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import dev.dprice.productivity.todo.auth.feature.screens.resetpassword.ui.ResetPasswordEvent.*
import dev.dprice.productivity.todo.auth.feature.ui.TitleBlock
import dev.dprice.productivity.todo.ui.components.*

sealed class ResetPasswordEvent {
    data class UpdatePasswordValue(val value: String) : ResetPasswordEvent()
    data class UpdatePasswordFocus(val focus: Boolean) : ResetPasswordEvent()
    data class UpdateCodeValue(val value: String) : ResetPasswordEvent()
    data class UpdateCodeFocus(val focus: Boolean) : ResetPasswordEvent()
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
                    viewModel.viewState.form.code,
                    viewModel.viewState.form.password,
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
    updateEntry: (ResetPasswordEvent) -> Unit,
    submitForm: () -> Unit
) {
    val focusManager = LocalFocusManager.current
    RoundedEntryCard(
        entry = codeEntry,
        textStyle = MaterialTheme.typography.h2.copy(
            textAlign = TextAlign.Center
        ),
        modifier = Modifier
            .width(256.dp)
            .padding(top = 24.dp, start = 32.dp, end = 32.dp)
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
            .padding(top = 24.dp, start = 32.dp, end = 32.dp)
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

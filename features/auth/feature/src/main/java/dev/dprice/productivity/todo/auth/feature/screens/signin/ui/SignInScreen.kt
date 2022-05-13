package dev.dprice.productivity.todo.auth.feature.screens.signin.ui

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.dprice.productivity.todo.auth.feature.screens.signin.model.ErrorState
import dev.dprice.productivity.todo.auth.feature.screens.signin.model.SignInAction
import dev.dprice.productivity.todo.auth.feature.screens.signin.model.SignInAction.Type
import dev.dprice.productivity.todo.auth.feature.screens.signin.model.SignInForm
import dev.dprice.productivity.todo.auth.feature.screens.signin.model.SignInState
import dev.dprice.productivity.todo.auth.feature.ui.TitleBlock
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
    onFormUpdated: (SignInAction) -> Unit = { }

) {
    // todo: Need to scroll somehow
    WavyBackdropScaffold(
        state = wavyScaffoldState,
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

                Text(
                    text = "Sign in with your username and password.",
                    modifier = Modifier.padding(top = 16.dp),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.body1,
                )

                AnimatedVisibility(visible = state.error is ErrorState.Message) {
                    if (state.error is ErrorState.Message) {
                        WarningMessage(
                            message = stringResource(id = state.error.messageId),
                            modifier = Modifier.padding(top = 16.dp, start = 32.dp, end = 32.dp)
                        )
                    }
                }

                // todo: Show password in entry field
                Form(
                    signInForm = state.form,
                    onEntryChanged = onFormUpdated
                )

                RoundedButton(
                    text = "Sign in",
                    onClick = onSubmitForm,
                    modifier = Modifier.padding(top = 8.dp, bottom = 24.dp),
                    contentPadding = PaddingValues(horizontal = 80.dp, vertical = 16.dp),
                    buttonState = state.buttonState,
                )

                TextWithClickableSuffix(
                    text = "",
                    modifier = Modifier.padding(bottom = 24.dp),
                    suffixText = "Forgot password?",
                    onClick = goToForgotPassword
                )

                TextWithClickableSuffix(
                    text = "Don't have an account yet? ",
                    suffixText = "Create account",
                    onClick = goToSignUp
                )
            }
        }
    )
}

@Composable
private fun Form(
    signInForm: SignInForm,
    onEntryChanged: (SignInAction) -> Unit
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
                onEntryChanged(
                    SignInAction(
                        signInForm.username.value,
                        it.hasFocus,
                        Type.UPDATE_USERNAME
                    )
                )
            },
            onImeAction = { focusManager.moveFocus(FocusDirection.Down) },
            onTextChanged = {
                onEntryChanged(
                    SignInAction(
                        it,
                        signInForm.username.hasFocus,
                        Type.UPDATE_USERNAME
                    )
                )
            }
        )
        RoundedEntryCard(
            entry = signInForm.password,
            modifier = Modifier.onFocusChanged {
                onEntryChanged(
                    SignInAction(
                        signInForm.password.value,
                        it.hasFocus,
                        Type.UPDATE_PASSWORD
                    )
                )
            },
            onImeAction = { focusManager.clearFocus() },
            onTextChanged = {
                onEntryChanged(
                    SignInAction(
                        it,
                        signInForm.password.hasFocus,
                        Type.UPDATE_PASSWORD
                    )
                )
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
private fun PreviewSignInScreenWithError() {
    TodoAppTheme {
        SignIn(
            state = SignInState(
                error = ErrorState.Message(
                    messageId = R.string.error_no_internet
                )
            ),
            wavyScaffoldState = WavyScaffoldState(
                initialBackDropHeight = 128.dp,
                initialWaveHeight = 128.dp,
                initialFrequency = 0.3f
            )
        )
    }
}

package dev.dprice.productivity.todo.auth.verify.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dev.dprice.productivity.todo.ui.components.*
import dev.dprice.productivity.todo.ui.theme.TodoAppTheme

@Composable
fun VerifyCode(
    state: WavyScaffoldState,
    username: String,
    goToMainApp: () -> Unit,
    goBack: () -> Unit,
    viewModel: VerifyCodeViewModel = hiltViewModel<VerifyCodeViewModelImpl>()
) {
    OnBackDialog(onConfirm = goBack)

    WavyBackdropScaffold(
        state = state,
        phaseOffset = 0.3f,
        backContent = {
            TitleBlock(colour = MaterialTheme.colors.background, title = "Verify Sign Up")
        },
        frontContent = {
            val focusManager = LocalFocusManager.current

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TitleBlock(colour = MaterialTheme.colors.primary, title = "Verify Sign Up")

                Text(
                    text = "Please enter the verification code that has been sent to your email address",
                    modifier = Modifier.padding(top = 16.dp, start = 32.dp, end = 32.dp),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colors.onBackground
                )

                RoundedEntryCard(
                    entry = viewModel.viewState.code,
                    textStyle = MaterialTheme.typography.h2.copy(
                        textAlign = TextAlign.Center
                    ),
                    modifier = Modifier
                        .padding(top = 24.dp)
                        .width(256.dp)
                        .onFocusChanged {
                            viewModel.updateCode(viewModel.viewState.code.value, it.hasFocus)
                        },
                    onImeAction = {
                        focusManager.clearFocus()
                        viewModel.onSubmit(username, goToMainApp)
                    },
                    onTextChanged = {
                        viewModel.updateCode(it, viewModel.viewState.code.hasFocus)
                    }
                )

                RoundedButton(
                    text = "Verify",
                    onClick = {
                        viewModel.onSubmit(username, goToMainApp = goToMainApp)
                    },
                    modifier = Modifier.padding(top = 8.dp, bottom = 24.dp),
                    contentPadding = PaddingValues(horizontal = 80.dp, vertical = 16.dp),
                    buttonState = viewModel.viewState.buttonState,
                )

                Text(
                    text = "Not received your verification code yet?",
                    modifier = Modifier.padding(horizontal = 32.dp),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colors.onBackground
                )

                TextWithClickableSuffix(
                    text = "",
                    suffixText = "Resend verification code",
                    onClick = {
                        viewModel.resendVerificationCode(username)
                    }
                )

            }
        }
    )
}

@Composable
fun OnBackDialog(
    onConfirm: () -> Unit
) {
    val isDialogShown: MutableState<Boolean> = remember { mutableStateOf(false) }

    BackHandler(enabled = !isDialogShown.value) {
        isDialogShown.value = true
    }

    if (isDialogShown.value) {
       AppDialog(
           title = "Are you sure you want to leave?",
           message = "Your account can be verified at a later date when signing in.",
           onConfirm = onConfirm,
           isDialogShown = isDialogShown
       )
    }
}

/**
 * Preview
 */

@Preview
@Composable
private fun PreviewVerifyCode() {
    val state = WavyScaffoldState(
        initialBackDropHeight = 128.dp,
        initialWaveHeight = 128.dp,
        initialFrequency = 0.3f
    )
    TodoAppTheme() {
        VerifyCode(state = state, "testUser", { }, { })
    }
}

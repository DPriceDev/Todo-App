package dev.dprice.productivity.todo.auth.feature.screens.forgotpassword.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dev.dprice.productivity.todo.auth.feature.ui.components.TitleBlock
import dev.dprice.productivity.todo.ui.components.*

@Composable
fun ForgotPassword(
    state: WavyScaffoldState,
    goToResetPassword: () -> Unit,
    viewModel: ForgotPasswordViewModel = hiltViewModel<ForgotPasswordViewModelImpl>()
) {
    WavyBackdropScaffold(
        state = state,
        phaseOffset = 0.3f,
        backContent = {
            TitleBlock(
                colour = MaterialTheme.colors.background,
                title = "Forgot Password"
            )
        },
        frontContent = {
            val focusManager = LocalFocusManager.current

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TitleBlock(colour = MaterialTheme.colors.primary, title = "Forgot Password")

                Text(
                    text = "Please enter the email address associated to your account and we will send you a email to reset your password.",
                    modifier = Modifier.padding(top = 16.dp, start = 32.dp, end = 32.dp),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colors.onBackground
                )

                RoundedEntryCard(
                    entry = viewModel.viewState.email,
                    modifier = Modifier
                        .padding(top = 24.dp, start = 32.dp, end = 32.dp)
                        .onFocusChanged {
                            viewModel.updateEmail(viewModel.viewState.email.value, it.hasFocus)
                        },
                    onImeAction = {
                        focusManager.clearFocus()
                        viewModel.submit(goToResetPassword = goToResetPassword)
                    },
                    onTextChanged = {
                        viewModel.updateEmail(it, viewModel.viewState.email.hasFocus)
                    }
                )

                RoundedButton(
                    text = "Reset password",
                    onClick = {
                        viewModel.submit(goToResetPassword = goToResetPassword)
                    },
                    modifier = Modifier.padding(top = 8.dp, bottom = 24.dp),
                    contentPadding = PaddingValues(horizontal = 80.dp, vertical = 16.dp),
                    buttonState = viewModel.viewState.buttonState,
                )

                TextWithClickableSuffix(
                    text = "",
                    modifier = Modifier.padding(bottom = 24.dp),
                    suffixText = "Already have a reset code?",
                    onClick = goToResetPassword
                )
            }
        }
    )
}

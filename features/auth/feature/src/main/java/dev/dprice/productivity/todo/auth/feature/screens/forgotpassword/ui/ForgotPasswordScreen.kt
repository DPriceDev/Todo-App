package dev.dprice.productivity.todo.auth.feature.screens.forgotpassword.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dev.dprice.productivity.todo.auth.feature.model.ErrorState
import dev.dprice.productivity.todo.auth.feature.screens.forgotpassword.model.ForgotPasswordEvent
import dev.dprice.productivity.todo.auth.feature.screens.forgotpassword.model.ForgotPasswordState
import dev.dprice.productivity.todo.auth.feature.ui.AuthWavyScaffold
import dev.dprice.productivity.todo.features.auth.feature.R
import dev.dprice.productivity.todo.ui.components.TextWithClickableSuffix
import dev.dprice.productivity.todo.ui.components.buttons.RoundedButton
import dev.dprice.productivity.todo.ui.components.scaffold.WavyScaffoldState
import dev.dprice.productivity.todo.ui.components.text.RoundedEntryCard

@Composable
fun ForgotPassword(
    state: ForgotPasswordState,
    wavyScaffoldState: WavyScaffoldState,
    onEvent: (ForgotPasswordEvent) -> Unit,
    onSubmit: () -> Unit,
    goToResetPassword: () -> Unit
) {
    AuthWavyScaffold(
        state = wavyScaffoldState,
        title = stringResource(id = R.string.forgot_password),
        description = stringResource(id = R.string.forgot_password_description),
        errorMessage = (state.errorState as? ErrorState.Message)?.let { stringResource(id = it.messageId) },
        phaseOffset = 0.3f, // todo: pass in
        frontContent = {
            val focusManager = LocalFocusManager.current

            RoundedEntryCard(
                entry = state.username,
                modifier = Modifier
                    .padding(top = 24.dp, start = 32.dp, end = 32.dp)
                    .onFocusChanged {
                        onEvent(ForgotPasswordEvent.UpdateUsernameFocus(it.hasFocus))
                    },
                onImeAction = {
                    focusManager.clearFocus()
                    onSubmit()
                },
                onTextChanged = {
                    onEvent(ForgotPasswordEvent.UpdateUsernameValue(it))
                }
            )

            RoundedButton(
                text = stringResource(id = R.string.forgot_password_button),
                onClick = onSubmit,
                modifier = Modifier.padding(top = 8.dp, bottom = 24.dp),
                contentPadding = PaddingValues(horizontal = 80.dp, vertical = 16.dp),
                buttonState = state.buttonState,
            )

            TextWithClickableSuffix(
                text = "",
                modifier = Modifier.padding(bottom = 24.dp),
                suffixText = stringResource(id = R.string.already_have_code_question),
                onClick = goToResetPassword
            )
        }
    )
}

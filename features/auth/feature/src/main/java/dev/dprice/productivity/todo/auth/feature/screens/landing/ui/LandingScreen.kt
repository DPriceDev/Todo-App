package dev.dprice.productivity.todo.auth.feature.screens.landing.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.dprice.productivity.todo.ui.components.RoundedButton
import dev.dprice.productivity.todo.ui.components.SpinningCircles
import dev.dprice.productivity.todo.ui.components.WavyBackdropScaffold
import dev.dprice.productivity.todo.ui.components.WavyScaffoldState
import dev.dprice.productivity.todo.ui.theme.TextBackground
import dev.dprice.productivity.todo.ui.theme.TodoAppTheme
import dev.dprice.productivity.todo.ui.theme.Yellow

@Composable
fun AuthLanding(
    state: WavyScaffoldState,
    goToSignUp: () -> Unit,
    goToSignIn: () -> Unit
) {
    BoxWithConstraints {
        WavyBackdropScaffold(
            state = state,
            backContent = {
                SpinningCircles(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(this@BoxWithConstraints.maxHeight - 248.dp)
                )
            },
            frontContent = {
                TextBlock(
                    goToSignUp = goToSignUp,
                    goToSignIn = goToSignIn,
                    modifier = Modifier.align(Alignment.BottomCenter)
                )
            }
        )
    }
}

@Composable
private fun TextBlock(
    goToSignUp: () -> Unit,
    goToSignIn: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp),
        modifier = Modifier
            .padding(32.dp)
            .then(modifier)
    ) {
        Text(
            text = "Much Todo",
            style = MaterialTheme.typography.h1,
            textAlign = TextAlign.Center,
            color = Yellow
        )

        Text(
            text = """
                Some sort of description here...
                Some sort of description here...
            """.trimIndent(),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.body1,
            color = TextBackground
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(24.dp, Alignment.CenterHorizontally)
        ) {
            RoundedButton(
                text = "Sign In",
                onClick = goToSignIn
            )
            RoundedButton(
                text = "Sign Up",
                onClick = goToSignUp
            )
        }
    }
}

/**
 * Preview
 */

@Preview
@Composable
private fun PreviewAuthLanding() {

    TodoAppTheme {
        BoxWithConstraints() {
            val state = WavyScaffoldState(
                initialBackDropHeight = maxHeight - 248.dp,
                initialWaveHeight = 48.dp,
                initialFrequency = 0.3f
            )

            AuthLanding(
                state,
                { }
            ) { }
        }
    }
}

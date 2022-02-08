package dev.dprice.productivity.todo.auth.feature.ui.landing

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.dprice.productivity.todo.ui.components.RoundedButton
import dev.dprice.productivity.todo.ui.components.WavePosition
import dev.dprice.productivity.todo.ui.components.WavyScaffold
import dev.dprice.productivity.todo.ui.theme.TextBackground
import dev.dprice.productivity.todo.ui.theme.TodoAppTheme
import dev.dprice.productivity.todo.ui.theme.Yellow

@Composable
fun AuthLanding(
    goToSignUp: () -> Unit,
    goToSignIn: () -> Unit
) {

    // todo need to make scaffold with variable wave height
    // todo or use offset padding?
    WavyScaffold(
        topContent = { height ->
            Box(
                modifier = Modifier.height(height + 64.dp)
            ) {
                FloatingCircles()
            }
        },
        wavePosition = WavePosition.Percentage(0.45f),
        waveHeight = 32.dp,
        waveFrequency = 0.45f
    ) { topPadding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxHeight()
                .padding(top = topPadding + 8.dp, bottom = 8.dp)
        ) {
            TextBlock(goToSignUp, goToSignIn)
        }
    }
}

@Composable
private fun TextBlock(
    goToSignUp: () -> Unit,
    goToSignIn: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp),
        modifier = Modifier.padding(horizontal = 32.dp)
    ) {
        Text(
            text = "Much Todo About Nothing",
            style = MaterialTheme.typography.h2,
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
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally)
        ) {
            RoundedButton(onClick = goToSignIn) {
                Text(text = "Sign In")
            }

            RoundedButton(onClick = goToSignUp) {
                Text(text = "Sign Up")
            }
        }
    }
}

@Composable
private fun FloatingCircles() {
    Canvas(
        modifier = Modifier.fillMaxSize()
    ) {
        drawCircle(
            Color.Red,
            100f,
            center = Offset(size.width / 2, size.height / 2)
        )

        drawCircle(
            Color.Green,
            100f,
            center = Offset(size.width / 4, size.height / 4)
        )

        drawCircle(
            Color.Green,
            100f,
            center = Offset(size.width / 2, size.height)
        )
    }
}

/**
 * Preview
 */

@Preview
@Composable
private fun PreviewAuthLanding() {
    TodoAppTheme {
        AuthLanding({ }) { }
    }
}
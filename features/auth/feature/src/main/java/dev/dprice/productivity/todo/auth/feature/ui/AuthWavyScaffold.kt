package dev.dprice.productivity.todo.auth.feature.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.dprice.productivity.todo.ui.components.WarningMessage
import dev.dprice.productivity.todo.ui.components.scaffold.WavyBackdropScaffold
import dev.dprice.productivity.todo.ui.components.scaffold.WavyScaffoldState
import dev.dprice.productivity.todo.ui.components.scaffold.WavyScaffoldStateProvider
import dev.dprice.productivity.todo.ui.theme.TodoAppTheme

@Composable
fun AuthWavyScaffold(
    state: WavyScaffoldState,
    title: String,
    modifier: Modifier = Modifier,
    description: String = "",
    errorMessage: String? = null,
    scrollState: ScrollState = rememberScrollState(),
    phaseOffset: Float = 0.0f,
    backgroundContentColour: Color = MaterialTheme.colors.background,
    foregroundContentColour: Color = MaterialTheme.colors.primary,
    backgroundColour: Color = MaterialTheme.colors.surface,
    foregroundColour: Color = MaterialTheme.colors.background,
    backContent: @Composable ColumnScope.(height: Dp) -> Unit = { },
    frontContent: @Composable ColumnScope.(padding: Dp) -> Unit = { },
) {
    WavyBackdropScaffold(
        state = state,
        modifier = modifier,
        backgroundColour = backgroundColour,
        foregroundColour = foregroundColour,
        phaseOffset = phaseOffset,
        backContent = { height ->
            Column {
                TitleBlock(
                    colour = backgroundContentColour,
                    title = title,
                    modifier = Modifier.graphicsLayer {
                        translationY = -scrollState.value.toFloat()
                    }
                )
                backContent(height)
            }
        },
        frontContent = { padding ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .verticalScroll(scrollState)
            ) {
                TitleBlock(
                    colour = foregroundContentColour,
                    title = title
                )
                Text(
                    text = description,
                    modifier = Modifier.padding(top = 16.dp, start = 32.dp, end = 32.dp),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.body1
                )

                ErrorMessage(errorMessage)

                frontContent(padding)
            }
        }
    )
}

@Composable
private fun ErrorMessage(
    message: String?
) {
    AnimatedVisibility(visible = message != null) {
        message?.let {
            Crossfade(targetState = it) { message ->
                WarningMessage(
                    message = message,
                    modifier = Modifier.padding(top = 16.dp, start = 32.dp, end = 32.dp)
                )
            }
        }
    }
}

@Preview
@Composable
private fun PreviewTitledErrorWavyBackdropScaffold(
    @PreviewParameter(WavyScaffoldStateProvider::class) state: WavyScaffoldState
) {
    TodoAppTheme {
        AuthWavyScaffold(
            state = state,
            title = LoremIpsum(2).values.joinToString(" "),
            description = LoremIpsum(10).values.joinToString(" "),
        ) {
            Text(
                text = LoremIpsum(100).values.joinToString(" "),
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

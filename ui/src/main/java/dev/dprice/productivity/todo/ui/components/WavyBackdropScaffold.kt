package dev.dprice.productivity.todo.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.dprice.productivity.todo.ui.theme.TodoAppTheme

@Composable
fun WavyBackdropScaffold(
    state: WavyScaffoldState,
    modifier: Modifier = Modifier,
    phaseOffset: Float = 0.0f,
    backgroundColour: Color = MaterialTheme.colors.surface,
    foregroundColour: Color = MaterialTheme.colors.background,
    layoutBeyondConstraints: Boolean = true,
    backContent: @Composable BoxScope.(height: Dp) -> Unit = { },
    frontContent: @Composable BoxScope.(topPadding: Dp) -> Unit = { },
) {
    WavyBackdropScaffold(
        backDropHeight = state.getBackDropHeight().value,
        waveHeight = state.getWaveHeight().value,
        waveFrequency = state.getFrequency().value,
        waveOffsetPercent = state.getPhase().value + phaseOffset,
        backgroundColour = backgroundColour,
        foregroundColour = foregroundColour,
        layoutBeyondConstraints = layoutBeyondConstraints,
        modifier = modifier,
        backContent = backContent,
        frontContent = frontContent
    )
}

@Composable
fun WavyBackdropScaffold(
    backDropHeight: Dp,
    modifier: Modifier = Modifier,
    waveHeight: Dp = 128.dp,
    waveFrequency: Float = 0.3f,
    waveOffsetPercent: Float = 0.0f,
    backgroundColour: Color = MaterialTheme.colors.surface,
    foregroundColour: Color = MaterialTheme.colors.background,
    layoutBeyondConstraints: Boolean = true,
    backContent: @Composable BoxScope.(height: Dp) -> Unit,
    frontContent: @Composable BoxScope.(topPadding: Dp) -> Unit,
) {
    val backOffset: Dp = maxOf(backDropHeight - waveHeight, 0.dp)

    Box(
        modifier = modifier
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = foregroundColour,
        ) {
            Box {
                frontContent(backOffset)
            }
        }
        
        WaveBottomCard(
            modifier = Modifier
                .height(backDropHeight)
                .clipToBounds()
                .fillMaxWidth(),
            backgroundColor = backgroundColour,
            waveHeight = waveHeight,
            frequency = waveFrequency,
            waveOffset = waveOffsetPercent
        ) {
            val layoutModifier = if (layoutBeyondConstraints) {
                // This layout is measuring an infinite child, then placing the children at 0, 0
                Modifier.layout { measurable, constraints ->
                    val childConstraints = constraints.copy(
                        maxHeight = Constraints.Infinity,
                        maxWidth = constraints.maxWidth
                    )
                    val placeable = measurable.measure(childConstraints)
                    val width = placeable.width.coerceAtMost(constraints.maxWidth)
                    val height = placeable.height.coerceAtMost(constraints.maxHeight)

                    layout(width, height) {
                        placeable.placeRelativeWithLayer(0, 0)
                    }
                }
            } else {
                Modifier
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .then(layoutModifier)
            ) {
                backContent(backOffset)
            }
        }
    }
}

@Preview
@Composable
fun PreviewWavyScaffoldAt20Percent() {
    TodoAppTheme {
        WavyBackdropScaffold(
            backDropHeight = 128.dp + 16.dp,
            waveFrequency = 1f,
            backContent = { height ->
                Box(
                    contentAlignment = Alignment.TopCenter,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = height)
                ) {
                    Text(text = "Aligned Content")
                }
            },
            frontContent = { topOffset ->
                Box(
                    contentAlignment = Alignment.TopCenter,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = topOffset)
                ) {
                    Text(text = "Aligned Content")
                }
            }
        )
    }
}

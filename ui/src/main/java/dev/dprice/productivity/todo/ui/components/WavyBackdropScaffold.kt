package dev.dprice.productivity.todo.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.BackdropScaffoldState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.layout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.dprice.productivity.todo.ui.theme.TodoAppTheme

data class WavyScaffoldState(
    val duration: Int = 500,
    var targetPosition: MutableState<Dp> = mutableStateOf(0.dp),
    var targetFrequency: MutableState<Float> = mutableStateOf(1f),
    var targetHeight: MutableState<Dp> = mutableStateOf(0.dp),
    var waveDuration: MutableState<Int> = mutableStateOf(15_000),
)

data class WavyScaffoldConfig(
    val backDropHeight: Dp,
    val waveHeight: Dp,
    val waveFrequency: Float,
    val waveOffsetPercent: Float,
)

@Composable
fun WavyBackdropScaffold(
    config: WavyScaffoldConfig,
    modifier: Modifier = Modifier,
    backContent: @Composable BoxScope.(height: Dp) -> Unit,
    frontContent: @Composable BoxScope.(topPadding: Dp) -> Unit,
) {
    WavyBackdropScaffold(
        backRevealHeight = config.backDropHeight,
        waveHeight = config.waveHeight,
        waveFrequency = config.waveFrequency,
        waveOffsetPercent = config.waveOffsetPercent,
        modifier = modifier,
        backContent = backContent,
        frontContent = frontContent
    )
}

@Composable
fun WavyBackdropScaffold(
    backRevealHeight: Dp,
    modifier: Modifier = Modifier,
    waveHeight: Dp = 128.dp,
    waveFrequency: Float = 0.3f,
    waveOffsetPercent: Float = 0.0f,
    backContent: @Composable BoxScope.(height: Dp) -> Unit,
    frontContent: @Composable BoxScope.(topPadding: Dp) -> Unit,
) {
    BoxWithConstraints(
        modifier = modifier
    ) {
        val backOffset = maxOf(backRevealHeight - waveHeight, 0.dp)

        Surface(
            modifier = Modifier
                .width(maxWidth)
                .height(maxHeight),
            color = MaterialTheme.colors.background
        ) {
            Box(
                modifier = Modifier.size(maxWidth, maxHeight)
            ) {
                frontContent(backOffset)
            }
        }

        WaveBottomCard(
            modifier = Modifier.size(maxWidth, backRevealHeight),
            waveHeight = waveHeight,
            frequency = waveFrequency,
            waveOffset = waveOffsetPercent
        ) {
            Box(
                modifier = Modifier
                    .size(maxWidth, maxHeight)
                    // This layout is measuring an infinite child, then placing the children at 0, 0
                    .layout { measurable, constraints ->
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
            backRevealHeight =128.dp + 16.dp,
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
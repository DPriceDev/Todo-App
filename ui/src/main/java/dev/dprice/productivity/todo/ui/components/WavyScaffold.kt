package dev.dprice.productivity.todo.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.dprice.productivity.todo.ui.theme.TodoAppTheme

sealed class WavePosition {
    data class Top(val padding: Dp) : WavePosition()
    data class Percentage(val percent: Float) : WavePosition()
}

@Composable
fun WavyScaffold(
    position: WavePosition,
    modifier: Modifier = Modifier,
    waveHeight: Dp = 128.dp,
    waveDuration: Int = 15_000,
    waveFrequency: Float = 0.3f,
    positionAnimationSpec: AnimationSpec<Dp> = tween(
        durationMillis = 500,
        easing = LinearEasing
    ),
    backContent: @Composable (height: Dp) -> Unit,
    frontContent: @Composable (topPadding: Dp) -> Unit,
) {
    val transition = rememberInfiniteTransition()
    val waveOffset = transition.animateFloat(
        initialValue = 0.0f,
        targetValue = 1.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = waveDuration,
                easing = LinearEasing
            )
        )
    )

    BoxWithConstraints(
        modifier = modifier
    ) {
        val backHeight = when(position) {
            is WavePosition.Percentage -> maxHeight * position.percent
            is WavePosition.Top -> position.padding
        }
        val animatedOffset = animateDpAsState(
            targetValue = backHeight,
            animationSpec = positionAnimationSpec
        )
        val backOffset = maxOf(animatedOffset.value - waveHeight, 0.dp)

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
            modifier = Modifier.size(maxWidth, animatedOffset.value),
            waveHeight = waveHeight,
            frequency = waveFrequency,
            waveOffset = waveOffset.value
        ) {
            Box(
                modifier = Modifier.size(maxWidth, maxHeight)
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
        WavyScaffold(
            position = WavePosition.Top(128.dp + 16.dp),
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

@Preview
@Composable
fun PreviewWavyScaffoldAt50Percent() {
    TodoAppTheme {
        WavyScaffold(
            position = WavePosition.Percentage(0.5f),
            backContent = {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(text = "Aligned Content")
                }
            },
            frontContent = {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(text = "Aligned Content")
                }
            }
        )
    }
}

@Preview
@Composable
fun PreviewWavyScaffoldAt80Percent() {
    TodoAppTheme {
        WavyScaffold(
            modifier = Modifier.fillMaxSize(),
            position = WavePosition.Percentage(.8f),
            backContent = {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(text = "Aligned Content")
                }
            },
            frontContent = {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(128.dp),
                ) {
                    Text(text = "Aligned Content")
                }
            }
        )
    }
}
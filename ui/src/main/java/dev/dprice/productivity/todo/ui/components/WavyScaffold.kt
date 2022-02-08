package dev.dprice.productivity.todo.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

sealed class WavePosition {
    data class Top(val padding: Dp) : WavePosition()
    data class Percentage(val percent: Float) : WavePosition()
    object Wrap: WavePosition()
}

@Composable
fun WavyScaffold(
    waveHeight: Dp = 128.dp,
    waveDuration: Int = 15_000,
    waveFrequency: Float = 0.3f,
    wavePosition: WavePosition = WavePosition.Top(16.dp),
    topContent: @Composable (topPadding: Dp) -> Unit,
    bottomContent: @Composable (topPadding: Dp) -> Unit,
) {
    BoxWithConstraints {
        val padding = when(wavePosition) {
            is WavePosition.Percentage -> (maxHeight * wavePosition.percent) - (waveHeight / 2)
            is WavePosition.Top -> wavePosition.padding
            is WavePosition.Wrap -> Dp.Infinity
        }

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

        Surface {
            BoxWithConstraints(
                modifier = Modifier.fillMaxHeight()
            ) {
                // need to pass the correct padding here
                topContent(padding)
            }

            val cardModifier = if(wavePosition == WavePosition.Wrap) {
                Modifier.align(Alignment.BottomCenter)
            } else {
                Modifier
                    .fillMaxHeight()
                    .padding(top = padding)
            }

            WaveToppedCard(
                waveHeight = waveHeight,
                waveOffset = waveOffset.value,
                frequency = waveFrequency,
                backgroundColor = MaterialTheme.colors.background,
                modifier = cardModifier,
                content = {
                    bottomContent(waveHeight)
                }
            )
        }
    }
}


package dev.dprice.productivity.todo.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun WavyScaffold(
    waveHeight: Dp = 128.dp,
    waveDuration: Int = 15_000,
    waveFrequency: Float = 0.3f,
    topPadding: Dp = 16.dp,
    topContent: @Composable () -> Unit,
    bottomContent: @Composable () -> Unit,
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

    Surface {
        Box {
            topContent()
        }

        WaveToppedCard(
            waveHeight = waveHeight,
            waveOffset = waveOffset.value,
            frequency = waveFrequency,
            backgroundColor = MaterialTheme.colors.background,
            modifier = Modifier
                .fillMaxHeight()
                .padding(top = topPadding),
            content = bottomContent
        )
    }
}


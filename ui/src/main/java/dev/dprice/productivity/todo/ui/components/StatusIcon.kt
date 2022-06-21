package dev.dprice.productivity.todo.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.outlined.Circle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.dp
import dev.dprice.productivity.todo.ui.theme.completeColour

// todo: separate out icon animation
@Composable
fun StatusIcon(
    isComplete: Boolean,
    modifier: Modifier = Modifier,
    index: Int = 0,
    pulseDuration: Int = 2000
) {
    Box(
        modifier = Modifier
            .padding(end = 12.dp)
            .then(modifier)
    ) {
        val scale: Float by animateFloatAsState(
            targetValue = if (isComplete) 1.0f else 0.0f,
            animationSpec = tween(
                durationMillis = 300
            )
        )

        val rotation: Float by animateFloatAsState(
            targetValue = if (isComplete) 360f else 0.0f,
            animationSpec = tween(
                durationMillis = 300
            )
        )

        val scaleWave: Float by rememberInfiniteTransition().animateFloat(
            initialValue = 1.0f,
            targetValue = 1.2f,
            animationSpec = infiniteRepeatable(
                repeatMode = RepeatMode.Reverse,
                animation = tween(
                    durationMillis = pulseDuration,
                    easing = LinearEasing
                ),
                initialStartOffset = StartOffset(
                    (pulseDuration * (index / 4f)).toInt(),
                    StartOffsetType.FastForward
                )
            )
        )

        if (scale != 1f) {
            Icon(
                Icons.Outlined.Circle,
                null,
                Modifier.size(24.dp)
            )
        }

        if (scale != 0f) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .scale(scale * scaleWave)
                    .rotate(rotation),
            ) {
                Icon(
                    Icons.Filled.Circle,
                    null,
                    Modifier.size(24.dp),
                    tint = completeColour
                )
                Icon(
                    Icons.Default.Check,
                    null,
                    Modifier
                        .size(24.dp)
                        .padding(4.dp),
                )
            }
        }
    }
}
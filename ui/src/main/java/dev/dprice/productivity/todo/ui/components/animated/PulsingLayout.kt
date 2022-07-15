package dev.dprice.productivity.todo.ui.components.animated

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale

@Composable
fun PulsingLayout(
    modifier: Modifier = Modifier,
    durationMillis: Int = 3000,
    pulseRange: ClosedRange<Float> = 0.98f..1.0f,
    easing: Easing = FastOutSlowInEasing,
    content: @Composable () -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition()
    val pulseAnimation by infiniteTransition.animateFloat(
        initialValue = pulseRange.start,
        targetValue = pulseRange.endInclusive,
        animationSpec = infiniteRepeatable(
            tween(durationMillis = durationMillis, easing = easing),
            RepeatMode.Reverse
        )
    )

    Box(
        modifier = Modifier
            .scale(pulseAnimation)
            .then(modifier)
    ) {
        content()
    }

}
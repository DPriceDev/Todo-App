package dev.dprice.productivity.todo.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color

// todo: convert pulsing to modifier?
@Composable
fun PulsingButton(
    backgroundColour: Color,
    modifier: Modifier = Modifier,
    durationMillis: Int = 3000,
    pulseRange: ClosedRange<Float> = 0.98f..1.0f,
    onClick: () -> Unit,
    content: @Composable () -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition()
    val pulseAnimation by infiniteTransition.animateFloat(
        initialValue = pulseRange.start,
        targetValue = pulseRange.endInclusive,
        animationSpec = infiniteRepeatable(
            tween(durationMillis = durationMillis, easing = FastOutSlowInEasing),
            RepeatMode.Reverse
        )
    )

    RoundedButton(
        colors = ButtonDefaults.buttonColors(
            backgroundColor = backgroundColour
        ),
        onClick = { onClick() },
        contentPadding = PaddingValues(),
        modifier = Modifier
            .scale(pulseAnimation)
            .then(modifier)
    ) {
        content()
    }
}
package dev.dprice.productivity.todo.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

// todo: convert pulsing to modifier?
@Composable
fun PulsingButton(
    backgroundColour: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    content: @Composable () -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition()
    val pulseAnimation by infiniteTransition.animateFloat(
        initialValue = 0.98f,
        targetValue = 1.0f,
        animationSpec = infiniteRepeatable(
            tween(durationMillis = 3000, easing = FastOutSlowInEasing),
            RepeatMode.Reverse
        )
    )

    Card(
        shape = RoundedCornerShape(percent = 50),
        backgroundColor = backgroundColour,
        elevation = 8.dp,
        modifier = Modifier
            .scale(pulseAnimation)
            .clickable { onClick() }
            .then(modifier),
        content = content
    )
}
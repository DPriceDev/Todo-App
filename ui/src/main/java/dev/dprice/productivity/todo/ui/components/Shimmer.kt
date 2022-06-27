package dev.dprice.productivity.todo.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode

@Composable
fun Shimmer(
    modifier: Modifier = Modifier,
    duration: Int = 4000,
    content: @Composable () -> Unit
) {
    val shimmerProgress by rememberInfiniteTransition().animateFloat(
        initialValue = 0.0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            tween(durationMillis = duration, easing = FastOutSlowInEasing),
            RepeatMode.Restart
        )
    )

    BoxWithConstraints(
        modifier = modifier
    ) {
        val shimmerWidth = constraints.maxWidth * 0.20f
        val shimmerAnimation = -shimmerWidth + (constraints.maxWidth + (2 * shimmerWidth)) * shimmerProgress

        val shimmerColorShades = listOf(
            Color.Unspecified,
            Color.White.copy(0.5f),
            Color.Unspecified,
        )

        val brush = Brush.linearGradient(
            colors = shimmerColorShades,
            start = Offset(shimmerAnimation, shimmerAnimation),
            end = Offset(shimmerAnimation + shimmerWidth, shimmerAnimation + shimmerWidth),
            tileMode = TileMode.Decal
        )

        Box(
            modifier = Modifier.background(brush = brush)
        ) {
            content()
        }
    }
}
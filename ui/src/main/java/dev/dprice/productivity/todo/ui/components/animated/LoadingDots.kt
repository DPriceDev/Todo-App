package dev.dprice.productivity.todo.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.dprice.productivity.todo.ui.theme.TodoAppTheme

@Composable
fun LoadingDots(
    modifier: Modifier = Modifier,
    diameter: Dp = 12.dp,
    spacing: Dp = 6.dp,
    colour: Color = MaterialTheme.colors.background,
    duration: Int = 1000
) {
    val infinite = rememberInfiniteTransition()

    val scales = (0 until 3).map {
        infinite.animateFloat(
            initialValue = 0.0f,
            targetValue = 1.0f,
            animationSpec = infiniteRepeatable(
                initialStartOffset = StartOffset(
                    (duration * 0.3f * it).toInt()
                ),
                animation = keyframes {
                    durationMillis = duration
                    0.0f at (duration * 0.0f).toInt()
                    1.0f at (duration * 0.2f).toInt()
                    0.0f at (duration * 1.0f).toInt()
                }
            )
        )
    }

    with(LocalDensity.current) {
        Canvas(
            modifier = Modifier
                .size(height = diameter, width = (diameter * 3) + (spacing * 2))
                .then(modifier)
        ) {
            scales.forEachIndexed { index, scale ->

                translate(
                    left = (index - 1) * (diameter.toPx() + spacing.toPx())
                ) {
                    dot(
                        scale = scale.value,
                        radius = diameter.toPx() / 2f,
                        colour = colour
                    )
                }
            }
        }
    }
}

private fun DrawScope.dot(
    scale: Float,
    radius: Float,
    colour: Color
) {
    scale(
        scale = scale,
        pivot = Offset(radius * 4, radius)
    ) {
        drawCircle(
            color = colour,
            radius = radius
        )
    }
}

@Preview
@Composable
private fun PreviewLoadingDots() {
    TodoAppTheme {
        LoadingDots()
    }
}
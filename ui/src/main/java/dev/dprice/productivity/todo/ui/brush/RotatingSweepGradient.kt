package dev.dprice.productivity.todo.ui.brush

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import dev.dprice.productivity.todo.ui.theme.MediumBlue
import kotlin.math.cos

fun rotatingSweepGradient(
    progress: Float,
    size: Size
) : Brush {
    return Brush.sweepGradient(
        0.0f to MediumBlue.copy(alpha = 1f - progress),
        0.0f + progress to MediumBlue,
        0.0f + progress to Color.Unspecified,
        1.0f to MediumBlue.copy(alpha = 1f - progress),
        center = Offset(
            size.width * (0.5f + (cos(progress * 3.14f * 2f) * 0.25f)),
            size.height / 2f
        )
    )
}
package dev.dprice.productivity.todo.ui.paths

import androidx.compose.ui.graphics.Path

fun Path.relativeSinWaveTo(
    dx: Float,
    height: Float
) {
    relativeQuadraticBezierTo(
        dx / 4,
        height,
        dx / 2,
        0f
    )

    relativeQuadraticBezierTo(
        dx / 4,
        -height,
        dx / 2,
        0f
    )
}
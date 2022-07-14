package dev.dprice.productivity.todo.ui.shapes

import androidx.compose.foundation.shape.GenericShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp

@Composable
fun TabShape(
    cornerRadius: Dp
) : Shape {
    val pixelRadius = with(LocalDensity.current) { cornerRadius.toPx() }
    return GenericShape { size, _ ->
        moveTo(0f, size.height)

        val insideWidth = size.width - (2 * pixelRadius)
        val cornerArcSize = Size(2 * pixelRadius, 2 * pixelRadius)

        arcTo(
            rect = Rect(
                Offset(-pixelRadius, size.height - (2 * pixelRadius)),
                cornerArcSize
            ),
            startAngleDegrees = 90f,
            sweepAngleDegrees = -90f,
            forceMoveTo = false
        )
        lineTo(pixelRadius, insideWidth / 2)

        arcTo(
            rect = Rect(
                Offset(pixelRadius, 0f),
                Size(insideWidth, insideWidth)
            ),
            startAngleDegrees = 180f,
            sweepAngleDegrees = 180f,
            forceMoveTo = false
        )

        lineTo(size.width - pixelRadius, size.height - pixelRadius)

        arcTo(
            rect = Rect(
                Offset(size.width - pixelRadius, size.height - (2 * pixelRadius)),
                cornerArcSize
            ),
            startAngleDegrees = 180f,
            sweepAngleDegrees = -90f,
            forceMoveTo = false
        )
    }
}
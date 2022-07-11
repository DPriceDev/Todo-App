package dev.dprice.productivity.todo.features.tasks.screens.add.group

import androidx.compose.foundation.shape.GenericShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun OffsetCircle(
    radius: Dp,
    offsetX: Dp = 0.dp,
    offsetY: Dp = 0.dp
) : Shape {
    val radiusInPixels = with(LocalDensity.current) { radius.toPx() }
    val origin = with(LocalDensity.current) {
        Offset(offsetX.toPx(), offsetY.toPx())
    }
    return GenericShape() { size, _ ->
        arcTo(
            Rect(
                (Offset(size.width / 2, size.height / 2) + origin) - Offset(radiusInPixels, radiusInPixels),
                Size(radiusInPixels * 2, radiusInPixels * 2)
            ),
            0f,
            359f,
            forceMoveTo = true
        )
    }
}
package dev.dprice.productivity.todo.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.MoreHoriz
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.vector.VectorPainter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import dev.dprice.productivity.todo.ui.theme.completeColour
import dev.dprice.productivity.todo.ui.theme.inProgressColour
import dev.dprice.productivity.todo.ui.theme.incompleteColour

@Composable
fun SpinningCircles(
    modifier: Modifier = Modifier,
    state: SpinningCirclesState = remember { SpinningCirclesState() },
) {
    LaunchedEffect(key1 = state) {
        state.initialize()
    }

    BoxWithConstraints(
        modifier = modifier
    ) {
        val crossPainter = rememberVectorPainter(image = Icons.Rounded.Clear)
        val progressPainter = rememberVectorPainter(image = Icons.Rounded.MoreHoriz)
        val completePainter = rememberVectorPainter(image = Icons.Rounded.Check)

        val diameter = constraints.maxWidth / 4.5f
        val width = constraints.maxWidth - diameter
        val height = constraints.maxHeight - diameter

        Canvas(
            modifier = Modifier.fillMaxSize()
        ) {
            state.getCircles().forEach { circle ->
                translate(
                    top = width * circle.y,
                    left = height * circle.x
                ) {
                    rotate(
                        degrees = circle.getRotation().value,
                        pivot = Offset(diameter / 3, diameter / 3)
                    ) {
                        scale(
                            scale = circle.getScale().value * circle.getInitialScale().value,
                            pivot = Offset(diameter / 2, diameter / 2)
                        ) {
                            circleIcon(
                                diameter = diameter,
                                painter = when (circle.type) {
                                    SpinningCirclesState.Type.CROSS -> crossPainter
                                    SpinningCirclesState.Type.PROGRESS -> progressPainter
                                    SpinningCirclesState.Type.COMPLETE -> completePainter
                                },
                                backgroundColour = when (circle.type) {
                                    SpinningCirclesState.Type.CROSS -> incompleteColour
                                    SpinningCirclesState.Type.PROGRESS -> inProgressColour
                                    SpinningCirclesState.Type.COMPLETE -> completeColour
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

private fun DrawScope.circleIcon(
    painter: VectorPainter,
    backgroundColour: Color,
    diameter: Float
) {
    val radius = diameter / 2
    drawCircle(
        color = backgroundColour,
        radius = radius,
        center = Offset(radius, radius)
    )

    val iconSize = diameter * 0.75f
    val offset = diameter * 0.125f

    translate(
        left = offset,
        top = offset
    ) {
        with(painter) {
            draw(
                size = Size(iconSize, iconSize),
                colorFilter = ColorFilter.tint(
                    color = Color.White
                )
            )
        }

    }
}
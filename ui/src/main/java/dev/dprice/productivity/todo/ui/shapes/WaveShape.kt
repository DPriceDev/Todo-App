package dev.dprice.productivity.todo.ui.shapes

import androidx.compose.foundation.shape.GenericShape
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Path
import dev.dprice.productivity.todo.ui.paths.relativeSinWaveTo
import kotlin.math.ceil
import kotlin.math.roundToInt

fun waveToppedShape(
    height: Float,
    frequency: Float = 1f,
    offset: Float = 0f
) = GenericShape { size, _ ->
    waveShape(height, frequency, offset, height / 2, size.height, size)
}

fun waveBottomedShape(
    height: Float,
    frequency: Float = 1f,
    offset: Float = 0f
) = GenericShape { size, _ ->
    waveShape(height, frequency, offset, size.height - (height / 2), 0f, size)
}

private fun Path.waveShape(
    height: Float,
    frequency: Float = 1f,
    offset: Float = 0f,
    startHeight: Float,
    firstCornerY: Float,
    size: Size
) {
    val waveWidth = size.width / frequency

    moveTo(-(size.width / frequency) * offset, startHeight)
    drawSinWave(frequency, waveWidth, height)

    relativeSinWaveTo(waveWidth, height)
    lineTo(size.width, firstCornerY)
    lineTo(0f, firstCornerY)
    close()
}

private fun Path.drawSinWave(
    frequency: Float,
    waveWidth: Float,
    height: Float
) {
    repeat(ceil(frequency).roundToInt()) {
        relativeSinWaveTo(waveWidth, height)
    }
}
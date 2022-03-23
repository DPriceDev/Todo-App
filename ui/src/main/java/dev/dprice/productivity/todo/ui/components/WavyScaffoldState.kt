package dev.dprice.productivity.todo.ui.components

import androidx.compose.animation.core.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.floor

data class WavyScaffoldState(
    val duration: Int = 500,
    var targetPosition: MutableState<Dp> = mutableStateOf(0.dp),
    var targetFrequency: MutableState<Float> = mutableStateOf(1f),
    var targetHeight: MutableState<Dp> = mutableStateOf(0.dp),
    var waveDuration: MutableState<Int> = mutableStateOf(15_000),
) {
    private val offsetTarget = mutableStateOf(0.0f)

    @Composable
    fun animatedPosition() = animateDpAsState(
        targetValue = targetPosition.value,
        animationSpec = tween(durationMillis = duration)
    )

    @Composable
    fun animatedFrequency() = animateFloatAsState(
        targetValue = targetFrequency.value,
        animationSpec = tween(durationMillis = duration)
    )

    @Composable
    fun animatedHeight() = animateDpAsState(
        targetValue = targetHeight.value,
        animationSpec = tween(durationMillis = duration)
    )

    @Composable
    fun animatedOffset() : Float {
        val offset = animateFloatAsState(
            targetValue = offsetTarget.value,
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = waveDuration.value,
                    easing = LinearEasing
                )
            )
        )

        LaunchedEffect(key1 = waveDuration.value) {
            offsetTarget.value = offset.value + 1f
        }

        return offset.value - floor(offset.value)
    }

    @Composable
    fun animatedConfig() = Config(
        animatedPosition().value,
        animatedHeight().value,
        animatedFrequency().value,
        animatedOffset(),
    )

    data class Config(
        val backDropHeight: Dp,
        val waveHeight: Dp,
        val waveFrequency: Float,
        val waveOffsetPercent: Float,
    )
}
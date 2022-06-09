package dev.dprice.productivity.todo.ui.components

import androidx.compose.animation.core.*
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlin.math.floor

data class WavyScaffoldState(
    private val animationDuration: Int = 500,
    private val initialBackDropHeight: Dp = 0.dp,
    private val initialFrequency: Float = 1f,
    private val initialPhase: Float = 0f,
    private val initialWaveHeight: Dp = 0.dp,
    private val initialDuration: Int = 15_000,
) {
    private val animatableBackDropHeight = Animatable(initialBackDropHeight, Dp.VectorConverter)
    private val animatableFrequency = Animatable(initialFrequency)
    private val animatableHeight = Animatable(initialWaveHeight, Dp.VectorConverter)
    private val animatablePhase = Animatable(initialPhase)
    private val animatableDuration = Animatable(initialDuration, Int.VectorConverter)

    fun getPhase() : State<Float> {
        val phase = animatablePhase.asState()
        return mutableStateOf(phase.value - floor(phase.value))
    }

    fun getBackDropHeight() = animatableBackDropHeight.asState()

    fun getFrequency() = animatableFrequency.asState()

    fun getWaveHeight() = animatableHeight.asState()

    suspend fun animate(
        backDropHeight: Dp = animatableBackDropHeight.targetValue,
        frequency: Float = animatableFrequency.targetValue,
        waveHeight: Dp = animatableHeight.targetValue,
        duration: Int = animatableDuration.targetValue
    ) = coroutineScope {

        launch { animatableBackDropHeight.animateTo(backDropHeight, getAnimationSpec()) }
        launch { animatableFrequency.animateTo(frequency, getAnimationSpec()) }
        launch { animatableHeight.animateTo(waveHeight, getAnimationSpec()) }
        launch { animatableDuration.animateTo(duration, getAnimationSpec()) }

        launch {
            animatablePhase.animateTo(
                animatablePhase.value + 1f,
                infiniteRepeatable(
                    tween(
                        animatableDuration.value,
                        easing = LinearEasing
                    )
                )
            )
        }
    }

    private fun <T> getAnimationSpec() = tween<T>(durationMillis = animationDuration)
}

class WavyScaffoldStateProvider : PreviewParameterProvider<WavyScaffoldState> {
    override val values = sequenceOf(
        WavyScaffoldState(
            initialBackDropHeight = 128.dp,
            initialWaveHeight = 128.dp,
            initialFrequency = 0.3f
        )
    )
}
package dev.dprice.productivity.todo.ui.components

import androidx.compose.animation.core.*
import androidx.compose.runtime.State
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class SpinningCirclesState {

    suspend fun initialize() = coroutineScope {
        circles.forEach { circle ->
            launch { circle.start() }
        }
    }

    fun getCircles(): List<Circle> = circles

    enum class Type {
        CROSS,
        PROGRESS,
        COMPLETE,
    }

    enum class RotationDirection {
        CW,
        CCW
    }

    data class Circle(
        val x: Float,
        val y: Float,
        private val initialScaleDelay: Int,
        private val initialRotation: Float,
        private val rotationDirection: RotationDirection,
        val type: Type,
    ) {
        private val animatableRotation = Animatable(0.0f)
        private val animatableInitialScale = Animatable(0.0f)
        private val animatableScale = Animatable(0.8f)

        suspend fun start() = coroutineScope {
            launch { animateRotation(rotationDirection) }
            launch { animateScale(initialScaleDelay + 300) }
            launch { animateInitialScale(initialScaleDelay) }
        }

        private suspend fun animateInitialScale(delay: Int) {
            animatableInitialScale.animateTo(
                targetValue = 1.0f,
                animationSpec = tween(
                    durationMillis = 300,
                    delayMillis = delay,
                    easing = LinearEasing
                ),
            )
        }

        private suspend fun animateScale(delay: Int) {
            animatableScale.animateTo(0.75f, animationSpec = tween(0))
            animatableScale.animateTo(
                targetValue = 1.0f,
                animationSpec = infiniteRepeatable(
                    animation = tween(
                        durationMillis = 2000,
                        delayMillis = delay
                    ),
                    repeatMode = RepeatMode.Reverse
                )
            )
        }

        private suspend fun animateRotation(direction: RotationDirection) {
            animatableRotation.animateTo(0.0f, animationSpec = tween(0))
            animatableRotation.animateTo(
                targetValue = when(direction) {
                    RotationDirection.CW -> 360f
                    RotationDirection.CCW -> -360f
                },
                animationSpec = infiniteRepeatable(
                    animation = tween(
                        durationMillis = 15_000,
                        easing = LinearEasing
                    )
                )
            )
        }

        fun getRotation() : State<Float> = animatableRotation.asState()

        fun getScale() : State<Float> = animatableScale.asState()

        fun getInitialScale() : State<Float> = animatableInitialScale.asState()
    }

    companion object {
        private val circles = listOf(
            Circle(-0.1f, -0.1f, 700, 90f, RotationDirection.CW, Type.CROSS),
            Circle(0.5f, 0.05f, 800, 20f, RotationDirection.CCW, Type.PROGRESS),
            Circle(0.9f, 0.2f, 400,  140f, RotationDirection.CCW, Type.COMPLETE),
            Circle(-0.14f, 0.3f, 0,  60f, RotationDirection.CW, Type.CROSS),
            Circle(0.4f, 0.35f, 650,  20f, RotationDirection.CCW, Type.CROSS),
            Circle(0.7f, 0.5f, 550, 110f, RotationDirection.CW, Type.PROGRESS),
            Circle(0.2f, 0.6f, 200,  40f, RotationDirection.CW, Type.COMPLETE),
            Circle(1.1f, 0.7f, 0,  20f, RotationDirection.CW, Type.CROSS),
            Circle(0.8f, 0.8f, 300,  150f, RotationDirection.CCW, Type.COMPLETE),
            Circle(0.45f, 0.9f, 750,  30f, RotationDirection.CCW, Type.PROGRESS),
            Circle(0.8f, 1.15f, 600,  70f, RotationDirection.CW, Type.CROSS),
            Circle(0.1f, 1.05f, 0,  160f, RotationDirection.CCW, Type.COMPLETE),
        )
    }
}
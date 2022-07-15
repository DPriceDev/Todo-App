package dev.dprice.productivity.todo.ui.components.buttons

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import dev.dprice.productivity.todo.ui.components.animated.PulsingLayout

// todo: convert pulsing to modifier?
@Composable
fun PulsingButton(
    backgroundColour: Color,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    durationMillis: Int = 3000,
    pulseRange: ClosedRange<Float> = 0.98f..1.0f,
    onClick: () -> Unit,
    content: @Composable () -> Unit
) {
    PulsingLayout(
        modifier = modifier,
        durationMillis = durationMillis,
        pulseRange = pulseRange
    ) {
        RoundedButton(
            colors = ButtonDefaults.buttonColors(
                backgroundColor = backgroundColour
            ),
            onClick = { onClick() },
            enabled = enabled,
            contentPadding = PaddingValues()
        ) {
            content()
        }
    }
}
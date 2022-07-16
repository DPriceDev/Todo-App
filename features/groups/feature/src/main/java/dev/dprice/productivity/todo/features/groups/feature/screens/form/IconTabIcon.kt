package dev.dprice.productivity.todo.features.groups.feature.screens.form

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.dprice.productivity.todo.ui.theme.TodoAppTheme
import dev.dprice.productivity.todo.ui.theme.Yellow

@Composable
fun IconTabIcon(
    icon: ImageVector?,
    modifier: Modifier = Modifier,
    iconColour: Color = Color.White,
    borderColour: Color = Yellow
) {
    val scale by rememberInfiniteTransition().animateFloat(
        initialValue = 1f,
        targetValue = 0.98f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000)
        )
    )

    Column(
        modifier = Modifier
            .padding(start = 8.dp, end = 8.dp, bottom = 8.dp)
            .then(modifier),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Surface(
            color = Color.Unspecified,
            shape = CircleShape,
            border = BorderStroke(
                width = 2.dp,
                color = borderColour
            )
        ) {
            Icon(
                icon ?: Icons.Default.Close,
                contentDescription = null,
                tint = if (icon != null) iconColour else Color.Red,
                modifier = Modifier
                    .size(48.dp)
                    .padding(8.dp)
                    .scale(scale)
            )
        }
    }
}

/**
 * Preview
 */

@Preview
@Composable
private fun PreviewIconPickerRow() {
    TodoAppTheme {
        IconTabIcon(icon = Icons.Default.Edit)
    }
}
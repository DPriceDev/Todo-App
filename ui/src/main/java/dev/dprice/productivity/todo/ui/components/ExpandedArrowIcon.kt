package dev.dprice.productivity.todo.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate

@Composable
fun ExpandedArrowIcon(
    isSelected: Boolean,
    modifier: Modifier = Modifier
) {
    val targetRotation = if (isSelected) 180f else 0f
    val rotation: Float by animateFloatAsState(
        targetValue = targetRotation,
        animationSpec = tween()
    )
    Icon(
        Icons.Outlined.ArrowDropDown,
        null,
        Modifier
            .rotate(rotation)
            .then(modifier)
    )
}
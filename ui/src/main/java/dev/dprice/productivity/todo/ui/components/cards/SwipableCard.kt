package dev.dprice.productivity.todo.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import dev.dprice.productivity.todo.ui.theme.MediumBlue
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SwipeableCard(
    onClick: () -> Unit,
    anchors: Map<Float, Boolean>,
    modifier: Modifier = Modifier,
    swipeState: SwipeableState<Boolean> = rememberSwipeableState(initialValue = false),
    swipingEnabled: Boolean = true,
    enabled: Boolean = true,
    shape: Shape = RoundedCornerShape(8.dp),
    backgroundColor: Color = MediumBlue,
    contentColor: Color = Color.White,
    border: BorderStroke? = null,
    elevation: Dp = 8.dp,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable () -> Unit
) {
    Card(
        elevation = elevation,
        backgroundColor = backgroundColor,
        contentColor = contentColor,
        enabled = enabled,
        shape = shape,
        modifier = Modifier
            .padding(4.dp)
            .offset { IntOffset(swipeState.offset.value.roundToInt(), 0) }
            .swipeable(
                state = swipeState,
                orientation = Orientation.Horizontal,
                thresholds = { _, _ -> FractionalThreshold(0.75f) },
                enabled = swipingEnabled,
                anchors = anchors
            )
            .then(modifier),
        border = border,
        interactionSource = interactionSource,
        onClick = onClick,
        content = content
    )
}
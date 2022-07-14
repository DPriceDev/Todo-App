package dev.dprice.productivity.todo.ui.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.dprice.productivity.todo.ui.shapes.OffsetCircle
import dev.dprice.productivity.todo.ui.shapes.TabShape
import dev.dprice.productivity.todo.ui.theme.Yellow
import kotlin.math.sqrt

@Composable
fun <T> TabPager(
    items: List<T>,
    selected: Int?,
    modifier: Modifier = Modifier,
    colour: Color = Yellow,
    selectorShape: Shape = RoundedCornerShape(32.dp),
    tabShape: Shape = TabShape(cornerRadius = 16.dp),
    tabContent: @Composable (T, Boolean) -> Unit,
    selectorContent: @Composable (T) -> Unit,
    onSelect: (Int) -> Unit
) {
    BoxWithConstraints {
        val originHeight = 32f
        val expandWidth = (this@BoxWithConstraints.maxWidth.value / 4)
        val expandHeight = this@BoxWithConstraints.maxHeight.value + originHeight
        val maxRadius = sqrt((expandWidth * expandWidth) + (expandHeight * expandHeight))

        Column(
            modifier = modifier
        ) {
            Tabs(
                items = items,
                selected = selected,
                shape = tabShape,
                colour = colour,
                onSelect = onSelect,
                content = tabContent
            )

            val startX = -(this@BoxWithConstraints.maxWidth / 4)
            val offsetX = startX + (this@BoxWithConstraints.maxWidth / 2) // * (state.selectedIndex ?: 0)

            var lastSelected: Int? by remember { mutableStateOf(selected) }

            LaunchedEffect(key1 = selected) {
                selected?.let { lastSelected = it }
            }

            SelectionBox(
                isExpanded = selected != null,
                colour = colour,
                shape = selectorShape,
                maxRadius = maxRadius.dp,
                offsetX = offsetX,
                offsetY = (-originHeight).dp,
                content = {
                    lastSelected?.let { index -> items.getOrNull(index)?.let { selectorContent(it) } }
                }
            )
        }
    }
}

@Composable
private fun <T> Tabs(
    items: List<T>,
    selected: Int?,
    shape: Shape,
    colour: Color,
    onSelect: (Int) -> Unit,
    content: @Composable (T, Boolean) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        items.forEachIndexed { index, item ->
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.Center,
            ) {
                Tab(
                    isSelected = index == selected,
                    shape = shape,
                    colour = colour,
                    onClick = { onSelect(index) },
                    content = { content(item, it) }
                )
            }
        }
    }
}

@Composable
private fun Tab(
    isSelected: Boolean,
    modifier: Modifier = Modifier,
    shape: Shape = TabShape(16.dp),
    colour: Color = Yellow,
    onClick: () -> Unit,
    content: @Composable (isSelected: Boolean) -> Unit
) {
    BoxWithConstraints(
        modifier = Modifier
            .clip(shape)
            .clickable { onClick() }
            .then(modifier)
    ) {
        val radius by animateDpAsState(
            targetValue = if (isSelected) {
                Size(
                    constraints.maxWidth.toFloat(),
                    constraints.maxHeight.toFloat()
                ).extentRadius.dp
            } else {
                0.dp
            },
            animationSpec = tween(durationMillis = 3000)
        )

        content(isSelected = false)

        Surface(
            modifier = Modifier.clip(
                OffsetCircle(
                    radius = radius,
                    offsetY = 0.dp
                )
            ),
            shape = shape,
            color = colour
        ) {
            content(isSelected = true)
        }
    }
}

@Composable
private fun SelectionBox(
    isExpanded: Boolean,
    colour: Color,
    shape: Shape,
    maxRadius: Dp,
    offsetX: Dp,
    offsetY: Dp,
    duration: Int = 3000,
    content: @Composable () -> Unit
) {
    var maxHeight by remember { mutableStateOf(0) }

    val height by animateIntAsState(
        targetValue = if (isExpanded) maxHeight else 0,
        animationSpec = tween(durationMillis = duration)
    )

    val radius by animateDpAsState(
        targetValue = if (isExpanded) maxRadius else 0.dp,
        animationSpec = tween(durationMillis = duration)
    )

    SubcomposeLayout(
        modifier = Modifier.clipToBounds()
    ) { constraints ->

        val looseConstraints = constraints.copy(minWidth = 0, minHeight = 0)
        val measurable = subcompose(Unit) {
            BoxWithConstraints {
                Surface(
                    color = colour,
                    shape = shape,
                    modifier = Modifier
                        .clip(
                            OffsetCircle(
                                radius = radius,
                                offsetX = offsetX,
                                offsetY = offsetY - (this.maxHeight / 2)
                            )
                        ),
                    content = content
                )
            }
        }

        val placeable = measurable.first().measure(looseConstraints)

        maxHeight = placeable.height

        layout(placeable.width, height) { placeable.place(0, 0) }
    }
}

val Size.extentRadius
    get() = sqrt((height * height) + (width * width)) / 2
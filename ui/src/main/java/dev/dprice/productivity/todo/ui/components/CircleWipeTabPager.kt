package dev.dprice.productivity.todo.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.dprice.productivity.todo.ui.components.scaffold.TabPagerScaffold
import dev.dprice.productivity.todo.ui.shapes.OffsetCircle
import kotlin.math.sqrt

@Composable
fun <T> CircleWipeTabPager(
    items: List<T>,
    selected: T?,
    modifier: Modifier = Modifier,
    duration: Int = 800,
    tabOriginOffset: Dp = 0.dp,
    tabContent: @Composable (T, Boolean) -> Unit,
    dropdownContent: @Composable (T) -> Unit
) {
    BoxWithConstraints {

        var isExpanded by remember { mutableStateOf(false) }
        var lastIndex by remember { mutableStateOf(1) }
        var lastSelected: T? by remember { mutableStateOf(null) }
        LaunchedEffect(key1 = selected) {
            if (selected != null) {
                lastIndex = items.indexOf(selected) + 1
                lastSelected = selected
            } else {
                isExpanded = false
            }
        }

        val radiusAnimation = remember { Animatable(0.dp, Dp.VectorConverter) }

        TabPagerScaffold(
            items = items,
            selected = selected,
            animationSpec = tween(
                durationMillis = if (selected == null) (duration * 0.8f).toInt() else (duration * 0.6f).toInt(),
                easing = if (selected == null) CubicBezierEasing(1f, 0f, 0.54f, 1f) else FastOutSlowInEasing
            ),
            modifier = modifier,
            tabContent = { item, isSelected, height, _ ->
                Tab(
                    isSelected = isSelected,
                    isExpanded = isExpanded,
                    duration = duration,
                    radius = if (item == lastSelected) radiusAnimation.value else 0.dp,
                    originOffset = tabOriginOffset,
                    height = height
                ) {
                    tabContent(item, it)
                }
            },
            dropdownContent = { item, height, tabHeight ->
                val tabWidth = maxWidth.value / (items.count() + 1)
                val expandWidth = tabWidth * lastIndex
                val offsetX = expandWidth - ((maxWidth.value) / 2)


                val originX = if (offsetX > 0) offsetX + 16 else offsetX - 16

                val totalHeight = height + tabHeight
                val expandHeight = with(LocalDensity.current) { totalHeight.toDp().value } + tabOriginOffset.value
                val maxRadius = sqrt((expandWidth * expandWidth) + (expandHeight * expandHeight))

                LaunchedEffect(key1 = selected, key2 = height) {
                    if (selected != null && !isExpanded && radiusAnimation.targetValue != maxRadius.dp) {
                        radiusAnimation.animateTo(
                            targetValue = maxRadius.dp,
                            animationSpec = tween(durationMillis = duration)
                        )
                        isExpanded = true
                    } else if (selected == null && radiusAnimation.targetValue != 0.dp) {
                        radiusAnimation.animateTo(
                            targetValue = 0.dp,
                            animationSpec = tween(durationMillis = duration)
                        )
                        isExpanded = false
                    }
                }

                val originY = (tabHeight / 2) - with(LocalDensity.current) { tabOriginOffset.toPx().toInt() }

                DropDown(
                    item = item,
                    radius = radiusAnimation.value,
                    originX = originX,
                    originY = originY,
                    content = dropdownContent,
                    expandHeight = height
                )
            }
        )
    }
}

@Composable
private fun Tab(
    isSelected: Boolean,
    isExpanded: Boolean,
    duration: Int,
    originOffset: Dp,
    radius: Dp,
    height: Int,
    content: @Composable (isSelected: Boolean) -> Unit
) {
    val tabRadius = with(LocalDensity.current) { (height / 2).dp }

    Box {
        val builtRadius by animateDpAsState(
            targetValue = if (isSelected) tabRadius else 0.dp,
            animationSpec = tween(durationMillis = (duration * 0.5f).toInt())
        )

        content(isSelected = false)

        Box(
            modifier = Modifier
                .clip(
                    OffsetCircle(
                        radius = if (isExpanded) builtRadius else radius,
                        offsetY = originOffset
                    )
                )
        ) {
            content(isSelected = true)
        }
    }
}

@Composable
private fun <T> DropDown(
    item: T,
    radius: Dp,
    originX: Float,
    originY: Int,
    expandHeight: Int,
    content: @Composable (T) -> Unit
) {
    val height = with(LocalDensity.current) { expandHeight.toDp() }
    Box(
        modifier = Modifier.clip(
            OffsetCircle(
                radius = radius,
                offsetX = originX.dp,
                offsetY = -(height / 2) - with(LocalDensity.current) { originY.toDp() }
            )
        )
    ) {
        content(item)
    }
}
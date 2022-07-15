package dev.dprice.productivity.todo.ui.components

import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import dev.dprice.productivity.todo.ui.components.scaffold.TabPagerScaffold
import dev.dprice.productivity.todo.ui.shapes.OffsetCircle
import kotlin.math.sqrt

// todo: animate duration multiplied by radius divided by height to match both animations
@Composable
fun <T> CircleWipeTabPager(
    items: List<T>,
    selected: T?,
    modifier: Modifier = Modifier,
    duration: Int = 800,
    tabOriginOffset: Dp = 0.dp,
    tabContent: @Composable (T, Boolean) -> Offset,
    dropdownContent: @Composable (T) -> Unit
) {
    BoxWithConstraints {
        val overallHeight = constraints.maxHeight
        val tabWidth = maxWidth.value / (items.count() + 1)

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

        val expandWidth = tabWidth * lastIndex
        val expandHeight = this@BoxWithConstraints.maxHeight.value + tabOriginOffset.value
        val maxRadius = sqrt((expandWidth * expandWidth) + (expandHeight * expandHeight))

        val offsetX = expandWidth - ((maxWidth.value) / 2)
        val originX = if (offsetX > 0) offsetX + 16 else offsetX - 16

        val radius by animateDpAsState(
            targetValue = selected?.let { maxRadius.dp } ?: 0.dp,
            animationSpec = tween(durationMillis = duration),
            finishedListener = { isExpanded = it > 0.dp }
        )

        TabPagerScaffold(
            items = items,
            selected = selected,
            animationSpec = tween(
                durationMillis = if (selected == null) (duration * 0.8f).toInt() else (duration * 0.6f).toInt(),
                easing = if (selected == null) CubicBezierEasing(1f, 0f, 0.54f, 1f) else FastOutSlowInEasing
            ),
            modifier = modifier,
            tabContent = { item, isSelected ->
                Tab(
                    isSelected = isSelected,
                    isExpanded = isExpanded,
                    duration = duration,
                    radius = if (item == lastSelected) radius else 0.dp,
                    originOffset = tabOriginOffset
                ) {
                    tabContent(item, it)
                }
            },
            dropdownContent = { item ->
                BoxWithConstraints {
                    val tabHeight = overallHeight - constraints.maxHeight
                    val originY = (tabHeight / 2) - with(LocalDensity.current) { tabOriginOffset.toPx().toInt() }

                    DropDown(
                        item = item,
                        radius = radius,
                        originX = originX,
                        originY = originY,
                        content = dropdownContent
                    )
                }
            }
        )
    }
}

@Composable
private fun <T> DropDown(
    item: T,
    radius: Dp,
    originX: Float,
    originY: Int,
    content: @Composable (T) -> Unit
) {
    var size by remember { mutableStateOf(IntSize.Zero) }
    val height = with(LocalDensity.current) { size.height.toDp() }
    Box(
        modifier = Modifier
            .onGloballyPositioned { size = it.size }
            .clip(
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

@Composable
private fun Tab(
    isSelected: Boolean,
    isExpanded: Boolean,
    duration: Int,
    originOffset: Dp,
    radius: Dp,
    content: @Composable (isSelected: Boolean) -> Offset
) {
    var size by remember { mutableStateOf(IntSize.Zero) }
    val pixelRadius = with(size) { sqrt((height * height) + (width * width).toDouble()) / 2 }
    val tabRadius = with(LocalDensity.current) { pixelRadius.dp }

    Box(
        modifier = Modifier.onGloballyPositioned { size = it.size / 2 }
    ) {
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

val IntSize.extentRadius
    get() = sqrt((height * height) + (width * width).toDouble()) / 2
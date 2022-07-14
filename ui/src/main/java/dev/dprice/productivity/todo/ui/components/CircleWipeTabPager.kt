package dev.dprice.productivity.todo.ui.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
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
    duration: Int = 5000,
    tabContent: @Composable (T, Boolean) -> Offset,
    dropdownContent: @Composable (T) -> Unit
) {
    BoxWithConstraints {
        val overallHeight = constraints.maxHeight
        val tabWidth = maxWidth.value / (items.count() + 1)

        // Keeps the last index to know where to expand and shrink the circle from.
        var lastIndex by remember { mutableStateOf(1) }
        LaunchedEffect(key1 = selected) {
            selected?.let { lastIndex = items.indexOf(it) + 1 }
        }

        val expandWidth = tabWidth * lastIndex
        val expandHeight = this@BoxWithConstraints.maxHeight.value
        val maxRadius = sqrt((expandWidth * expandWidth) + (expandHeight * expandHeight))

        val originX = expandWidth - ((maxWidth.value - 32) / 2)



        val radius by animateDpAsState(
            targetValue = selected?.let { maxRadius.dp } ?: 0.dp,
            animationSpec = tween(durationMillis = duration)
        )

        TabPagerScaffold(
            items = items,
            selected = selected,
            expandDuration = duration,
            modifier = modifier,
            tabContent = { item, isSelected ->
                Tab(
                    isSelected = isSelected,
                    duration = duration,
                    maxRadius = selected?.let {
                        if (selected == item) maxRadius.dp else 0.dp // todo: need to think about this
                    } ?: maxRadius.dp
                ) {
                    tabContent(item, it)
                }
            },
            dropdownContent = { item ->
                BoxWithConstraints {

                    val tabHeight = overallHeight - constraints.maxHeight
                    val originY = (tabHeight / 2)

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
    modifier: Modifier = Modifier,
    content: @Composable (T) -> Unit
) {
    BoxWithConstraints(
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .clip(
                    OffsetCircle(
                        radius = radius,
                        offsetX = originX.dp,
                        offsetY = -(maxHeight / 2) - with(LocalDensity.current) { originY.toDp() }
                    )
                )
        ) {
            content(item)
        }
    }
}

@Composable
private fun Tab(
    isSelected: Boolean,
    duration: Int,
    maxRadius: Dp,
    content: @Composable (isSelected: Boolean) -> Offset
) {
    Box {
        val radius = animateDpAsState(
            targetValue = if (isSelected) {
                maxRadius
            } else {
                0.dp
            },
            animationSpec = tween(durationMillis = duration)
        )

//        LaunchedEffect(key1 = maxRadius) {
//            if (maxRadius > radius.value)
//        }

        content(isSelected = false)

        Box(
            modifier = Modifier
                .clip(
                    OffsetCircle(
                        radius = radius.value,
                        //offsetY = with(LocalDensity.current) { (size.height / 2).toDp() }
                    )
                )
        ) {
            content(isSelected = true)
        }
    }
}

val Size.extentRadius
    get() = sqrt((height * height) + (width * width)) / 2
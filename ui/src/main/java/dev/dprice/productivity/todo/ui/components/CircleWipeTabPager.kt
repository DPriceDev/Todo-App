package dev.dprice.productivity.todo.ui.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
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
    duration: Int = 500,
    tabContent: @Composable (T, Boolean) -> Offset,
    dropdownContent: @Composable (T) -> Unit
) {
    BoxWithConstraints {
        val originHeight = 32f
        val expandWidth = (this@BoxWithConstraints.maxWidth.value / 4)
        val expandHeight = this@BoxWithConstraints.maxHeight.value + originHeight
        val maxRadius = sqrt((expandWidth * expandWidth) + (expandHeight * expandHeight))

        TabPagerScaffold(
            items = items,
            selected = selected,
            modifier = modifier,
            tabContent = { item, isSelected ->
                Tab(
                    isSelected = isSelected,
                    duration = duration
                ) {
                    tabContent(item, it)
                }
            },
            dropdownContent = { item ->
                val radius by animateDpAsState(
                    targetValue = selected?.let { maxRadius.dp } ?: 0.dp,
                    animationSpec = tween(durationMillis = duration)
                )

                DropDown(
                    item = item,
                    radius = radius,
                    content = dropdownContent,
                )
            }
        )
    }
}

@Composable
private fun <T> DropDown(
    item: T,
    radius: Dp,
    content: @Composable (T) -> Unit
) {
    Box(
        modifier = Modifier
            .clip(
                OffsetCircle(
                    radius = radius,
                    offsetX = 0.dp, //todo: offsetX,
                    offsetY = 0.dp //todo: offsetY - (this.maxHeight / 2)
                )
            )
    ) {
        content(item)
    }
}

@Composable
private fun Tab(
    isSelected: Boolean,
    duration: Int,
    content: @Composable (isSelected: Boolean) -> Offset
) {
    BoxWithConstraints {
        val radius by animateDpAsState(
            targetValue = if (isSelected) {
                Size(
                    constraints.maxWidth.toFloat(),
                    constraints.maxHeight.toFloat()
                ).extentRadius.dp
            } else {
                0.dp
            },
            animationSpec = tween(durationMillis = duration)
        )

        val offset = content(isSelected = false)

        Box(
            modifier = Modifier.clip(
                OffsetCircle(
                    radius = radius,
                    offsetY = offset.y.dp,
                    offsetX = offset.x.dp
                )
            )
        ) {
            content(isSelected = true)
        }
    }
}

val Size.extentRadius
    get() = sqrt((height * height) + (width * width)) / 2
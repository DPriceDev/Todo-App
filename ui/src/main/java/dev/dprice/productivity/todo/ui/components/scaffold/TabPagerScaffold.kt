package dev.dprice.productivity.todo.ui.components.scaffold

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layoutId
import kotlinx.coroutines.launch

internal enum class LayoutId {
    TABS,
    DROPDOWN
}

@Composable
fun <T> TabPagerScaffold(
    items: List<T>,
    selected: T?,
    modifier: Modifier = Modifier,
    animationSpec: AnimationSpec<Int> = tween(durationMillis = 500),
    tabContent: @Composable (T, Boolean, Int, Int) -> Unit,
    dropdownContent: @Composable (T, Int, Int) -> Unit
) {
    var tabsHeight by remember { mutableStateOf(0) }
    var dropdownHeight by remember { mutableStateOf(0) }

    var lastSelected: T? by remember { mutableStateOf(selected) }
    LaunchedEffect(key1 = selected) {
        selected?.let { lastSelected = it }
    }

    Layout(
        modifier = modifier,
        content = {
            Tabs(
                items = items,
                selected = selected,
                content = { item, isSelected ->
                    tabContent(item, isSelected, tabsHeight, dropdownHeight)
                },
                modifier = Modifier.layoutId(LayoutId.TABS)
            )

            DropdownDrawer(
                isExpanded = selected != null,
                animationSpec = animationSpec,
                modifier = Modifier.layoutId(LayoutId.DROPDOWN),
                content = { height ->
                    lastSelected?.let { item -> dropdownContent(item, height, tabsHeight) }
                }
            )
        }
    ) { measurables, constraints ->
        val placeables = measurables.map { measurable ->
             measurable.measure(constraints).apply {
                when (measurable.layoutId as LayoutId) {
                    LayoutId.TABS -> tabsHeight = height
                    LayoutId.DROPDOWN -> dropdownHeight = height
                }
            }
        }

        val width = placeables.maxBy { it.width }.width
        val totalHeight = placeables.sumOf { it.height }

        layout(width, totalHeight) {
            placeables.first().place(0, 0)
            placeables[1].place(0, tabsHeight)
        }
    }
}

@Composable
private fun <T> Tabs(
    items: List<T>,
    selected: T?,
    modifier: Modifier = Modifier,
    content: @Composable (T, Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Spacer(modifier = Modifier.weight(1f))

        items.forEach { item ->
            Box(contentAlignment = Alignment.Center) {
                content(item, item == selected)
            }

            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

@Composable
private fun DropdownDrawer(
    isExpanded: Boolean,
    animationSpec: AnimationSpec<Int>,
    modifier: Modifier = Modifier,
    content: @Composable (Int) -> Unit
) {
    var dropdownHeight by remember { mutableStateOf(0) }

    val scope = rememberCoroutineScope()
    val animation: Animatable<Int, AnimationVector1D>? by remember {
        mutableStateOf(
            Animatable(0, Int.VectorConverter, 0)
        )
    }

    Layout(
        modifier = Modifier
            .clipToBounds()
            .then(modifier),
        content = { content(dropdownHeight) }
    ) { measurables, constraints ->
        val looseConstraints = constraints.copy(minWidth = 0, minHeight = 0)
        val placeable = measurables.firstOrNull()?.measure(looseConstraints)
        dropdownHeight = placeable?.height ?: 0

        when {
            isExpanded && animation?.targetValue != dropdownHeight -> scope.launch {
                animation?.animateTo(dropdownHeight, animationSpec)
            }
            !isExpanded && animation?.targetValue != 0 -> scope.launch {
                animation?.animateTo(0, animationSpec)
            }
        }

        layout(placeable?.width ?: 0, animation?.value ?: 0) {
            placeable?.place(0, 0)
        }
    }
}
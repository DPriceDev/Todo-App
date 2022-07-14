package dev.dprice.productivity.todo.ui.components.scaffold

import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.layout.SubcomposeLayout

@Composable
fun <T> TabPagerScaffold(
    items: List<T>,
    selected: T?,
    modifier: Modifier = Modifier,
    expandDuration: Int = 500,
    tabContent: @Composable (T, Boolean) -> Unit,
    dropdownContent: @Composable (T) -> Unit
) {
    Column(modifier = modifier) {
        Tabs(
            items = items,
            selected = selected,
            content = tabContent
        )

        // Keep the last selected tab for shrinking the tabs
        var lastSelected: T? by remember { mutableStateOf(selected) }
        LaunchedEffect(key1 = selected) { selected?.let { lastSelected = it } }

        DropdownDrawer(
            isExpanded = selected != null,
            duration = expandDuration,
            content = {
                lastSelected?.let { item -> dropdownContent(item) }
            }
        )
    }
}

@Composable
private fun <T> Tabs(
    items: List<T>,
    selected: T?,
    content: @Composable (T, Boolean) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Spacer(modifier = Modifier.weight(1f))

        items.forEach { item ->
            Box(
                //modifier = Modifier.weight(1f),
                contentAlignment = Alignment.Center,
            ) {
                content(item, item == selected)
            }

            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

@Composable
private fun DropdownDrawer(
    isExpanded: Boolean,
    duration: Int,
    content: @Composable () -> Unit
) {
    var maxHeight by remember { mutableStateOf(0) }

    val height by animateIntAsState(
        targetValue = if (isExpanded) maxHeight else 0,
        animationSpec = tween(durationMillis = duration)
    )

    SubcomposeLayout(
        modifier = Modifier.clipToBounds()
    ) { constraints ->

        val looseConstraints = constraints.copy(minWidth = 0, minHeight = 0)
        val measurable = subcompose(Unit) { content() }

        val placeable = measurable.firstOrNull()?.measure(looseConstraints)

        maxHeight = placeable?.height ?: 0

        layout(placeable?.width ?: 0, height) { placeable?.place(0, 0) }
    }
}
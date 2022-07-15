package dev.dprice.productivity.todo.ui.components.scaffold

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.layout.SubcomposeLayout
import kotlinx.coroutines.launch

@Composable
fun <T> TabPagerScaffold(
    items: List<T>,
    selected: T?,
    modifier: Modifier = Modifier,
    animationSpec: AnimationSpec<Int> = tween(durationMillis = 500),
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
            animationSpec = animationSpec,
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
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min),
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
    content: @Composable () -> Unit
) {
    val scope = rememberCoroutineScope()
    val animation: Animatable<Int, AnimationVector1D>? by remember {
        mutableStateOf(
            Animatable(0, Int.VectorConverter, 0)
        )
    }

    SubcomposeLayout(
        modifier = Modifier.clipToBounds()
    ) { constraints ->

        val looseConstraints = constraints.copy(minWidth = 0, minHeight = 0)
        val measurable = subcompose(Unit) { content() }

        val placeable = measurable.firstOrNull()?.measure(looseConstraints)

        val maxHeight = placeable?.height ?: 0

        if (isExpanded && animation?.targetValue != maxHeight) {
            scope.launch {
                animation?.animateTo(
                    maxHeight,
                    animationSpec
                )
            }
        }

        if (!isExpanded && animation?.targetValue != 0) {
            scope.launch {
                animation?.animateTo(
                    0,
                    animationSpec
                )
            }
        }

        layout(placeable?.width ?: 0, animation?.value ?: 0) {
            placeable?.place(0, 0)
        }
    }
}
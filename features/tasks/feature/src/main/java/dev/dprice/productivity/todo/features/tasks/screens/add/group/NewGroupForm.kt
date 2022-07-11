package dev.dprice.productivity.todo.features.tasks.screens.add.group

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.dprice.productivity.todo.features.tasks.screens.add.group.model.NewGroupAction
import dev.dprice.productivity.todo.features.tasks.screens.add.group.model.NewGroupState
import dev.dprice.productivity.todo.ui.components.*
import dev.dprice.productivity.todo.ui.theme.TodoAppTheme
import dev.dprice.productivity.todo.ui.theme.Yellow
import kotlin.math.sqrt

@Composable
fun NewGroupForm(
    form: NewGroupState,
    modifier: Modifier = Modifier,
    onAction: (NewGroupAction) -> Unit
) {
    val focusManager = LocalFocusManager.current
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Text(
            text = "Create a new group for your tasks! Assign it a title and an icon and colour to keep track of it easier.",
            style = MaterialTheme.typography.body1,
            textAlign = TextAlign.Center,
            color = Color.White
        )

        FormDivider(modifier = Modifier.padding(20.dp))

        RoundedEntryCard(
            entry = form.title,
            modifier = Modifier.onFocusChanged {
                onAction(NewGroupAction.UpdateTitleFocus(it.hasFocus))
            },
            onImeAction = { focusManager.moveFocus(FocusDirection.Down) },
            onTextChanged = { onAction(NewGroupAction.UpdateTitleText(it)) }
        )

        GroupSelector(form)

        FormDivider(modifier = Modifier.padding(20.dp))

        RoundedButton(
            text = "Create",
            buttonState = form.buttonState,
            modifier = Modifier.focusable(),
            onClick = { /* todo */ }
        )
    }
}

@Composable
private fun GroupSelector(form: NewGroupState) {
    BoxWithConstraints {
        var isExpanded: Boolean by remember { mutableStateOf(false) }

        val expandWidth = (this@BoxWithConstraints.maxWidth.value / 4) * 3
        val expandHeight = this@BoxWithConstraints.maxHeight.value + 56f
        val maxRadius = sqrt((expandWidth * expandWidth) + (expandHeight * expandHeight))

        val radius: Dp by animateDpAsState(
            targetValue = if (isExpanded) maxRadius.dp else 0.dp,
            animationSpec = tween(durationMillis = 600)
        )

        Column {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    ColourPickerRow(
                        colour = form.colour,
                        modifier = Modifier
                            .focusable(),
                        onClick = { /* todo */ }
                    )
                }



                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Surface(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .height(72.dp)
                            .clip(
                                OffsetCircle(
                                    radius = radius,
                                    offsetY = (-4).dp
                                )
                            )
                            .width(96.dp),
                        shape = TabShape(16.dp),
                        color = Yellow
                    ) {
                        
                    }

                    IconPickerRow(
                        icon = form.icon,
                        modifier = Modifier.focusable(),
                        onClick = { isExpanded = !isExpanded }
                    )
                }
            }

            val edgeOffset = (this@BoxWithConstraints.maxWidth / 4)
            AnimatedVisibility(
                visible = isExpanded,
                enter = expandVertically(
                    expandFrom = Alignment.Top,
                    animationSpec = tween(durationMillis = 400)
                ),
                exit = shrinkVertically(
                    shrinkTowards = Alignment.Top,
                    animationSpec = tween(durationMillis = 800)
                ),
            ) {
                BoxWithConstraints {
                    SelectorWindow(
                        modifier = Modifier.clip(
                            OffsetCircle(
                                radius = radius,
                                offsetY = (-56).dp - (maxHeight / 2),
                                offsetX = edgeOffset
                            )
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun SelectorWindow(
    modifier: Modifier = Modifier
) {
    Surface(
        color = Yellow,
        shape = RoundedCornerShape(32.dp),
        modifier = modifier
    ) {
        LazyHorizontalGrid(
            rows = GridCells.Fixed(3),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            items(20) {
                Icon(
                    Icons.Default.Edit,
                    null
                )
            }
        }
    }
}

/**
 * Preview
 */

@Preview
@Composable
private fun PreviewNewGroupForm() {
    TodoAppTheme {
        NewGroupForm(
            form = NewGroupState(),
            onAction = { }
        )
    }
}
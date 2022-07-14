package dev.dprice.productivity.todo.features.tasks.screens.add.group

import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.dprice.productivity.todo.features.tasks.screens.add.group.model.NewGroupAction
import dev.dprice.productivity.todo.features.tasks.screens.add.group.model.NewGroupState
import dev.dprice.productivity.todo.ui.components.ColourPickerRow
import dev.dprice.productivity.todo.ui.components.FormDivider
import dev.dprice.productivity.todo.ui.components.IconPickerRow
import dev.dprice.productivity.todo.ui.components.TabPager
import dev.dprice.productivity.todo.ui.components.buttons.RoundedButton
import dev.dprice.productivity.todo.ui.components.text.RoundedEntryCard
import dev.dprice.productivity.todo.ui.theme.TodoAppTheme
import dev.dprice.productivity.todo.ui.theme.Yellow

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

enum class GroupTab {
    ICON,
    COLOUR
}

@Composable
private fun GroupSelector(form: NewGroupState) {

    var selected: Int? by remember {
        mutableStateOf(null)
    }

    TabPager(
        selected = selected,
        items = GroupTab.values().toList(),
        //modifier = Modifier.onFocusChanged { if (!it.hasFocus) selected = null },
        tabContent = { tab, isSelected ->
            Box(
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 8.dp)
            ) {
                when (tab) {
                    GroupTab.ICON -> IconPickerRow(
                        icon = form.icon,
                        iconColour = if (isSelected) Color.Black else Color.White,
                        borderColour = if (isSelected) Color.Black else Yellow,
                    )

                    GroupTab.COLOUR -> ColourPickerRow(
                        colour = form.colour,
                        borderColour = if (isSelected) Color.Black else Yellow,
                    )
                }
            }
        },
        selectorContent = { tab ->
            when (tab) {
                GroupTab.ICON -> IconSelection()
                GroupTab.COLOUR -> ColourSelection()
            }
        },
        onSelect = {
            selected = if (selected == it) null else it
        }
    )
}

@Composable
fun IconSelection(
    modifier: Modifier = Modifier
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

@Composable
fun ColourSelection(
    modifier: Modifier = Modifier
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
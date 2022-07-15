package dev.dprice.productivity.todo.features.tasks.screens.add.group

import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.dprice.productivity.todo.features.tasks.screens.add.group.model.NewGroupAction
import dev.dprice.productivity.todo.features.tasks.screens.add.group.model.NewGroupState
import dev.dprice.productivity.todo.ui.components.CircleWipeTabPager
import dev.dprice.productivity.todo.ui.components.ColourPickerRow
import dev.dprice.productivity.todo.ui.components.FormDivider
import dev.dprice.productivity.todo.ui.components.IconPickerRow
import dev.dprice.productivity.todo.ui.components.buttons.RoundedButton
import dev.dprice.productivity.todo.ui.components.text.RoundedEntryCard
import dev.dprice.productivity.todo.ui.shapes.TabShape
import dev.dprice.productivity.todo.ui.theme.TodoAppTheme
import dev.dprice.productivity.todo.ui.theme.Yellow

enum class GroupTab(val title: String) {
    ICON("Group icon"),
    COLOUR("Group colour")
}

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

        GroupOptionTabs(
            icon = form.icon,
            colour = form.colour
        )

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
private fun GroupOptionTabs(
    icon: ImageVector?,
    colour: Color?
) {

    var selected: GroupTab? by remember {
        mutableStateOf(null)
    }

    CircleWipeTabPager(
        selected = selected,
        items = GroupTab.values().toList(),
        tabOriginOffset = 12.dp,
        tabContent = { tab, isSelected ->
            GroupTab(
                title = tab.title,
                isSelected = isSelected,
                onSelect = { selected = if (selected == tab) null else tab  }
            ) {
                when (tab) {
                    GroupTab.ICON -> IconPickerRow(
                        icon = icon,
                        iconColour = if (isSelected) Color.Black else Color.White,
                        borderColour = if (isSelected) Color.Black else Yellow,
                    )

                    GroupTab.COLOUR -> ColourPickerRow(
                        colour = colour,
                        borderColour = if (isSelected) Color.Black else Yellow,
                    )
                }
            }
            Offset(0f, 16f)
        },
        dropdownContent = { tab -> TabDropdown(tab) }
    )
}

@Composable
private fun TabDropdown(tab: GroupTab) {
    Surface(
        color = Yellow,
        shape = RoundedCornerShape(32.dp)
    ) {
        when (tab) {
            GroupTab.ICON -> IconSelection()
            GroupTab.COLOUR -> ColourSelection()
        }
    }
}

@Composable
private fun GroupTab(
    title: String,
    isSelected: Boolean,
    onSelect: () -> Unit,
    content: @Composable () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = if(isSelected) "" else title,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Surface(
            color = if (isSelected) Yellow else Color.Unspecified,
            shape = TabShape(cornerRadius = 16.dp),
            modifier = Modifier
                .clip(shape = TabShape(cornerRadius = 16.dp))
                .clickable { onSelect() }
        ) {
            Box(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                content()
            }
        }
    }
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
            .then(modifier)
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
            .then(modifier)
    ) {
        items(20) {
            Icon(
                Icons.Default.Home,
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
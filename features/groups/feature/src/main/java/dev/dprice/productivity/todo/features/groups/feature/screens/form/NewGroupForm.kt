package dev.dprice.productivity.todo.features.groups.feature.screens.form

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.dprice.productivity.todo.features.groups.data.model.Colour
import dev.dprice.productivity.todo.features.groups.data.model.Icon
import dev.dprice.productivity.todo.features.groups.feature.R
import dev.dprice.productivity.todo.features.groups.feature.screens.form.model.NewGroupAction
import dev.dprice.productivity.todo.features.groups.feature.screens.form.model.NewGroupState
import dev.dprice.productivity.todo.features.groups.feature.screens.form.model.NewGroupState.GroupTab
import dev.dprice.productivity.todo.features.groups.feature.screens.selector.asColour
import dev.dprice.productivity.todo.features.groups.feature.screens.selector.asImageVector
import dev.dprice.productivity.todo.ui.components.CircleWipeTabPager
import dev.dprice.productivity.todo.ui.components.FormDivider
import dev.dprice.productivity.todo.ui.components.buttons.RoundedButton
import dev.dprice.productivity.todo.ui.components.text.RoundedEntryCard
import dev.dprice.productivity.todo.ui.shapes.TabShape
import dev.dprice.productivity.todo.ui.theme.TodoAppTheme
import dev.dprice.productivity.todo.ui.theme.Yellow

@Composable
fun NewGroupForm(
    state: NewGroupState,
    modifier: Modifier = Modifier,
    onAction: (NewGroupAction) -> Unit
) {
    val focusManager = LocalFocusManager.current
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(16.dp)
            .padding(top = 16.dp)
            .verticalScroll(rememberScrollState())
            .then(modifier)
    ) {
        Text(
            text = stringResource(id = R.string.group_form_description),
            style = MaterialTheme.typography.body1,
            textAlign = TextAlign.Center,
            color = Color.White
        )

        FormDivider(modifier = Modifier.padding(20.dp))

        val groupFocusRequester = remember { FocusRequester() }
        RoundedEntryCard(
            entry = state.title,
            modifier = Modifier.onFocusChanged {
                if (it.hasFocus) groupFocusRequester.freeFocus()
                onAction(NewGroupAction.UpdateTitleFocus(it.hasFocus))
            },
            onImeAction = { groupFocusRequester.requestFocus() },
            onTextChanged = { onAction(NewGroupAction.UpdateTitleText(it)) }
        )

        GroupOptionTabs(
            selectedTab = state.selectedTab,
            icon = state.icon,
            colour = state.colour,
            modifier = Modifier.focusRequester(groupFocusRequester),
            updateIcon = { onAction(NewGroupAction.SelectIcon(it)) },
            updateColour = { onAction(NewGroupAction.SelectColour(it)) },
            selectTab = {
                if (it == null) {
                    groupFocusRequester.freeFocus()
                } else {
                    groupFocusRequester.requestFocus()
                }
                onAction(NewGroupAction.SelectTab(it))
            }
        )

        FormDivider(modifier = Modifier.padding(20.dp))

        RoundedButton(
            text = stringResource(id = R.string.group_form_create_button),
            buttonState = state.buttonState,
            modifier = Modifier.focusable(),
            onClick = { onAction(NewGroupAction.SubmitForm) }
        )
    }
}

@Composable
private fun GroupOptionTabs(
    selectedTab: GroupTab?,
    icon: Icon,
    colour: Colour,
    modifier: Modifier = Modifier,
    updateIcon: (Icon) -> Unit,
    updateColour: (Colour) -> Unit,
    selectTab: (GroupTab?) -> Unit
) {
    CircleWipeTabPager(
        selected = selectedTab,
        items = GroupTab.values().toList(),
        tabOriginOffset = 12.dp,
        modifier = Modifier
            .onFocusChanged {
                if (it.hasFocus && selectedTab == null) selectTab(GroupTab.ICON)
            }
            .then(modifier),
        tabContent = { tab, isSelected ->
            GroupTab(
                title = stringResource(id = tab.titleId),
                isSelected = isSelected,
                onSelect = { selectTab(if (selectedTab == tab) null else tab) }
            ) {
                when (tab) {
                    GroupTab.ICON -> IconTabIcon(
                        icon = icon.asImageVector(),
                        iconColour = if (isSelected) Color.Black else Color.White,
                        borderColour = if (isSelected) Color.Black else Yellow,
                    )

                    GroupTab.COLOUR -> ColourTabIcon(
                        colour = colour.asColour(),
                        borderColour = if (isSelected) Color.Black else Yellow,
                    )
                }
            }
        },
        dropdownContent = { tab ->
            TabDropdown(
                currentIcon = icon,
                currentColour = colour,
                tab = tab,
                updateIcon = updateIcon,
                updateColour = updateColour
            )
        }
    )
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
private fun TabDropdown(
    tab: GroupTab,
    currentColour: Colour,
    currentIcon: Icon,
    updateIcon: (Icon) -> Unit,
    updateColour: (Colour) -> Unit,
) {
    Surface(
        color = Yellow,
        shape = RoundedCornerShape(32.dp)
    ) {
        when (tab) {
            GroupTab.ICON -> IconSelection(
                currentIcon = currentIcon,
                icons = Icon.values().toList(),
                onSelect = updateIcon
            )
            GroupTab.COLOUR -> ColourSelection(
                currentColour = currentColour,
                colours = Colour.values().toList(),
                onSelect = updateColour
            )
        }
    }
}

@Composable
fun IconSelection(
    currentIcon: Icon,
    icons: List<Icon>,
    onSelect: (Icon) -> Unit
) {
    LazyHorizontalGrid(
        rows = GridCells.Fixed(3),
        modifier = Modifier
            .fillMaxWidth()
            .height((80.dp * 3) + 32.dp),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(icons) { icon ->
            Surface(
                color = Color.Unspecified,
                shape = CircleShape,
                border = BorderStroke(
                    width = 2.dp,
                    color = if (currentIcon == icon) Color.Black else Color.Unspecified
                ),
                modifier = Modifier
                    .padding(16.dp)
                    .clip(CircleShape)
                    .clickable { onSelect(icon) }
            ) {
                Icon(
                    icon.asImageVector(),
                    contentDescription = null,
                    tint = if (icon == Icon.NONE) Color.Red else Color.Black,
                    modifier = Modifier
                        .size(48.dp)
                        .padding(8.dp)
                )
            }
        }
    }
}

@Composable
fun ColourSelection(
    currentColour: Colour,
    colours: List<Colour>,
    onSelect: (Colour) -> Unit
) {
    LazyHorizontalGrid(
        rows = GridCells.Fixed(3),
        modifier = Modifier
            .fillMaxWidth()
            .height((80.dp * 3) + 32.dp),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(colours) { colour ->
            Surface(
                color = colour.asColour(),
                modifier = Modifier
                    .size(80.dp)
                    .padding(16.dp)
                    .clickable { onSelect(colour) },
                border = BorderStroke(
                    color = if (colour == currentColour) Color.Black else Color.Unspecified,
                    width = 2.dp
                ),
                shape = CircleShape,
                shadowElevation = 8.dp,
                content = { }
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
            state = NewGroupState(),
            onAction = { }
        )
    }
}
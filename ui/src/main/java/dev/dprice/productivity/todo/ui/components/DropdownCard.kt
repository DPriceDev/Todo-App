package dev.dprice.productivity.todo.features.tasks.screens.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import dev.dprice.productivity.todo.ui.theme.MediumBlue

@Composable
private fun DropdownCard(
    title: String,
    value: String,
    modifier: Modifier = Modifier,
    isExpanded: MutableState<Boolean> = remember { mutableStateOf(false) },
    dropdownContent: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = modifier
    ) {
        OutlinedTextField(
            value = value,
            label = { Text(title) },
            shape = RoundedCornerShape(percent = 50),
            textStyle = MaterialTheme.typography.body1,
            enabled = false,
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = null
                )
            },
            onValueChange = { },
            modifier = Modifier.clickable { isExpanded.value = true },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                disabledTextColor = MediumBlue,
                disabledBorderColor = MediumBlue,
                disabledLabelColor = MediumBlue,
                disabledTrailingIconColor = MediumBlue
            )
        )

        DropdownMenu(
            expanded = isExpanded.value,
            onDismissRequest = { isExpanded.value = false },
            content = dropdownContent
        )
    }
}
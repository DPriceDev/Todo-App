package dev.dprice.productivity.todo.ui.components

import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.compose.ui.unit.dp
import dev.dprice.productivity.todo.ui.theme.TodoAppTheme
import dev.dprice.productivity.todo.ui.theme.Yellow

sealed class FormEntry<out T> {

    data class Description(
        val text: String
    ) : FormEntry<Nothing>()

    data class Row<T>(val entries :List<FormEntry<T>>) : FormEntry<T>()

    data class Text<T>(val id: T, val entry: EntryField) : FormEntry<T>()
    object Divider : FormEntry<Nothing>()
    data class Button<T>(val id: T, val state: ButtonState) : FormEntry<T>()
    data class ColourPicker<T>(val id: T, val colour: Color) : FormEntry<T>()
    data class IconPicker<T>(val id: T, val icon: ImageVector) : FormEntry<T>()
}

sealed class FormAction<T> {
    abstract val id: T

    data class UpdateText<T>(override val id: T, val text: String) : FormAction<T>()
    data class UpdateFocus<T>(override val id: T, val focus: Boolean) : FormAction<T>()
    data class ButtonClicked<T>(override val id: T) : FormAction<T>()
}

@Composable
fun <T> Form(
    entries: List<FormEntry<T>>,
    modifier: Modifier = Modifier,
    onAction: (FormAction<T>) -> Unit
) {
    val focusManager = LocalFocusManager.current
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = modifier
    ) {
        entries.forEach { entry ->
            FormEntries(
                entry = entry,
                onAction = onAction,
                focusManager = focusManager
            )
        }
    }
}

@Composable
fun FormDivider(
    modifier: Modifier = Modifier
) {
    Divider(
        color = Yellow,
        thickness = 1.dp,
        modifier = modifier
    )
}

@Composable
private fun <T> FormEntries(
    entry: FormEntry<T>,
    focusManager: FocusManager,
    onAction: (FormAction<T>) -> Unit,
) {
    when (entry) {
        is FormEntry.Description -> Text(
            text = entry.text,
            style = MaterialTheme.typography.body1,
            textAlign = TextAlign.Center,
            color = Color.White,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        FormEntry.Divider -> Divider(
            color = Yellow,
            thickness = 1.dp,
            modifier = Modifier.padding(bottom = 20.dp)
        )
        is FormEntry.Text -> RoundedEntryCard(
            entry = entry.entry,
            modifier = Modifier.onFocusChanged {
                onAction(FormAction.UpdateFocus(entry.id, it.hasFocus))
            },
            onImeAction = { focusManager.moveFocus(FocusDirection.Down) },
            onTextChanged = { onAction(FormAction.UpdateText(entry.id, it)) }
        )
        is FormEntry.Button -> RoundedButton(
            text = "Create",
            buttonState = entry.state,
            modifier = Modifier.focusable(),
            onClick = { onAction(FormAction.ButtonClicked(entry.id)) }
        )
        is FormEntry.ColourPicker -> ColourPickerRow(
            colour = entry.colour,
            modifier = Modifier.focusable(),
            onClick = { onAction(FormAction.ButtonClicked(entry.id)) }
        )
        is FormEntry.IconPicker -> IconPickerRow(
            icon = entry.icon,
            modifier = Modifier.focusable(),
            onClick = { onAction(FormAction.ButtonClicked(entry.id)) }
        )
        is FormEntry.Row -> FormRow(
            entries = entry.entries,
            onAction = onAction,
            focusManager = focusManager
        )
    }
}

@Composable
private fun <T> FormRow(
    entries: List<FormEntry<T>>,
    focusManager: FocusManager,
    onAction: (FormAction<T>) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        entries.forEach { entry ->
            Box(
                modifier = Modifier.weight(1f)
            ) {
                FormEntries(
                    entry = entry,
                    onAction = onAction,
                    focusManager = focusManager
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
private fun PreviewForm() {

    val entries = listOf(
        FormEntry.Description(
            text = LoremIpsum(20).values.joinToString()
        ),
        FormEntry.Divider,
        FormEntry.Text(
            id = "Text1",
            EntryField()
        ),
        FormEntry.Text(
            id = "Text2",
            EntryField()
        ),
        FormEntry.Divider,
        FormEntry.Row(
            listOf(
                FormEntry.ColourPicker(id = "picker1", Color(0xFF4BC550)),
                FormEntry.IconPicker(id = "picker2", Icons.Default.Edit),
            )
        ),
        FormEntry.Divider,
        FormEntry.Button(
            id = "Text3",
            state = ButtonState.ENABLED
        ),
    )

    TodoAppTheme {
        Form(entries = entries) { }
    }
}
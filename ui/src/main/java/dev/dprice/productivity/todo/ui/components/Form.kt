package dev.dprice.productivity.todo.ui.components

import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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

    data class Text<T>(val id: T, val entry: EntryField) : FormEntry<T>()
    object Divider : FormEntry<Nothing>()
    data class Button<T>(val id: T, val state: ButtonState) : FormEntry<T>()
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
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = modifier
    ) {
        entries.forEach { entry ->
            when(entry) {
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
                // todo:
                is FormEntry.Text -> RoundedEntryCard(
                    entry = entry.entry,
                    onTextChanged = { }
                )
                // todo
                is FormEntry.Button -> RoundedButton(
                    text = "Create",
                    buttonState = entry.state,
                    modifier = Modifier.focusable(),
                    onClick = { /* todo */ }
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
        FormEntry.Button(
            id = "Text3",
            state = ButtonState.ENABLED
        ),
    )

    TodoAppTheme {
        Form(entries = entries) { }
    }
}
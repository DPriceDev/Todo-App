package dev.dprice.productivity.todo.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.dprice.productivity.todo.ui.theme.TodoAppTheme
import dev.dprice.productivity.todo.ui.theme.Yellow

sealed class FormEntry<out T> {

    data class Text<T>(
        val id: T,
        val entry: EntryField
    ) : FormEntry<T>()

    object Divider : FormEntry<Nothing>()
}

@Composable
fun <T> Form(
    entries: List<FormEntry<T>>
) {
    Column {
        entries.forEach { entry ->
            when(entry) {
                FormEntry.Divider -> Divider(
                    color = Yellow,
                    thickness = 1.dp,
                    modifier = Modifier.padding(bottom = 20.dp)
                )
                is FormEntry.Text -> RoundedEntryCard(
                    entry = entry.entry,
                    onTextChanged = { }
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
        FormEntry.Text(
            id = "Text1",
            EntryField()
        ),
        FormEntry.Text(
            id = "Text1",
            EntryField()
        ),
        FormEntry.Divider,
        FormEntry.Text(
            id = "Text1",
            EntryField()
        ),
    )

    TodoAppTheme {
        Form(entries = entries)
    }
}
package dev.dprice.productivity.todo.features.tasks.ui.list

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import dev.dprice.productivity.todo.ui.components.EntryField
import dev.dprice.productivity.todo.ui.components.RoundedEntryCard

@Composable
fun TitleBar() {
    var isSearching: Boolean by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(72.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (isSearching) {
            RoundedEntryCard(
                entry = EntryField(
                    value = "",
                    hintText = "Search Tasks",
                    icon = Icons.Outlined.Search
                ),
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp),
                onTextChanged = { }
            )
        } else {
            Text(
                text = "Tasks",
                modifier = Modifier
                    .padding(start = 64.dp)
                    .weight(1f),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.h3
            )
        }

        IconButton(
            onClick = { isSearching = true }
        ) {
            Icon(
                Icons.Outlined.Search,
                null,
                modifier = Modifier
                    .size(48.dp)
                    .padding(12.dp)
            )
        }

        Spacer(modifier = Modifier.width(8.dp))
    }
}

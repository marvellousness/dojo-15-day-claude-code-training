package com.dojo.notes.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.dojo.notes.data.Note

/** Stateless screen: everything comes in through [state] and leaves through callbacks. */
@Composable
fun NotesScreen(
    state: NotesUiState,
    onAdd: (String) -> Unit,
    onToggle: (Note) -> Unit,
    onSync: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var draft by rememberSaveable { mutableStateOf("") }

    Column(modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(
                value = draft,
                onValueChange = { draft = it },
                label = { Text("New note") },
                modifier = Modifier.weight(1f).testTag("noteInput"),
            )
            Button(
                onClick = { onAdd(draft); draft = "" },
                enabled = draft.isNotBlank(),
                modifier = Modifier.testTag("addButton"),
            ) { Text("Add") }
        }
        Button(
            onClick = onSync,
            enabled = !state.isSyncing,
            modifier = Modifier.fillMaxWidth().testTag("syncButton"),
        ) { Text(if (state.isSyncing) "Syncing…" else "Sync") }
        state.message?.let { Text(it, modifier = Modifier.testTag("message")) }
        LazyColumn(Modifier.fillMaxSize()) {
            items(state.notes, key = { it.id }) { note ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(checked = note.done, onCheckedChange = { onToggle(note) })
                    Text(note.text + if (note.synced) "" else "  •")
                }
            }
        }
    }
}

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
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.dojo.notes.R
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
                label = { Text(stringResource(R.string.new_note_label)) },
                modifier = Modifier.weight(1f).testTag("noteInput"),
            )
            Button(
                onClick = { onAdd(draft); draft = "" },
                enabled = draft.isNotBlank(),
                modifier = Modifier.testTag("addButton"),
            ) { Text(stringResource(R.string.add)) }
        }
        Button(
            onClick = onSync,
            enabled = !state.isSyncing,
            modifier = Modifier.fillMaxWidth().testTag("syncButton"),
        ) { Text(stringResource(if (state.isSyncing) R.string.syncing else R.string.sync)) }
        state.syncResult?.let { result ->
            val text = when (result) {
                is SyncResult.Synced if result.count == 0 -> stringResource(R.string.nothing_to_sync)
                is SyncResult.Synced -> pluralStringResource(R.plurals.synced_notes, result.count, result.count)
                // Raw exception text can contain backend internals, so the user sees a generic message.
                is SyncResult.Failed -> stringResource(R.string.sync_failed)
            }
            Text(text, modifier = Modifier.testTag("message"))
        }
        LazyColumn(Modifier.fillMaxSize()) {
            items(state.notes, key = { it.id }) { note ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(checked = note.done, onCheckedChange = { onToggle(note) })
                    Text(note.text)
                    if (!note.synced) {
                        val notSynced = stringResource(R.string.not_synced)
                        Text("  •", modifier = Modifier.semantics { contentDescription = notSynced })
                    }
                }
            }
        }
    }
}

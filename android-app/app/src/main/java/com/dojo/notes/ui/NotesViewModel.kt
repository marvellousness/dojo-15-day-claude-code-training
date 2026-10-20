package com.dojo.notes.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dojo.notes.data.Note
import com.dojo.notes.data.NotesRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/** Outcome of the last sync; the screen turns it into translated text. */
sealed interface SyncResult {
    data class Synced(val count: Int) : SyncResult
    data class Failed(val reason: String?) : SyncResult
}

data class NotesUiState(
    val notes: List<Note> = emptyList(),
    val isSyncing: Boolean = false,
    val syncResult: SyncResult? = null,
)

class NotesViewModel(private val repository: NotesRepository) : ViewModel() {
    private val syncState = MutableStateFlow(NotesUiState())

    val uiState: StateFlow<NotesUiState> =
        combine(repository.notes, syncState) { notes, sync -> sync.copy(notes = notes) }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), NotesUiState())

    fun addNote(text: String) {
        viewModelScope.launch { repository.add(text) }
    }

    fun toggleDone(note: Note) {
        viewModelScope.launch { repository.toggleDone(note.id) }
    }

    fun sync() {
        if (syncState.value.isSyncing) return
        // Set before launching so a second tap is ignored even if the coroutine hasn't started yet.
        syncState.update { it.copy(isSyncing = true, syncResult = null) }
        viewModelScope.launch {
            val result = try {
                val count = repository.sync()
                if (count > 0) SyncResult.Synced(count) else null
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                SyncResult.Failed(e.message)
            }
            syncState.update { it.copy(isSyncing = false, syncResult = result) }
        }
    }
}

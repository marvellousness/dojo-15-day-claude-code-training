package com.dojo.notes.data

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

/** Single entry point for note data: local Room storage plus push-only sync. */
class NotesRepository(
    private val dao: NoteDao,
    private val api: SyncApi,
    // Network calls may block (like a real HTTP client), so they never run on the caller's thread.
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) {
    val notes: Flow<List<Note>> = dao.observeAll()

    suspend fun add(text: String) {
        val trimmed = text.trim()
        if (trimmed.isEmpty()) return
        dao.insert(Note(text = trimmed))
    }

    suspend fun toggleDone(id: Long) {
        dao.toggleDone(id)
    }

    /** Pushes unsynced notes (the backend may accept only part of them) and returns how many were accepted. */
    suspend fun sync(): Int {
        val pending = dao.unsynced()
        if (pending.isEmpty()) return 0
        val accepted = withContext(ioDispatcher) {
            val token = api.fetchToken()
            // An expired session returns a blank token; don't send notes the backend will reject.
            if (token.isBlank()) emptyList() else api.push(token, pending)
        }
        // Only what the backend accepted is synced; the rest stays pending for the next sync.
        // Each row is checked against the version that was pushed, so an edit made during the push is sent next time.
        val acceptedIds = accepted.toSet()
        dao.markSynced(pending.filter { it.id in acceptedIds })
        return accepted.size
    }
}

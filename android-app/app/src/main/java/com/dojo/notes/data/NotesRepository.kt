package com.dojo.notes.data

import kotlinx.coroutines.flow.Flow

/** Single entry point for note data: local Room storage plus push-only sync. */
class NotesRepository(
    private val dao: NoteDao,
    private val api: SyncApi,
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

    /** Pushes unsynced notes and returns how many were accepted. */
    suspend fun sync(): Int {
        val pending = dao.unsynced()
        if (pending.isEmpty()) return 0
        val token = api.fetchToken()
        val accepted = api.push(token, pending)
        dao.markSynced(accepted)
        return accepted.size
    }
}

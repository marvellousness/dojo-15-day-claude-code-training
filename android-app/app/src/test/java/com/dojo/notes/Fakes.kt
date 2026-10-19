package com.dojo.notes

import com.dojo.notes.data.Note
import com.dojo.notes.data.NoteDao
import com.dojo.notes.data.SyncApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

/** In-memory [NoteDao] so repository and ViewModel tests run on the JVM without Room. */
class FakeNoteDao : NoteDao {
    val notes = MutableStateFlow<List<Note>>(emptyList())
    private var nextId = 1L

    override fun observeAll(): Flow<List<Note>> = notes
    override suspend fun unsynced(): List<Note> = notes.value.filter { !it.synced }
    override suspend fun insert(note: Note): Long {
        val id = nextId++
        notes.value = listOf(note.copy(id = id)) + notes.value
        return id
    }
    override suspend fun update(note: Note) {
        notes.value = notes.value.map { if (it.id == note.id) note else it }
    }
    override suspend fun markSynced(ids: List<Long>) {
        notes.value = notes.value.map { if (it.id in ids) it.copy(synced = true) else it }
    }
}

/** Instant [SyncApi] that records calls and can be told to fail. */
class RecordingSyncApi(var failWith: Exception? = null) : SyncApi {
    var tokenCalls = 0
    val pushed = mutableListOf<List<Note>>()

    override suspend fun fetchToken(): String {
        tokenCalls++
        return "test-token"
    }
    override suspend fun push(token: String, notes: List<Note>): List<Long> {
        failWith?.let { throw it }
        pushed += notes
        return notes.map { it.id }
    }
}

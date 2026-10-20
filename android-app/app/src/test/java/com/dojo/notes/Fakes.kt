package com.dojo.notes

import com.dojo.notes.data.Note
import com.dojo.notes.data.NoteDao
import com.dojo.notes.data.SyncApi
import kotlinx.coroutines.CompletableDeferred
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
    override suspend fun toggleDone(id: Long) {
        notes.value = notes.value.map { if (it.id == id) it.copy(done = !it.done, synced = false) else it }
    }
    override suspend fun markSynced(ids: List<Long>) {
        notes.value = notes.value.map { if (it.id in ids) it.copy(synced = true) else it }
    }
}

/**
 * [SyncApi] that records calls and can be told to fail. Set [gate] to hold `push`
 * open until the test completes it, to observe the in-flight state.
 */
class RecordingSyncApi(var failWith: Exception? = null) : SyncApi {
    var gate: CompletableDeferred<Unit>? = null
    var token = "test-token"
    var tokenCalls = 0
    val pushed = mutableListOf<List<Note>>()

    override suspend fun fetchToken(): String {
        tokenCalls++
        return token
    }
    override suspend fun push(token: String, notes: List<Note>): List<Long> {
        gate?.await()
        failWith?.let { throw it }
        pushed += notes
        return notes.map { it.id }
    }
}

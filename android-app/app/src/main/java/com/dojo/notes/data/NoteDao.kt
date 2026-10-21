package com.dojo.notes.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Query("SELECT * FROM notes ORDER BY createdAt DESC")
    fun observeAll(): Flow<List<Note>>

    @Query("SELECT * FROM notes WHERE synced = 0")
    suspend fun unsynced(): List<Note>

    @Insert
    suspend fun insert(note: Note): Long

    /** Flips `done` in the database itself, so rapid taps can't act on a stale copy. */
    @Query("UPDATE notes SET done = NOT done, synced = 0, version = version + 1 WHERE id = :id")
    suspend fun toggleDone(id: Long)

    /** Marks the row synced only if it is still the [version] that was pushed; a newer edit stays pending. */
    @Query("UPDATE notes SET synced = 1 WHERE id = :id AND version = :version")
    suspend fun markSynced(id: Long, version: Int)

    /** Version-checks each pushed copy in one transaction, so the notes list sees one change per sync. */
    @Transaction
    suspend fun markSynced(pushed: List<Note>) {
        pushed.forEach { markSynced(it.id, it.version) }
    }
}

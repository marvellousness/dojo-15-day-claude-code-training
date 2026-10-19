package com.dojo.notes.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
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
    @Query("UPDATE notes SET done = NOT done, synced = 0 WHERE id = :id")
    suspend fun toggleDone(id: Long)

    @Query("UPDATE notes SET synced = 1 WHERE id IN (:ids)")
    suspend fun markSynced(ids: List<Long>)
}

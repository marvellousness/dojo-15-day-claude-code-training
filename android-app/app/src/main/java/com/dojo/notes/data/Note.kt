package com.dojo.notes.data

import androidx.room.Entity
import androidx.room.PrimaryKey

/** A single note. `synced` turns true once the fake backend has accepted it. */
@Entity(tableName = "notes")
data class Note(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val text: String,
    val done: Boolean = false,
    val synced: Boolean = false,
    val createdAt: Long = System.currentTimeMillis(),
)

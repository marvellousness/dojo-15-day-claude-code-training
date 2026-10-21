package com.dojo.notes.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * A single note. `synced` turns true once the fake backend has accepted it.
 * `version` goes up on every local edit, so a sync can tell whether the row changed after it was pushed.
 */
@Entity(tableName = "notes")
data class Note(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val text: String,
    val done: Boolean = false,
    val synced: Boolean = false,
    val createdAt: Long = System.currentTimeMillis(),
    // Must match the DEFAULT in MIGRATION_1_2, or Room's schema check fails on upgrade.
    @ColumnInfo(defaultValue = "0") val version: Int = 0,
)

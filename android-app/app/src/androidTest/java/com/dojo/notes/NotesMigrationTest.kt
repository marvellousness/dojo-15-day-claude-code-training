package com.dojo.notes

import androidx.room.testing.MigrationTestHelper
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.dojo.notes.data.NotesDatabase
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/** Upgrades a real v1 database (from app/schemas) and checks Room accepts the result. */
@RunWith(AndroidJUnit4::class)
class NotesMigrationTest {
    @get:Rule
    val helper = MigrationTestHelper(InstrumentationRegistry.getInstrumentation(), NotesDatabase::class.java)

    @Test
    fun migrate1To2_keepsNotesAndStartsVersionAtZero() {
        helper.createDatabase(DB_NAME, 1).apply {
            execSQL("INSERT INTO notes (id, text, done, synced, createdAt) VALUES (1, 'kept', 1, 0, 1000)")
            close()
        }

        // validateDroppedTables = true; also fails if the result doesn't match the v2 schema (e.g. a missing DEFAULT).
        val db = helper.runMigrationsAndValidate(DB_NAME, 2, true, NotesDatabase.MIGRATION_1_2)

        db.query("SELECT text, done, synced, version FROM notes WHERE id = 1").use { row ->
            assertTrue(row.moveToFirst())
            assertEquals("kept", row.getString(0))
            assertEquals(1, row.getInt(1))
            assertEquals(0, row.getInt(2))
            assertEquals(0, row.getInt(3))
        }
    }

    private companion object {
        const val DB_NAME = "migration-test.db"
    }
}

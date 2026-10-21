package com.dojo.notes

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.dojo.notes.data.Note
import com.dojo.notes.data.NoteDao
import com.dojo.notes.data.NotesDatabase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/** Runs the real SQL that [FakeNoteDao] only imitates (NOTE-108 version check). */
@RunWith(AndroidJUnit4::class)
class NoteDaoTest {
    private lateinit var db: NotesDatabase
    private lateinit var dao: NoteDao

    @Before fun setUp() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        db = Room.inMemoryDatabaseBuilder(context, NotesDatabase::class.java).build()
        dao = db.noteDao()
    }

    @After fun tearDown() = db.close()

    private suspend fun only(): Note = dao.observeAll().first().single()

    @Test
    fun toggleDone_bumpsVersionAndClearsSynced() = runBlocking {
        val id = dao.insert(Note(text = "a"))
        dao.markSynced(id, version = 0)

        dao.toggleDone(id)

        val note = only()
        assertTrue(note.done)
        assertFalse(note.synced)
        assertEquals(1, note.version)
    }

    @Test
    fun markSynced_withMatchingVersion_setsSynced() = runBlocking {
        val id = dao.insert(Note(text = "a"))

        dao.markSynced(id, version = 0)

        assertTrue(dao.unsynced().isEmpty())
    }

    @Test
    fun markSynced_withStaleVersion_leavesRowPending() = runBlocking {
        val id = dao.insert(Note(text = "a"))
        dao.toggleDone(id) // edited after the version-0 copy was pushed

        dao.markSynced(id, version = 0)

        assertEquals(listOf(id), dao.unsynced().map { it.id })
    }

    @Test
    fun markSyncedBatch_marksCurrentRowsAndLeavesStaleOnesPending() = runBlocking {
        val current = dao.insert(Note(text = "current"))
        val stale = dao.insert(Note(text = "stale"))
        val pushed = dao.unsynced() // both captured at version 0
        dao.toggleDone(stale) // edited after the push captured it

        dao.markSynced(pushed)

        assertEquals(listOf(stale), dao.unsynced().map { it.id })
        assertTrue(dao.observeAll().first().single { it.id == current }.synced)
    }
}

package com.dojo.notes

import com.dojo.notes.data.NotesRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class NotesRepositoryTest {
    private val dao = FakeNoteDao()
    private val api = RecordingSyncApi()
    private val repository = NotesRepository(dao, api)

    @Test
    fun `add trims text and ignores blanks`() = runTest {
        repository.add("  buy milk  ")
        repository.add("   ")

        assertEquals(listOf("buy milk"), dao.notes.value.map { it.text })
    }

    @Test
    fun `sync pushes only unsynced notes and marks them synced`() = runTest {
        repository.add("a")
        repository.add("b")

        assertEquals(2, repository.sync())
        assertTrue(dao.notes.value.all { it.synced })
        assertEquals(0, repository.sync())
        assertEquals(1, api.pushed.size)
    }

    @Test
    fun `sync with nothing pending skips the network`() = runTest {
        assertEquals(0, repository.sync())
        assertEquals(0, api.tokenCalls)
    }

    @Test
    fun `toggling a note marks it unsynced again`() = runTest {
        repository.add("a")
        repository.sync()

        repository.toggleDone(dao.notes.value.single().id)

        val note = dao.notes.value.single()
        assertTrue(note.done)
        assertEquals(false, note.synced)
    }

    @Test
    fun `toggling twice returns the note to not done`() = runTest {
        repository.add("a")
        val id = dao.notes.value.single().id

        repository.toggleDone(id)
        repository.toggleDone(id)

        assertEquals(false, dao.notes.value.single().done)
    }
}

package com.dojo.notes

import com.dojo.notes.data.NotesRepository
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
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

    @Test
    fun `sync with a blank token sends nothing and keeps notes pending`() = runTest {
        api.token = ""
        repository.add("a")

        assertEquals(0, repository.sync())
        assertTrue(api.pushed.isEmpty())
        assertEquals(1, dao.unsynced().size)
    }

    // NOTE-108: a change made while the push is in flight must not be marked synced.
    @Test
    fun `a toggle during sync keeps the note unsynced and the next sync sends the new state`() = runTest {
        val repository = NotesRepository(dao, api, StandardTestDispatcher(testScheduler))
        repository.add("a")
        val id = dao.notes.value.single().id
        api.gate = CompletableDeferred()

        val sync = launch { repository.sync() }
        runCurrent() // push has captured "a" with done = false and is waiting on the gate
        repository.toggleDone(id)
        api.gate!!.complete(Unit)
        sync.join()

        val note = dao.notes.value.single()
        assertTrue(note.done)
        assertFalse("edited during sync, so it must stay pending", note.synced)

        api.gate = null
        assertEquals(1, repository.sync())
        assertTrue(api.pushed.last().single().done)
        assertTrue(dao.notes.value.single().synced)
    }

    @Test
    fun `notes not edited during sync are still marked synced`() = runTest {
        val repository = NotesRepository(dao, api, StandardTestDispatcher(testScheduler))
        repository.add("a")
        repository.add("b")
        val b = dao.notes.value.single { it.text == "b" }.id
        api.gate = CompletableDeferred()

        val sync = launch { repository.sync() }
        runCurrent()
        repository.toggleDone(b)
        api.gate!!.complete(Unit)
        sync.join()

        assertTrue(dao.notes.value.single { it.text == "a" }.synced)
        assertFalse("b was edited during sync", dao.notes.value.single { it.text == "b" }.synced)
    }
}

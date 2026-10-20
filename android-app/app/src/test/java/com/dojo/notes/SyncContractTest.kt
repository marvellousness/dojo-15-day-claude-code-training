package com.dojo.notes

import com.dojo.notes.data.FakeSyncApi
import com.dojo.notes.data.NotesRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Tests against the real [FakeSyncApi] contract (batch limit, latency), which
 * [RecordingSyncApi] doesn't model. Written red for NOTE-101 and NOTE-102.
 */
class SyncContractTest {
    private val dao = FakeNoteDao()

    // NOTE-102: notes the backend didn't accept must stay pending.
    @Test
    fun `sync marks only accepted notes as synced and a second sync sends the rest`() = runTest {
        val repository = NotesRepository(dao, FakeSyncApi(latencyMs = 0))
        repeat(5) { repository.add("test ${it + 1}") }

        assertEquals(3, repository.sync())
        assertEquals(3, dao.notes.value.count { it.synced })
        assertEquals(2, dao.unsynced().size)

        assertEquals(2, repository.sync())
        assertTrue(dao.notes.value.all { it.synced })
    }

    // NOTE-101: sync must suspend, not block the calling (main) thread.
    @Test
    fun `sync does not block the calling thread`() = runTest {
        val repository = NotesRepository(dao, FakeSyncApi(latencyMs = 200))
        repository.add("test 1")

        val start = System.nanoTime()
        repository.sync()
        val blockedMs = (System.nanoTime() - start) / 1_000_000

        assertTrue("sync blocked the calling thread for ${blockedMs}ms", blockedMs < 100)
    }
}

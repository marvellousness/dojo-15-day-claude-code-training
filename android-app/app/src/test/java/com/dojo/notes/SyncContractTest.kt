package com.dojo.notes

import com.dojo.notes.data.FakeSyncApi
import com.dojo.notes.data.NotesRepository
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withContext
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.util.concurrent.Executors
import kotlin.system.measureTimeMillis

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

    // NOTE-101: while the upload runs, the calling (main) thread must stay free for input.
    // Real threads, not runTest: virtual time would hide a blocking Thread.sleep.
    @Test
    fun `sync keeps the calling thread free during the upload`() = runBlocking {
        val main = Executors.newSingleThreadExecutor().asCoroutineDispatcher()
        try {
            val repository = NotesRepository(dao, FakeSyncApi(latencyMs = 200))
            repository.add("test 1")

            val sync = launch(main) { repository.sync() }
            delay(300) // token fetch (200 ms) done, upload (400 ms) in progress
            val waitedMs = measureTimeMillis { withContext(main) {} }
            sync.join()

            assertTrue("main thread was blocked for ${waitedMs}ms", waitedMs < 100)
        } finally {
            main.close()
        }
    }
}

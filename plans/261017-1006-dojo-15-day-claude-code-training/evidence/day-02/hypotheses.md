# Day 2 task 2.4 — hypotheses from the trace, each verified by running (2026-10-20)

Input given to Claude: the NOTE-101 ANR main-thread trace (`adb bugreport`, see NOTE-101) + the NOTE-102 repro + the code (`FakeSyncApi.kt`, `NotesRepository.kt`, `NotesViewModel.kt`).
Method: list hypotheses, then for each one run something that could prove it wrong. Experiments ran as a temporary JVM test (`HypothesisExperimentsTest.kt`, code below, deleted afterwards so it doesn't ship).

| # | Hypothesis | How verified (ran) | Result | Verdict |
|---|---|---|---|---|
| H1 | `FakeSyncApi.push` blocks the calling thread (real sleep, not a suspending `delay`) | Timed `push(3 notes, 100 ms)` in real time under `runTest` (virtual time would make `delay` ~0 ms) | **411 ms** real time | Confirmed |
| H2 | Block time grows with notes, capped by a batch size | Timed `push` for 1, 2, 3, 5, 10 notes at 100 ms | 205 / 303 / 405 / 401 / 402 ms → `(n+1) × latency`, capped at 3 notes | Confirmed: with the default 1500 ms, 3+ notes = **6 s** blocked, above the 5 s input-dispatch timeout |
| H3 | The database (Room/SQLite) runs on the main thread | Searched the full `"main"` stack in the ANR file for sqlite/room frames | 0 frames | Rejected |
| H4 | The token fetch is what blocks | Timed `fetchToken(1000 ms)` in real time under `runTest` | **1 ms** (it suspends with `delay`) | Rejected; it explains the ~1.5 s of normal UI *before* the freeze |
| H5 | The backend accepts at most N notes per request | `push(5 notes)` return value | `[1, 2, 3]` → max 3 | Confirmed |
| H6 | `NotesRepository.sync` marks more notes synced than the backend accepted | Repository + `FakeSyncApi(0)` + `FakeNoteDao`, 5 notes, sync twice | accepted=3, markedSynced=**5**, pendingAfter=0, secondSync=0 | Confirmed → NOTE-102 root cause |
| H7 | The blocking call runs on Main because the coroutine resumes there | Read deeper frames of the same ANR `"main"` stack | `NotesRepository$sync$1.invokeSuspend` resumed by `kotlinx.coroutines.android.HandlerContext.scheduleResumeAfterDelay` → `Handler.dispatchMessage` (the main Looper) | Confirmed → the coroutine started in `viewModelScope` (Main) and came back to Main after the token `delay`, then hit `Thread.sleep` |

## Conclusions
- **NOTE-101:** `FakeSyncApi.simulateUpload` uses `Thread.sleep(latency × (n+1))`, a blocking call inside a `suspend fun`, and it runs on the main thread (H1, H2, H7). With 3+ unsynced notes and the default 1.5 s latency, Main is blocked 6 s, so any touch in that window ends in an input-dispatch ANR. Not the database (H3), not the token call (H4).
- **NOTE-102:** the backend accepts at most 3 per request (H5), but `NotesRepository.sync` marks every pending note as synced instead of only the accepted ids (H6). Two notes never reach the backend and a second sync finds nothing.
- **Why the tests didn't catch either:** `RecordingSyncApi` (the test fake) has no batch limit and never blocks, so it doesn't model the real contract.

## Experiment code (temporary, not in the repo)
```kotlin
package com.dojo.notes

import com.dojo.notes.data.FakeSyncApi
import com.dojo.notes.data.Note
import com.dojo.notes.data.NotesRepository
import kotlinx.coroutines.test.runTest
import org.junit.Test

/** Temporary Day 2 task 2.4 experiments: each test prints evidence for one hypothesis. Not committed. */
class HypothesisExperimentsTest {
    private fun notes(n: Int) = (1..n).map { Note(id = it.toLong(), text = "test $it") }

    private inline fun realMs(block: () -> Unit): Long {
        val start = System.nanoTime(); block(); return (System.nanoTime() - start) / 1_000_000
    }

    @Test fun `H1 push blocks real time even under virtual time`() = runTest {
        val api = FakeSyncApi(latencyMs = 100)
        val ms = realMs { api.push("t", notes(3)) }
        println("H1 push(3 notes, 100ms) real time = ${ms}ms (delay() would be ~0ms under runTest)")
    }

    @Test fun `H2 push duration vs note count`() = runTest {
        val api = FakeSyncApi(latencyMs = 100)
        for (n in listOf(1, 2, 3, 5, 10)) {
            println("H2 notes=$n real=${realMs { api.push("t", notes(n)) }}ms")
        }
    }

    @Test fun `H4 fetchToken does not block real time`() = runTest {
        val api = FakeSyncApi(latencyMs = 1_000)
        println("H4 fetchToken(1000ms) real time = ${realMs { api.fetchToken() }}ms")
    }

    @Test fun `H5 backend accepts at most N per request`() = runTest {
        val api = FakeSyncApi(latencyMs = 0)
        println("H5 push(5) returned ids = ${api.push("t", notes(5))}")
    }

    @Test fun `H6 repository marks more notes synced than accepted`() = runTest {
        val dao = FakeNoteDao()
        val repo = NotesRepository(dao, FakeSyncApi(latencyMs = 0))
        repeat(5) { repo.add("test ${it + 1}") }
        val accepted = repo.sync()
        val marked = dao.notes.value.count { it.synced }
        println("H6 accepted=$accepted markedSynced=$marked pendingAfter=${dao.unsynced().size} secondSync=${repo.sync()}")
    }
}
```

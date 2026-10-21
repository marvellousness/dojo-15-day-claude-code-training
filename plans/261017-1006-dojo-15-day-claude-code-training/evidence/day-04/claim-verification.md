# Day 4 · Exercise 4.5 — Explain a feature, then verify claims

**Prompt:** "Explore this codebase and explain how Sync works, end to end."
**Code at:** `feat/training-plan` @ 7fec39f

## Claude's explanation (Sync, tap to message)

1. **Tap.** `NotesScreen` shows "Sync notes now". The button is disabled while `isSyncing` is true and calls `onSync`, which goes to `NotesViewModel.sync()`.
2. **ViewModel guard.** `sync()` returns early if a sync is running. It sets `isSyncing = true` *before* `launch`, so a second fast tap is ignored even before the coroutine starts. Then it calls `repository.sync()`. Any exception except `CancellationException` becomes `SyncResult.Failed`.
3. **Repository.** `NotesRepository.sync()` reads `dao.unsynced()` (`synced = 0`) and returns 0 if there is nothing to send. On `ioDispatcher` it fetches a token, then pushes the pending notes. If the token is blank, it skips the push and treats the result as "nothing accepted". It marks only the accepted ids as synced and returns how many there were.
4. **Fake backend.** `FakeSyncApi.push` accepts at most `MAX_BATCH = 3` notes. It blocks the thread with `Thread.sleep(latencyMs * (n + 1))`, which simulates a blocking HTTP client. That is why the call has to run on `Dispatchers.IO`. Latency comes from `BuildConfig.SYNC_LATENCY_MS` (wired in `NotesApp`).
5. **Message.** The screen maps the result to text: `Synced(0)` shows "Nothing to sync", `Synced(n)` shows the plural "Synced n note(s)", and `Failed` shows "Sync failed. Try again." (`reason` is never shown).
6. **Marker.** Each unsynced row shows a "•" marker whose content description is "Not synced". `toggleDone` sets `synced = 0` again, so the edited note is sent on the next sync.

## Claims to verify by reading code (fill in yourself)

| # | Claim | Where to look | Verdict (true / false) | Notes |
|---|---|---|---|---|
| 1 | A second tap during a sync is ignored even if the coroutine has not started yet | `ui/NotesViewModel.kt:44-47` | | |
| 2 | Pushing never runs on the main thread, even though `push` calls `Thread.sleep` | `data/NotesRepository.kt:31`, `data/FakeSyncApi.kt:33-35` | | |
| 3 | When the session has expired (blank token), the user sees "Nothing to sync" even though notes are still pending | `data/NotesRepository.kt:34`, `ui/NotesScreen.kt:63` | | |
| 4 | Toggling a note makes it unsynced again | `data/NoteDao.kt:20` | | |

Pick at least 2. Claim 3, if it holds, is a new bug that isn't in a ticket yet. It's a good candidate for Exercise 4.7.

## What I checked and how (fill in)
-

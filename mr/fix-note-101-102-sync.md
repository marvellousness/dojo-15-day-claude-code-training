# fix(app): sync no longer freezes the app or drops notes (NOTE-101, NOTE-102)

Branch: `fix/note-101-102-sync` → `feat/training-plan` (local MR; GitLab unreachable)
Closes: [NOTE-101](../tickets/NOTE-101.md), [NOTE-102](../tickets/NOTE-102.md)

_Drafted by Claude (Day 3 task 3.2); reviewed by the author and accepted unchanged ("MR draft is good", 2026-10-20)._

## What
- **NOTE-101 (ANR):** `NotesRepository.sync` now runs the token fetch and the upload on an IO dispatcher (`withContext(ioDispatcher)`), so the main thread stays free while notes upload.
- **NOTE-102 (lost notes):** `sync` marks only the ids the backend accepted as synced (`dao.markSynced(accepted)`), not every pending note. Notes over the batch limit stay pending and go out on the next sync.
- `NotesRepository` takes `ioDispatcher: CoroutineDispatcher = Dispatchers.IO` as a constructor parameter. Production wiring in `NotesApp` is unchanged; tests pass their own dispatcher.

3 files, +71 / −7: `NotesRepository.kt`, `SyncContractTest.kt` (new), `NotesViewModelTest.kt`.

## Why
- **NOTE-101:** `FakeSyncApi.push` blocks like a real HTTP client: `(n+1) × latency`, capped at 3 notes, so 6 s with the default 1.5 s. `sync` was called from `viewModelScope` (Main) and resumed on Main after the token `delay`, then blocked Main inside `push`. Any touch in that window ended in an input-dispatch ANR. The ANR trace in NOTE-101 shows `Thread.sleep` under `NotesRepository$sync$1` on `"main"` (hypotheses H1, H2, H7 in `evidence/day-02/hypotheses.md`).
- **NOTE-102:** the backend accepts at most 3 notes per request (H5), but `sync` called `markSynced` with every pending id (H6). With 5 notes the app said "Synced 3", marked all 5 synced, and the 2 extra notes never reached the backend.

## Key decisions
- **Dispatcher in the repository, not the API or the ViewModel.** The repository is the one place that knows it calls the network. Moving the switch here keeps the ViewModel simple and protects every caller, not only the Sync button. Alternative considered: make `FakeSyncApi` use `delay` instead of `Thread.sleep`. Rejected because the fake stands in for a blocking HTTP client; changing it would hide the bug, not fix it.
- **Injectable dispatcher.** The ViewModel tests run on `StandardTestDispatcher`; with a hard-coded `Dispatchers.IO` the upload would leave the test scheduler, and `advanceUntilIdle()` could return before the sync finished.
- **One batch per tap, no loop.** `sync` still sends one batch of up to 3 and returns what was accepted. A user with 5 notes taps twice (3, then 2). Looping until empty is a product decision (progress UI, partial failure), so it's out of scope here; see follow-ups.
- **NOTE-101 test rewritten.** The Day 2 version timed `sync()` with a wall clock inside `runTest`. That can never pass: even with the upload off Main, `sync()` still waits for it (~400 ms). The new test runs `sync` on a real single-thread "main" dispatcher, then 300 ms in (token done, upload running) checks that a task posted to that thread runs within 100 ms.

## How tested
- `./gradlew testDebugUnitTest`: 10/10 pass, 3 runs in a row with `--rerun-tasks` (repository 5, ViewModel 3, `SyncContractTest` 2).
- Red before green: on `day-02/failing-tests` both `SyncContractTest` tests fail. With the repository's `withContext` removed, the rewritten NOTE-101 test fails ("main thread was blocked"); with it restored, it passes.
- `./gradlew lint`: pass. `./gradlew connectedDebugAndroidTest` on the Pixel_9_Pro emulator (Android 16, API 36): Compose UI tests pass.
- Manual, same emulator, debug build with the default 1.5 s latency, clean app data:
  1. Added 5 notes, tapped **Sync notes now**, then tapped the screen 8 times over the next 4 s while the upload ran.
  2. No ANR dialog; logcat (cleared before the tap) has no "ANR in" or "Input dispatching timed out".
  3. Message "Synced 3 note(s)". Second tap: "Synced 2 note(s)". Third tap: "Synced 0 note(s)", and no note shows the "Not synced" marker.
- Not tested: a physical device; latencies other than 1.5 s; the screen closing mid-sync on the new code (the cancellation path was tested on Day 2, before this change); a real backend.

## Screenshots
None attached: the screenshots are larger than the tooling here can read back, so the result was checked through `uiautomator dump` text instead (step 3 above). Add before/after screenshots by hand if the reviewer wants them.

## Risks & follow-ups
- With more than 3 pending notes, one tap does not sync everything, and the message doesn't say more are left. Filed as [NOTE-107](../tickets/NOTE-107.md).
- A note toggled while a sync is in flight is marked synced, but the backend keeps the old state and the change is never sent. Confirmed with a temporary JVM test; existed before this change. Filed as [NOTE-108](../tickets/NOTE-108.md) (High).
- "Synced N note(s)" is still a hard-coded string in the ViewModel (known follow-up from the Day 2 scaffold MR).

## Review checklist (reviewer: Claude, then the author)
- [ ] Builds from a clean clone: `cd android-app && ./gradlew testDebugUnitTest lint`
- [ ] Could be reviewed from this description alone (task 3.3)
- [ ] At least 3 actionable review comments recorded below

## Review comments
_(none yet)_

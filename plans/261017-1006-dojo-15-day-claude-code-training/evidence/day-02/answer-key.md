# Day 2 answer key — DON'T READ until your analyses, repro steps and failing test are done

Seeded in commit "feat(app): batch sync uploads to match backend limit" on `seed/day-02-bugs`.

## NOTE-101 — App freezes when I sync (seeded, Android/threading)
- **Cause:** `FakeSyncApi.simulateUpload()` calls `Thread.sleep(latencyMs * (items + 1))`. `push()` is a `suspend fun`, but `Thread.sleep` blocks whatever thread runs it, and `NotesViewModel.sync()` runs in `viewModelScope` (Main). Blocking time = 1.5 s setup + 1.5 s per note (max 3 per batch): 1 note = 3 s, 2 notes = 4.5 s, 3+ notes = 6 s. The token call uses `delay()` and doesn't block.
- **Repro:** add 3+ unsynced notes, tap Sync, then tap the screen during the freeze. The main thread blocks ~6 s, past the 5 s input-dispatch timeout, so touching the screen triggers the ANR dialog. With 1–2 notes it freezes 3–4.5 s and recovers. This is why "sometimes it comes back".
- **Key reporter questions:** how many notes were unsynced; did you touch the screen during the freeze; which build exactly ("it didn't happen in the first build").
- **Good fix:** never block: `delay()` or `withContext(Dispatchers.IO) { ... }` around blocking work. A test can assert that sync doesn't block Main (e.g. run with `StandardTestDispatcher` and check `isSyncing` emits before completion), or use StrictMode in debug.
- **Bad fix:** lowering the latency; moving the sleep to `fetchToken`.

## NOTE-102 — Notes marked synced but missing (seeded, plain logic)
- **Cause:** the backend accepts at most `MAX_BATCH = 3` notes per request and returns only the accepted ids. `NotesRepository.sync()` marks **all pending** notes as synced (`pending.map { it.id }`) instead of only `accepted`, and returns `accepted.size`. Notes 4+ lose their "•" marker but never reached the backend; a second sync finds nothing pending.
- **Visible symptom on device:** add 5 notes, tap Sync: the message says "Synced 3 note(s)" but all 5 markers disappear.
- **Failing test (2.5):** give the fake API a batch limit (or use `FakeSyncApi(latencyMs = 0)`), add 5 notes, sync, assert only the 3 accepted ids are `synced = true` and a second `sync()` pushes the remaining 2.
- **Good fix:** mark only `accepted`, and loop until nothing is pending (or until a request accepts 0, to avoid an endless loop).
- Note: the existing unit tests still pass because `RecordingSyncApi` has no batch limit. That's the lesson: the test fake didn't model the real contract.

## NOTE-103 — Text lost on rotation (NOT a bug in this app)
- The draft uses `rememberSaveable`, which survives rotation. It isn't reproducible on the emulator.
- The good outcome is "can't reproduce", with questions: which app version, which Samsung model and Android version, did the app get killed (process death), was it a split-screen or foldable change, was the keyboard a third-party one. Don't "fix" what you can't reproduce.

## NOTE-104 — Search (feature, ambiguous)
- Open questions: search text only or also done state; case/accent-insensitive; instant or on submit; empty-result state; does search include unsynced notes; where does the UI go; performance target ("a lot of notes" = how many?).
- Good first step: write Given/When/Then for the agreed scope before any code.

## NOTE-105 — Done notes to the bottom (feature, ambiguous)
- Open questions: move vs hide (the reporter isn't sure); ordering within the done group; animation; should undoing "done" move it back to its original position; is it a setting.
- Good first step: ask the reporter to choose between "move" and "hide", and propose a default.

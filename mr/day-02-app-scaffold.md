# feat(app): scaffold Dojo Notes Android training app

Branch: `day-02/app-scaffold` → `feat/training-plan` (local MR; GitLab unreachable)

## What
A small Android app in `android-app/` used for the rest of the training (tickets, seeded bugs, reviews, capstone).
- Kotlin, Jetpack Compose (Material 3), single `:app` module, MVVM with coroutines/Flow.
- Notes list: add, toggle done, and a "Sync" button. Notes are stored in Room.
- `FakeSyncApi` stands in for a backend with a configurable latency (default 1.5 s), so threading and ANR bugs can be seeded later.
- Manual dependency wiring in `NotesApp` (no DI framework).
- `.gitlab-ci.yml` at the repo root runs `lint` and `testDebugUnitTest`.

## Why
Day 2 task 2.0: the user chose an Android/Kotlin app instead of Node/TS (2026-10-19) to match their daily work.

## Key decisions
- Toolchain versions are copied from a reference project that already builds on this machine: AGP 9.4.1 (built-in Kotlin), Kotlin 2.4.21, KSP 2.3.12, Gradle 9.7.1, Room 2.8.5, Compose BOM 2026.06.01, lifecycle 2.11.0.
- `compileSdk = 37` because lifecycle 2.11.0 requires it; `targetSdk = 36`, `minSdk = 26`.
- `NotesScreen` is stateless (state in, callbacks out), so it can be UI-tested without a ViewModel.
- Unit tests use an in-memory `FakeNoteDao` and `RecordingSyncApi` instead of Room, so they run on the JVM.

## How tested
- `./gradlew testDebugUnitTest`: 6/6 pass (NotesRepositoryTest 4, NotesViewModelTest 2).
- `./gradlew lint`: passes.
- `./gradlew connectedDebugAndroidTest` on the Pixel_9_Pro emulator (Android 16): 2/2 Compose UI tests pass.
- Manual on the same emulator: launched the app, added "buy milk", tapped Sync; it showed "Synced 1 note(s)" and the unsynced marker cleared. No crash in logcat.
- Not tested: a physical device, rotation/process death, the CI file on a real GitLab runner.

## Risks & follow-ups
- The CI job downloads Android command-line tools by a pinned build number; check it on the first real GitLab run.
- No seeded tickets or bugs yet; that's the rest of task 2.0.

## Review checklist (reviewer: the user)
- [x] Builds from a clean clone: `cd android-app && ./gradlew testDebugUnitTest`
- [x] Code is readable and small enough to reason about
- [x] At least 3 actionable review comments recorded below (6)

## Review comments
_Drafted with Claude's help; accepted unchanged by the reviewer ("apply review", 2026-10-19)._

1. **[blocking]** `android-app/app/src/main/java/com/dojo/notes/ui/NotesViewModel.kt:42`: `catch (e: Exception)` also catches `CancellationException`. If the screen is closed mid-sync, cancellation is swallowed, the coroutine keeps running, and it can write "Sync failed: … was cancelled" into state. Fix: rethrow `CancellationException` (catch it first and rethrow), then handle other exceptions.
2. **[blocking]** `ui/NotesViewModel.kt:32` `toggleDone(note)`: it uses the `Note` snapshot from the UI. On a fast double tap, both calls compute `done = !false`, so the note stays done instead of toggling back, and any concurrent change to the row is overwritten. Fix: toggle by id in SQL, e.g. `@Query("UPDATE notes SET done = NOT done, synced = 0 WHERE id = :id")`.
3. **[suggestion]** `NotesApp.kt:11`: `FakeSyncApi()` uses a hard-coded 1.5 s latency. The plan says latency is configurable for the exercises, but today you have to edit code. Fix: a `buildConfigField("long", "SYNC_LATENCY_MS", "1500")` in the debug build type, passed into `FakeSyncApi`.
4. **[suggestion]** `test/java/com/dojo/notes/NotesViewModelTest.kt:22`: with `UnconfinedTestDispatcher` the sync runs eagerly, so no test checks that `isSyncing` is true while a sync is in flight, or that a second `sync()` is ignored. Fix: use `StandardTestDispatcher` with a fake whose `push` suspends on a `CompletableDeferred`, then assert the in-flight state and the double-tap guard.
5. **[suggestion]** `ui/NotesScreen.kt:49,55,61`: "Add", "Sync", "Syncing…" are hard-coded and can't be translated, and the "  •" unsynced marker is read by screen readers as "bullet". Fix: move the text to `strings.xml` and use `stringResource`; show an icon with `contentDescription = "Not synced"`.
6. **[question]** `.gitlab-ci.yml:11`: is command-line tools build `11076708` verified as current? Installing the SDK on every run is slow; would a prebuilt Android image or a cache be better?

## Author replies (round 1)
1. **Fixed.** `sync()` now rethrows `CancellationException` before the generic `catch`.
2. **Fixed.** New DAO query `toggleDone(id)` (`UPDATE notes SET done = NOT done, synced = 0 WHERE id = :id`); the repository and ViewModel pass the id, and the unused `update()` is removed. Test added: toggling twice returns to not done (on the fake DAO; the SQL itself is checked by Room at compile time but not exercised by a test).
3. **Fixed.** `BuildConfig.SYNC_LATENCY_MS` (default 1500) feeds `FakeSyncApi`. Override per build: `./gradlew installDebug -PsyncLatencyMs=5000`.
4. **Fixed, and it found a real bug.** Switched to `StandardTestDispatcher` and added a gated test for the in-flight state and the double tap. It **failed** against the original code: `isSyncing` was set inside the coroutine, so two quick taps both passed the guard and synced twice. `isSyncing` is now set before `launch`. I confirmed the test fails with the old order and passes with the new one.
5. **Fixed.** UI strings moved to `strings.xml` (`stringResource`); the "•" marker has `contentDescription = "Not synced"`. The ViewModel's "Synced N note(s)" message is still a hard-coded string (follow-up).
6. **Answered + fixed.** Build `11076708` existed but was old; the newest in Google's repository index is `16111833` (URL checked: HTTP 200). Also found that CI installed `platforms;android-36` while the app compiles against 37; it now installs `platforms;android-37.0`. Caching the SDK or using a prebuilt image is a follow-up for when GitLab is reachable.

## How tested (round 1)
- `./gradlew testDebugUnitTest lint`: pass (8 unit tests: repository 5, ViewModel 3; the new gated test was proven to fail on the old code).
- `./gradlew assembleDebug assembleDebugAndroidTest -PsyncLatencyMs=5000`: compiles.
- Not re-run: the Compose UI tests on the emulator (emulator stopped at the reviewer's request).

AI-assisted: scaffolded by Claude Code; built and tested on this machine as listed above.

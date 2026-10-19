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
- [ ] Builds from a clean clone: `cd android-app && ./gradlew testDebugUnitTest`
- [ ] Code is readable and small enough to reason about
- [ ] At least 3 actionable review comments recorded below

## Review comments

AI-assisted: scaffolded by Claude Code; built and tested on this machine as listed above.

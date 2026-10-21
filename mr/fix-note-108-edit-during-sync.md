# fix(app): a change made during Sync is no longer lost (NOTE-108)

Branch: `fix/note-108-edit-during-sync` → `feat/training-plan` (local MR; GitLab unreachable)
Closes: [NOTE-108](../tickets/NOTE-108.md)

_Day 4, Exercise 4.6 (full ticket loop). Planned in plan mode, approach chosen by the author (version column), implemented by Claude, steered by the author through a diff-pane hunk comment (`evidence/day-04/cp2-hunk-comment-steer.png`)._

## What
- **`Note.version`:** a new column (`@ColumnInfo(defaultValue = "0")`). `toggleDone` now increments it along with clearing `synced`.
- **Version-checked `markSynced`:** `UPDATE notes SET synced = 1 WHERE id = :id AND version = :version`. A row edited after it was pushed no longer matches, so it stays pending and goes out on the next sync.
- **One transaction per sync:** `NoteDao.markSynced(pushed: List<Note>)` is a `@Transaction` method that runs the per-row check for each pushed copy. The notes list sees one change per sync, not one per note. `NotesRepository.sync` passes the accepted copies, using `accepted.toSet()` for the `in` check.
- **Database v1 → v2:** `MIGRATION_1_2` runs `ALTER TABLE notes ADD COLUMN version INTEGER NOT NULL DEFAULT 0`. There is no destructive fallback, so existing notes are kept.
- **Schema export on:** `exportSchema = true`, with `room.schemaLocation = app/schemas`. `app/schemas/.../1.json` and `2.json` are committed, and the androidTest assets include the folder so the migration test can open v1.

Code: 8 changed files plus 3 new tests/schema files: `Note.kt`, `NoteDao.kt`, `NotesRepository.kt`, `NotesDatabase.kt`, `build.gradle.kts`, `libs.versions.toml`, `Fakes.kt`, `NotesRepositoryTest.kt`, new `NoteDaoTest.kt`, new `NotesMigrationTest.kt`, new `app/schemas/`.

**Start here:** `NotesRepository.sync()` and `NoteDao`:
```kotlin
// before
dao.markSynced(accepted)                       // UPDATE ... WHERE id IN (:ids), no check

// after
val acceptedIds = accepted.toSet()
dao.markSynced(pending.filter { it.id in acceptedIds })  // pushed copies, with their version

@Query("UPDATE notes SET synced = 1 WHERE id = :id AND version = :version")
suspend fun markSynced(id: Long, version: Int)

@Transaction
suspend fun markSynced(pushed: List<Note>) { pushed.forEach { markSynced(it.id, it.version) } }
```

## Why
`sync()` captures the pending notes, then pushes them, which takes about 4.5–6 s with the default 1.5 s latency. If the user toggled a note during the push, `toggleDone` set `synced = 0`, and then `markSynced` set it back to 1 by id. The backend kept the old `done`, the app showed the note as synced, and no later sync sent the change. That is silent data loss (High).

## Key decisions
- **Version column, not comparing content.** The author chose this in plan mode. Comparing `done` would need no migration, but it breaks quietly as soon as another field (such as text) becomes editable. A version covers every future edit, as long as each new write bumps it. That rule is written on `Note`; see follow-ups.
- **Per-row check inside one transaction.** Each row is checked on its own, so correctness doesn't need the transaction. The transaction is there so Room sends one invalidation per sync (the author's hunk-comment steer).
- **Message count unchanged (author decision).** `sync()` still returns `accepted.size`, which is what the backend received. So a note edited during the sync is counted in "Synced 1 note" but still shows "Not synced" afterwards. This is intended, not a bug.
- **Serialization constraint.** `room-testing` needs `kotlinx-serialization` 1.8.1, but androidTest is held to the app's 1.7.3 (from `room-runtime`). That mismatch made `MigrationTestHelper` crash with `AbstractMethodError`. A `constraints {}` entry raises the app to 1.8.1.
- **v1 schema generated after the fact.** v1 was never exported, so `1.json` was generated from the `feat/training-plan` code (681de2f) with `exportSchema` switched on temporarily, in a throwaway worktree.

## How tested
- **Red before green:** the two new `NotesRepositoryTest` cases failed on the old code ("edited during sync, so it must stay pending", "b was edited during sync").
  - With the version check removed from `FakeNoteDao`, they fail again.
  - With `DEFAULT 1` in the migration, `NotesMigrationTest` fails with `Migration didn't properly handle: notes`.
- **`./gradlew lint testDebugUnitTest`:** pass. This is the same command as `.gitlab-ci.yml`.
- **Instrumented, Pixel_9_Pro emulator (API 36):** `OK (8 tests)`.
  - `NoteDaoTest` (4) runs the real SQL: version bump, matching version, stale version, and a batch with one stale and one current note.
  - `NotesMigrationTest` (1) upgrades a real v1 database and validates it against the v2 schema; the data is kept and `version = 0`.
  - `NotesScreenTest` (3).
- **Manual upgrade:** installed the `feat/training-plan` build, added 2 notes, synced, then ticked one. Installed this branch over it without uninstalling. Both notes were kept with their state, and the app didn't crash. Screenshots: `evidence/day-04/v1-before-upgrade.png`, `v2-after-upgrade.png`.
- **Manual repro (debug build, `-PsyncLatencyMs=5000`):** toggled a note while "Syncing…" was showing. After the sync it still showed "Not synced", and the next sync sent it and cleared the marker. Screenshot: `evidence/day-04/note-108-after-toggle-during-sync.png`.
- **Not tested:**
  - The manual checks were run before the hunk-comment steer, so the `@Transaction` batch was covered only by the tests.
  - A physical device.
  - A real backend that receives the same note id twice. The design assumes the backend upserts.

## Risks & follow-ups
- Every future write to `notes` must bump `version`, or NOTE-108 comes back for that field. Consider a KDoc rule on `NoteDao` and a lint/test guard when text editing arrives.
- A note edited during a sync is sent again with its new state. The backend must treat a repeated id as an update. This holds for `FakeSyncApi`; it needs checking against a real API.
- The blank-token case still shows "Nothing to sync" while notes are pending (Day 4 claim 3, not yet filed).

## Review (Claude, before the author's steer)
Rendered with inline comments: `evidence/day-04/review-note-108-diff.png`. 1 medium, 2 low, 1 note, 1 ok.
1. **Medium:** the migration was only checked by hand. **Done:** schema export plus `NotesMigrationTest`, as the author asked.
2. **Low:** one write transaction per row; `accepted.toSet()`. **Done**, through the author's diff-pane hunk comment.
3. **Low:** the "`version` goes up on every local edit" rule needs to be visible where queries are written. **Open:** listed as a follow-up.

## Review checklist (reviewer: the author)
- [ ] Builds from a clean clone: `cd android-app && ./gradlew lint testDebugUnitTest`
- [ ] `connectedDebugAndroidTest` green on an emulator
- [ ] Could be reviewed from this description alone
- [ ] Approve, then merge `--no-ff` into `feat/training-plan`

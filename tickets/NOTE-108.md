# NOTE-108 — A change made while Sync is running is never sent

**Type:** Bug · **Reporter:** Dev (found in review of the NOTE-101/102 fix MR) · **Priority:** High (silent data loss)

If I tick or un-tick a note while a sync is in flight, the backend keeps the old state, the app shows the note as synced, and no later sync sends the change.

---
## Analysis (Claude-assisted)
- Ask: every local change must eventually reach the backend, even if it happens during a sync.
- Ambiguity: none on the expected result; the open question is the fix (row version, or compare-and-set when marking synced).
- First step: express it as a failing repository test (done below as a temporary test), then pick a fix.

## Repro (verified 2026-10-20 with a temporary JVM test on `fix/note-101-102-sync`; not yet on a device)
1. Note "a", not done, unsynced. Start `sync()`; hold `push` open (it has already captured "a" with `done=false`).
2. While `push` is in flight, `toggleDone(a)`: local row becomes `done=true, synced=false`.
3. Let `push` finish; it accepts id 1.
- Actual: backend received `done=false`; local row is `done=true, synced=true`; `unsynced()` is empty, so the next sync sends nothing.
- Expected: the row stays unsynced (or is re-sent) because it changed after it was captured.
- Root cause: `NotesRepository.sync` calls `dao.markSynced(accepted)` by id only (`UPDATE notes SET synced = 1 WHERE id IN (:ids)`), with no check that the row is still the version that was pushed. Existed before the NOTE-101/102 fix; the longer upload window (6 s) makes it easy to hit.

## Fix options (to discuss in the fix MR)
- Add a `version` (or `updatedAt`) column; `toggleDone` bumps it; `markSynced` only updates rows whose version matches the pushed copy.
- Or block edits while syncing (simpler, worse UX).

## Acceptance criteria (proposal)
- Given a sync is in flight with note A captured, when I toggle A, then after the sync A is still unsynced and the next sync sends the new state.
- Given no edits during the sync, then behavior is unchanged (accepted notes are marked synced).

## Status (2026-10-21)
- Merged f864492 (2026-10-21): [mr/fix-note-108-edit-during-sync.md](../mr/fix-note-108-edit-during-sync.md), branch `fix/note-108-edit-during-sync`.
- Approach: `version` column with a version-checked `markSynced` (one transaction per sync), plus the Room migration v1 to v2.
- Both acceptance criteria are covered by `NotesRepositoryTest` and `NoteDaoTest`, and the first was checked by hand on the emulator.
- Known, accepted: the result message counts what the backend accepted, so a note edited during the sync is counted but stays "Not synced".

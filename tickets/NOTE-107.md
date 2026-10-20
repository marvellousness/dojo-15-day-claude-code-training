# NOTE-107 — One sync doesn't send all notes, and the message doesn't say so

**Type:** Bug · **Reporter:** Dev (found in review of the NOTE-101/102 fix MR) · **Priority:** Medium

After the NOTE-102 fix, Sync sends one batch of at most 3 notes per tap. With 5 unsynced notes the first tap says "Synced 3 note(s)" and 2 notes stay unsynced, but nothing tells the user to tap again. Users will assume everything is synced.

---
## Analysis (Claude-assisted)
- Ask: after a sync, the user should know whether all notes went out, and ideally not need to tap more than once.
- Ambiguity: should one tap send everything (loop over batches), or should the app keep one batch per tap and show what's left? It's also open what happens if a later batch fails after an earlier one succeeded.
- First step: ask the PO to choose "loop until empty" or "show what's left", then write acceptance criteria.

## Repro (verified 2026-10-20, branch `fix/note-101-102-sync`, Pixel_9_Pro emulator, API 36)
1. Clear app data; add 5 notes.
2. Tap **Sync notes now**. Message: "Synced 3 note(s)". 2 notes still show the "Not synced" marker.
3. Tap again. Message: "Synced 2 note(s)".
- Expected: one tap syncs all 5, or the first message says 2 are left.
- Root cause: `FakeSyncApi.MAX_BATCH = 3`; `NotesRepository.sync` sends one batch per call.

## Questions for the PO
1. Loop until everything is sent, or one batch per tap with a "N left" message?
2. If batch 2 of 3 fails, what should the message say?
3. Should a progress indicator show batches ("3 of 5")?

## Acceptance criteria (proposal, to be confirmed by the PO)
- Given 5 unsynced notes, when I tap Sync once, then all 5 are synced and the message says "Synced 5 note(s)" (assumes "loop").
- Given 5 unsynced notes and the second batch fails, then the 3 accepted notes stay synced, the other 2 stay pending, and the message says "Synced 3 of 5; 2 not synced".

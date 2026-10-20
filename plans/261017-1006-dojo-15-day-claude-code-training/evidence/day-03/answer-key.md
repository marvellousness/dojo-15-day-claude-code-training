# Day 3 answer key — seeded issues in the Claude-authored MR (`claude/sync-message-resources`)

Don't read this until your review of `mr/claude-sync-message-resources.md` is written.

| # | Checklist step | Where | Issue | Expected comment |
|---|---|---|---|---|
| 1 | Correctness | `res/values/strings.xml`, `synced_notes` | Plurals has only `quantity="other"`, so English shows **"Synced 1 notes"**. | [blocking] Add `<item quantity="one">Synced %d note</item>`. |
| 2 | Correctness / scope | `NotesViewModel.sync()` | `if (count > 0) SyncResult.Synced(count) else null`: a sync with nothing to send now shows **no message**; before it showed "Synced 0 note(s)". A silent behavior change. | [blocking] Keep `Synced(0)` (and a "Nothing to sync" string), or document and justify the change. |
| 3 | Description | MR "Behavior" | Says "No behavior change" — false because of #1 (wording) and #2 (no message on 0). | [blocking] Fix the claim; describe the changes. |
| 4 | Correctness | `NotesScreen`, `Failed` branch | `result.reason.toString()` shows **"Sync failed: null"** when the exception has no message. | [suggestion] Fall back to a generic string when `reason == null`. |
| 5 | Tests | `NotesViewModelTest`, `NotesScreenTest` | No test for the 0-count case, and no test checks the rendered text (where #1 and #4 live). | [suggestion] Add a VM test for count 0 and a Compose test for "Synced 1 note" / "Synced 3 notes". |
| 6 | Tests / How tested | MR "How tested" | Only unit tests and lint; the UI text was never looked at on a device, and the Compose tests were compiled but not run. | [question] Was the message checked on the emulator? |

Pass bar for task 3.4: at least 3 actionable comments; finding #1 and #2 (or #3) counts as catching the seeded bugs.

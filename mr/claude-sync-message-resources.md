# feat(app): move the sync result message to string resources

Branch: `claude/sync-message-resources` → `feat/training-plan` (local MR; GitLab unreachable)
Author: Claude (Day 3 task 3.3/3.4: the Claude-authored MR for the user to review)
Related: follow-up from the Day 2 scaffold MR, review comment 5 ("Synced N note(s)" was still hard-coded in the ViewModel)

## What
- The ViewModel no longer builds user-facing text. `NotesUiState.message: String?` is replaced by `syncResult: SyncResult?`, a small sealed type: `Synced(count)` or `Failed(reason)`.
- `NotesScreen` turns the result into text with `pluralStringResource(R.plurals.synced_notes, …)` and `stringResource(R.string.sync_failed, …)`.
- New resources in `strings.xml`: `sync_failed`, `nothing_to_sync` and the `synced_notes` plurals (`one` and `other`).
- `NotesViewModelTest` asserts on `SyncResult` values instead of English strings; `NotesScreenTest` checks the rendered text.

## Why
Text built in the ViewModel can't be translated and ties the tests to English wording. Keeping the ViewModel free of `Context` means the JVM tests still run without Robolectric.

## Behavior
User-visible messages change (updated after review; the first version wrongly said "no behavior change"):

| Case | Before | After |
|---|---|---|
| Nothing to send | Synced 0 note(s) | Nothing to sync |
| 1 note | Synced 1 note(s) | Synced 1 note |
| 3 notes | Synced 3 note(s) | Synced 3 notes |
| Failure | Sync failed: <exception message, or "null"> | Sync failed. Try again. |

The failure reason is kept in `SyncResult.Failed.reason` for debugging but no longer shown, because raw exception text can contain backend internals.

## How tested
- `./gradlew testDebugUnitTest lint`: pass (12 unit tests, including the new count-0 test).
- `./gradlew connectedDebugAndroidTest` on the Pixel_9_Pro emulator (Android 16): 3/3 pass. The new `syncResultIsShownAsTranslatableText` covers 0, 1, 3 notes and two failures (with an internal-looking message, and with `null`).
- Red before green: with the `quantity="one"` line removed, `connectedDebugAndroidTest` fails; with it restored, it passes.
- Manual, same emulator (`-PsyncLatencyMs=300`, clean data): Sync with no notes shows "Nothing to sync"; 1 note shows "Synced 1 note"; 3 notes show "Synced 3 notes".
- Not tested: other languages (only English strings exist), a failure on a device (covered by the Compose test only).

## Review checklist (reviewer: the user)
- [ ] Builds from a clean clone: `cd android-app && ./gradlew testDebugUnitTest lint`
- [ ] Reviewed with the Day 3 checklist: correctness → tests → readability → security basics → scope
- [ ] At least 3 actionable review comments recorded below

## Review comments
_Reviewer: the user. Drafted by Claude at the user's request ("help me complete the review", 2026-10-20) using `docs/review-checklist.md`. Claude also wrote this MR and its answer key, so this is not an independent review._

**Verdict: changes requested** (3 blocking). → Round 1 replies below.

1. **[blocking] Correctness** · `android-app/app/src/main/res/values/strings.xml:9-11`: the `synced_notes` plurals only has `quantity="other"`. In English a count of 1 uses `one`, falls back to `other`, and shows **"Synced 1 notes"**. Fix: add `<item quantity="one">Synced %d note</item>`.
2. **[blocking] Correctness / scope** · `android-app/app/src/main/java/com/dojo/notes/ui/NotesViewModel.kt:50`: `if (count > 0) SyncResult.Synced(count) else null` means a sync with nothing to send now shows **no message at all**. Before this MR it showed "Synced 0 note(s)" (seen on the emulator on Day 3), so a tap on Sync gives no feedback. Fix: keep `SyncResult.Synced(0)` and add a "Nothing to sync" string (or a `zero`-style branch in the screen), or explain the change in the description.
3. **[blocking] Description** · `mr/claude-sync-message-resources.md:17`: "No behavior change" is wrong because of comments 1 and 2. Fix: list the before/after messages for 0, 1, 3 notes and a failure.
4. **[suggestion] Correctness + security basics** · `android-app/app/src/main/java/com/dojo/notes/ui/NotesScreen.kt:64`: `result.reason.toString()` shows **"Sync failed: null"** when the exception has no message, and when it does, the raw exception text (which with a real backend can contain server internals) goes straight to the user. Fix: show a generic `sync_failed` string ("Sync failed. Try again.") and log the reason instead.
5. **[suggestion] Tests** · `android-app/app/src/test/java/com/dojo/notes/NotesViewModelTest.kt:34-57`: tests only cover `Synced(1)` and `Failed("offline")`. Nothing covers count 0 (comment 2), and nothing checks the rendered text, which is where comments 1 and 4 live. Fix: a ViewModel test for 0, and a Compose test in `NotesScreenTest` for "Synced 1 note" and "Synced 3 notes".
6. **[question] How tested** · `mr/claude-sync-message-resources.md:21`: `assembleDebugAndroidTest` only compiles the UI tests. Were they run, and was the message looked at on the emulator for 0, 1 and 3 notes?

## Author replies (round 1)
_Claude, the author, 2026-10-20, at the user's request ("help me replies 6 review comments and complete them")._

1. **Fixed.** Added `<item quantity="one">Synced %d note</item>`. Shown on the emulator as "Synced 1 note"; the new Compose test fails without the line and passes with it.
2. **Fixed.** The ViewModel always sets `SyncResult.Synced(count)`, including 0. The screen shows a new `nothing_to_sync` string ("Nothing to sync") for 0, so a tap always gives feedback. New ViewModel test: `sync with nothing to send still reports a result`.
3. **Fixed.** "Behavior" now has a before/after table for 0, 1, 3 notes and a failure.
4. **Fixed.** The user sees a fixed "Sync failed. Try again." whatever the exception says, including `null`. The reason stays in `SyncResult.Failed.reason` for debugging. Not logged yet: the project has no logging setup, and `android.util.Log` in the ViewModel would break the JVM tests; left as a follow-up.
5. **Fixed.** ViewModel test for count 0, and a Compose test that checks the rendered text for 0, 1, 3 and two failures.
6. **Answered.** The first version only compiled the UI tests and nobody looked at the screen. Now: `connectedDebugAndroidTest` 3/3 on the emulator, plus a manual check of 0, 1 and 3 notes (see "How tested").

**Reviewer: approved** ("approve the replies", the user, 2026-10-20). Merged with `--no-ff` into `feat/training-plan`.

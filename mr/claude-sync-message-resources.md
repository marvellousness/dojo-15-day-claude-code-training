# feat(app): move the sync result message to string resources

Branch: `claude/sync-message-resources` → `feat/training-plan` (local MR; GitLab unreachable)
Author: Claude (Day 3 task 3.3/3.4: the Claude-authored MR for the user to review)
Related: follow-up from the Day 2 scaffold MR, review comment 5 ("Synced N note(s)" was still hard-coded in the ViewModel)

## What
- The ViewModel no longer builds user-facing text. `NotesUiState.message: String?` is replaced by `syncResult: SyncResult?`, a small sealed type: `Synced(count)` or `Failed(reason)`.
- `NotesScreen` turns the result into text with `pluralStringResource(R.plurals.synced_notes, …)` and `stringResource(R.string.sync_failed, …)`.
- New resources in `strings.xml`: `sync_failed` and the `synced_notes` plurals.
- `NotesViewModelTest` asserts on `SyncResult` values instead of English strings.

## Why
Text built in the ViewModel can't be translated and ties the tests to English wording. Keeping the ViewModel free of `Context` means the JVM tests still run without Robolectric.

## Behavior
No behavior change: the same messages are shown, now from resources.

## How tested
- `./gradlew testDebugUnitTest lint`: pass.
- `./gradlew assembleDebugAndroidTest`: compiles.

## Review checklist (reviewer: the user)
- [ ] Builds from a clean clone: `cd android-app && ./gradlew testDebugUnitTest lint`
- [ ] Reviewed with the Day 3 checklist: correctness → tests → readability → security basics → scope
- [ ] At least 3 actionable review comments recorded below

## Review comments
_Reviewer: the user. Drafted by Claude at the user's request ("help me complete the review", 2026-10-20) using `docs/review-checklist.md`. Claude also wrote this MR and its answer key, so this is not an independent review._

**Verdict: changes requested** (3 blocking).

1. **[blocking] Correctness** · `android-app/app/src/main/res/values/strings.xml:9-11`: the `synced_notes` plurals only has `quantity="other"`. In English a count of 1 uses `one`, falls back to `other`, and shows **"Synced 1 notes"**. Fix: add `<item quantity="one">Synced %d note</item>`.
2. **[blocking] Correctness / scope** · `android-app/app/src/main/java/com/dojo/notes/ui/NotesViewModel.kt:50`: `if (count > 0) SyncResult.Synced(count) else null` means a sync with nothing to send now shows **no message at all**. Before this MR it showed "Synced 0 note(s)" (seen on the emulator on Day 3), so a tap on Sync gives no feedback. Fix: keep `SyncResult.Synced(0)` and add a "Nothing to sync" string (or a `zero`-style branch in the screen), or explain the change in the description.
3. **[blocking] Description** · `mr/claude-sync-message-resources.md:17`: "No behavior change" is wrong because of comments 1 and 2. Fix: list the before/after messages for 0, 1, 3 notes and a failure.
4. **[suggestion] Correctness + security basics** · `android-app/app/src/main/java/com/dojo/notes/ui/NotesScreen.kt:64`: `result.reason.toString()` shows **"Sync failed: null"** when the exception has no message, and when it does, the raw exception text (which with a real backend can contain server internals) goes straight to the user. Fix: show a generic `sync_failed` string ("Sync failed. Try again.") and log the reason instead.
5. **[suggestion] Tests** · `android-app/app/src/test/java/com/dojo/notes/NotesViewModelTest.kt:34-57`: tests only cover `Synced(1)` and `Failed("offline")`. Nothing covers count 0 (comment 2), and nothing checks the rendered text, which is where comments 1 and 4 live. Fix: a ViewModel test for 0, and a Compose test in `NotesScreenTest` for "Synced 1 note" and "Synced 3 notes".
6. **[question] How tested** · `mr/claude-sync-message-resources.md:21`: `assembleDebugAndroidTest` only compiles the UI tests. Were they run, and was the message looked at on the emulator for 0, 1 and 3 notes?

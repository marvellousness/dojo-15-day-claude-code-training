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
_(reviewer: add yours here)_

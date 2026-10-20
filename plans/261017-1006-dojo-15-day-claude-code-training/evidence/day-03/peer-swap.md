# Day 3 task 3.3 — peer swap (solo: Claude ↔ the user), 2026-10-20

## A. Claude reads the user's MR: `mr/fix-note-101-102-sync.md` (branch `fix/note-101-102-sync`)
Question: could I review this from the description alone, before opening the diff?

**Verdict: yes.** What, why (with root causes linked to the tickets and hypotheses), key decisions with a rejected alternative, how tested (commands, red-before-green, manual emulator steps, what wasn't tested), and risks with tickets. I know what to look for before reading code.

Gaps found (reading the description first, then checking it against the branch):
1. **Stale size line.** It says "3 files, +71 / −7". The branch is now 7 files, +176 / −7, because the MR file and NOTE-107/108 were added later (`git diff --stat feat/training-plan...fix/note-101-102-sync`). Fix: update the line, or split code vs. docs ("code: 3 files +71/−7; plus MR and 2 tickets").
2. **No "where to start" pointer or snippet.** The core change is 4 lines in `NotesRepository.sync()`. A short before/after snippet would let a reviewer check the fix without opening the diff.
3. **Rewritten test is described but not shown.** The reasoning for replacing the Day 2 NOTE-101 test is clear; the 300 ms / 100 ms timing margins are only visible in code. Worth one line on why those numbers are safe (token 200 ms, upload 400 ms).

None of these block review. **All 3 applied** to the MR on 2026-10-20 at the user's request (branch `fix/note-101-102-sync`): size line split into code vs. docs, a "Start here" before/after snippet, and the timing reasoning.

## B. The user reads Claude's MR: `mr/claude-sync-message-resources.md` (branch `claude/sync-message-resources`)
_Drafted by Claude at the user's request ("help me complete the review", 2026-10-20). Claude wrote this MR and its answer key, so this is not a blind read._

**Verdict: partly.** What and why are clear, and it links the follow-up it comes from. But I couldn't approve it from the description alone:
1. **"No behavior change" is a claim with no evidence.** Moving text to resources and changing the state type both can change what users see; the description should show before/after messages for 0, 1, many and a failure.
2. **"How tested" only covers compiling and unit tests.** The change is about on-screen text, but nothing says the text was looked at on a device or checked by a Compose test. `assembleDebugAndroidTest` compiles the UI tests; it doesn't run them.
3. **No risks or follow-ups section**, e.g. translations still to add, or what happens when the error has no message.

# Day 3 task 3.3 — peer swap (solo: Claude ↔ the user), 2026-10-20

## A. Claude reads the user's MR: `mr/fix-note-101-102-sync.md` (branch `fix/note-101-102-sync`)
Question: could I review this from the description alone, before opening the diff?

**Verdict: yes.** What, why (with root causes linked to the tickets and hypotheses), key decisions with a rejected alternative, how tested (commands, red-before-green, manual emulator steps, what wasn't tested), and risks with tickets. I know what to look for before reading code.

Gaps found (reading the description first, then checking it against the branch):
1. **Stale size line.** It says "3 files, +71 / −7". The branch is now 7 files, +176 / −7, because the MR file and NOTE-107/108 were added later (`git diff --stat feat/training-plan...fix/note-101-102-sync`). Fix: update the line, or split code vs. docs ("code: 3 files +71/−7; plus MR and 2 tickets").
2. **No "where to start" pointer or snippet.** The core change is 4 lines in `NotesRepository.sync()`. A short before/after snippet would let a reviewer check the fix without opening the diff.
3. **Rewritten test is described but not shown.** The reasoning for replacing the Day 2 NOTE-101 test is clear; the 300 ms / 100 ms timing margins are only visible in code. Worth one line on why those numbers are safe (token 200 ms, upload 400 ms).

None of these block review.

## B. The user reads Claude's MR: `mr/claude-sync-message-resources.md` (branch `claude/sync-message-resources`)
_To be filled by the user: could you review it from the description alone? What's missing?_

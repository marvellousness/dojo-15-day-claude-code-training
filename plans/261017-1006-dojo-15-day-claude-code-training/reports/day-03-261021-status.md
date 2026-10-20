# Daily Status — Day 03 (2026-10-21)

**Topic:** Fix, review, merge · **Status:** done · **Tasks:** 9/9 · **Closed:** 2026-10-20 (one day early) · **CP1: pass** (review Claude-drafted, see below)

## Done today
- 3.1: fixed NOTE-102 (mark only the accepted ids as synced) and NOTE-101 (the network calls now run off the main thread through an injectable `ioDispatcher`) on `fix/note-101-102-sync`, in small commits. The Day 2 NOTE-101 test could never pass (wall-clock timing under `runTest`), so it was rewritten to check that the calling thread stays free. It was proven red on the old code and green on the fix.
- 3.2: MR description drafted by Claude and accepted by me unchanged. It was verified on the emulator: 5 notes synced as 3 then 2, with no ANR while tapping during sync. The two risks it found were filed as NOTE-107 (one sync doesn't send everything) and NOTE-108 (an edit during sync is lost; confirmed with a temporary test, High).
- 3.3: peer swap. Claude read my MR: it can be reviewed from the description alone, with 3 small gaps, all applied. My read of Claude's MR (Claude-drafted at my request): only partly reviewable, because "No behavior change" was unproven, "How tested" covered compiling only, and there were no risks.
- 3.4: `docs/review-checklist.md` (correctness → tests → readability → security basics → scope); 6 review comments on Claude's MR `claude/sync-message-resources`, covering all 6 seeded issues plus a security point.
- 3.5: a seeded teammate commit (a blank-token guard) conflicted with the NOTE-101 fix during the rebase. Resolved by keeping both, with a new test added; merged with `--no-ff` (19048f1) and the branch deleted.
- Extra: Claude answered the 6 review comments and fixed them, adding the singular form, "Nothing to sync", a generic failure text, a ViewModel test and a Compose text test (proven red without the fix). It was checked on the emulator, approved by me, and merged (c67468c).

## Evidence
- MRs:
  - `mr/fix-note-101-102-sync.md` (mine, merged 19048f1, with a conflict resolution section)
  - `mr/claude-sync-message-resources.md` (Claude's, with 6 comments, author replies and approval, merged c67468c)
- Notes: `evidence/day-03/peer-swap.md`; `docs/review-checklist.md` (skill seed for Day 7); tickets `NOTE-107.md`, `NOTE-108.md`.
- Branches: the fix and Claude MR branches are deleted; `day-02/failing-tests` is deleted (its test was merged, rewritten). `answer-key/day-03` is kept.

## Courses
- None scheduled for Day 3.

## Blockers
- GitLab is still unreachable, so the MRs are local and nothing has been pushed. Owner: me.

## Learnings
- **Prompt change that helped:** asking for "red before green" every time. Reverting the fix and re-running showed that the new tests really catch the bug, and it exposed that the Day 2 NOTE-101 test could never pass.
- **Where Claude surprised me (good):** it found a real, pre-existing data-loss bug (NOTE-108) while listing risks for the MR, and confirmed it with a test before filing it.
- **Where Claude surprised me (bad):** the first MR it wrote for me to review claimed "No behavior change" while changing three messages. A confident description is not evidence; the before/after table is.
- **Honest note on CP1:** the review counted for CP1 and part B of the peer swap were drafted by Claude at my request. Claude also wrote the seeded bugs and the answer key, so that review was not blind. The conflict resolution was Claude-assisted too. My own practice was accepting, steering and approving the work.
- **Git:** after `git rebase --onto`, the old branch isn't "merged" by hash even when its content is, so it needs `branch -D` after checking the content.

## Checkpoint status
- CP1 (Day 3): **pass, 4/4**. The repro is in NOTE-101/102; the fix and tests are on a branch; the MR is self-contained; the review has 6 comments; the merge had a resolved conflict.
- CP2 (Day 6, Sat 2026-10-24): 0/5.

## Plan tomorrow (Day 04, Thu 2026-10-22)
- 4.1–4.4: Claude Code desktop and CLI basics, permission modes, plan mode, `/resume`.
- 4.5: explore the codebase and verify 2 claims by reading the code.
- 4.6–4.7: a full ticket loop twice (plan mode, hunk-comment steer, tests, MR). Candidates: NOTE-103 (rotation loses the draft) and NOTE-108 (edit during sync lost).
- 4.8: the Claude Code 101 and Claude Code in action courses.

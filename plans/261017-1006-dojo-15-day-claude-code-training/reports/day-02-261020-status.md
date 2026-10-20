# Daily Status — Day 02 (2026-10-20)

**Topic:** Ticket analysis, reproduce · **Status:** done · **Tasks:** 8/8 · **Closed:** 2026-10-20

## Done today
- 2.0: scaffolded the Dojo Notes Android app (Kotlin, Compose, Room, JUnit and Compose UI tests, `.gitlab-ci.yml`). Reviewed it as a local MR with 6 comments, applied the fixes and merged it. Claude then seeded 5 tickets (NOTE-101 to NOTE-105) and two hidden bugs.
- 2.1–2.2: wrote a three-sentence analysis and reporter questions for each ticket, then applied the review. Role-played the NOTE-101 reporter with Claude.
- 2.3: reproduced both seeded bugs on the emulator (Pixel 9 Pro, API 36) and wrote exact repro steps into the tickets:
  - NOTE-101: an ANR when syncing 3 or more notes, with the main-thread trace from `adb bugreport` and the repro rate.
  - NOTE-102: the app says "Synced 3" but marks all 5 notes synced, and a second sync shows "Synced 0".
- 2.4: listed 7 hypotheses and checked each one by running a temporary JVM test. 4 were confirmed and 2 rejected (the database and the token call). H2 was confirmed with a correction: notes block for (n+1) × latency, capped at 3 notes.
- 2.5: turned both bugs into failing tests on branch `day-02/failing-tests` (`SyncContractTest`). After rebasing onto `feat/training-plan`, 8 tests pass and the 2 contract tests fail, as intended.
- 2.6: git block with branches only. Read `log A..B` and `diff A...B`, rebased onto a simulated teammate branch, resolved conflict #1 as "Sync notes now", then merged with `--no-ff`.
- 2.7: finished Claude 101 and earned its badge. Wrote a detailed summary and an email snippet from the lesson content.

## Evidence
- MR: `mr/day-02-app-scaffold.md` (local MR with review comments, author replies and "How tested"; merged).
- Tickets: `tickets/NOTE-101.md` … `NOTE-105.md` (analyses, reporter questions, repro steps for 101 and 102).
- Notes:
  - `evidence/day-02/hypotheses.md`
  - `evidence/day-02/git-block.md`
  - `evidence/day-02/claude-101-course-summary.md` and `claude-101-email-snippet.md`
- Branches: `day-02/failing-tests` (rebased onto the Day 2 close commit, red on purpose), `answer-key/day-02`.

## Courses
- Claude 101 — finished · badge https://academy.claude.com/badges/60b6e887-8a60-479b-a21f-423bb97e1915

## Blockers
- GitLab is still unreachable, so MRs stay local and nothing has been pushed. Owner: me.

## Learnings
- **Prompt change that helped:** giving Claude the trace, the repro and the code together, then asking for hypotheses that can each be proven wrong by running something. That gave a ranked list I could check, instead of one confident guess.
- **Where Claude surprised me (good):** a new gated test found a real double-tap bug in the sync guard (`isSyncing` was set inside `launch`). The test failed on the old code and passed on the fix.
- **Where Claude surprised me (bad):** it made claims it had not run. "The second sync finds nothing" was asserted before anyone checked it, a test count was wrong (9, not 8), and a repro hint ("tap right away") was wrong about timing.
- **Test fakes matter:** the original fake API had no batch limit and never blocked, so no existing test could catch either bug. The new contract tests model the real backend.
- **Git:** during a rebase, `HEAD` in a conflict is the upstream side, not my commit.

## Checkpoint status
- CP1 (Day 3, Wed 2026-10-21): 1/4. The repro is in the tickets. Still needed: a fix with tests on a branch, a self-contained MR, a review with at least 3 comments, and a merge with a resolved conflict.

## Plan tomorrow (Day 03, Wed 2026-10-21)
- Fix NOTE-101 (move the blocking upload off Main) and NOTE-102 (mark only the accepted ids) on `day-02/failing-tests` until `SyncContractTest` passes.
- Write the MR (what, why, how tested). Review it with at least 3 actionable comments, and apply them.
- Resolve the seeded conflict, merge and delete the branch. Close CP1.
- Follow-ups: move the hard-coded "Synced N" text to a string resource, add a build id, check the NOTE-101 repro rate by hand.

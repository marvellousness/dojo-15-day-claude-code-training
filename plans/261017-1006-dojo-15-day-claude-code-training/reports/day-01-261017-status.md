# Daily Status — Day 01 (2026-10-17)

**Topic:** Mental model + Claude basics · **Status:** done (1.4 carried to Day 2) · **Tasks:** 6/7 · **Closed:** 2026-10-19

## Done today
- Setup check: claude.ai sign-in OK, Claude Desktop installed, Claude Code CLI 2.1.302, Node 22 / npm 11 ready.
- Finished **AI Fluency: Framework and foundations** and **AI capabilities and limitations**, quizzes passed, badges earned.
- Git block done locally: branch, commits, `--no-ff` merge, branch deleted.
- Prompt exercise in claude.ai, Android-themed:
  - Q1: bug summary of an ANR, 3/5 → 4/5.
  - Q2: explaining ANR to a junior developer, 3/5 → 4/5.
  - Q3: GitLab MR description for the fix, 3/5 → 4/5.
- Reflection written (1.7). Detailed and email summaries written for both courses from the full lesson transcripts.

## Evidence
- MR: none yet; local git only (`day-01/git-block` merged into `feat/training-plan`).
- Notes: `evidence/day-01/prompt-notes.md` (setup check, Q1–Q3 prompts, answers, checklists, scores, reflection).
- Course summaries: `evidence/day-01/ai-fluency-course-summary.md`, `evidence/day-01/ai-capabilities-limitations-course-summary.md`, plus email snippets.

## Courses
- AI Fluency: Framework and foundations — finished · badge https://academy.claude.com/badges/2914b0a2-b3bd-412e-830f-cef084cd4e78
- AI capabilities and limitations — finished · badge https://academy.claude.com/badges/6695dbe2-cf03-42bd-95cc-face36fd48a0
- Claude 101 — not started; carried to Day 2 (task 2.7)

## Blockers
- GitLab (`gitlab.asoft-python.com`) is unreachable from this machine (SSH and HTTPS time out). Action: work on local git only and simulate MRs; push once the network or VPN allows. Owner: me.
- Behind schedule: Day 1 closed on 2026-10-19, two days late. Action: decide whether to shift the calendar or catch up.

## Learnings
- **Prompt change that helped:** stating the audience, goal, fixed format and word limit, and asking for evidence (file:line, bad → good code). This turned essays into usable tickets, explanations and MRs.
- **Where Claude surprised me (good):** it caught a code/trace mismatch in my own input. It added correct risks I hadn't asked for: rotating refresh tokens, the refresh-failure retry chain, the Interceptor bridge.
- **Where Claude surprised me (bad):** small invented details slipped past the guards: a fake version "1.2.3", a unit test that was never written, "only Pay was checked". It also confidently mislabelled the 20 s service timeout.
- **My own mistakes:** I sent template placeholders unfilled twice and gave a partial diff. Many model gaps started with my input.
- **Verified vs trusted:** I checked every specific (numbers, versions, test claims) against answer keys, the trace, the code and the docs. "How tested" stays unverified until I check it line by line.

## Checkpoint status
- CP1 (Day 3): 0/4. It needs a repro in a ticket, a fix plus test, an MR, a review and a merge with a resolved conflict (Days 2–3).

## Plan tomorrow (Day 02)
- 2.0: scaffold the training app (Node + TS, Express, Vitest, Playwright, CI file); Claude seeds 5 tickets and a hidden bug.
- 2.1–2.5: ticket analysis, clarifying questions, repro steps, hypotheses verified by running, the bug as a failing test.
- 2.6: git block, covering `pull --rebase`, log and diff, and merge conflict #1.
- 2.7: Claude 101, start and finish.

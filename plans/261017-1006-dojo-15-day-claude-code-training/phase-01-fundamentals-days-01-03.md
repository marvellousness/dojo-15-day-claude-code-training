---
phase: 1
title: "Shared fundamentals (Days 1–3)"
status: in-progress
dates: 2026-10-17 → 2026-10-21
checkpoint: CP1 (Day 3)
---

# Phase 01 — Shared fundamentals

**Goal:** take a ticket from read → reproduce → fix → MR → review → merge, using git properly and Claude as a working tool.

## Day 1 — Sat 2026-10-17 — Mental model + Claude basics
Courses: AI Fluency: Framework and foundations · AI capabilities and limitations · Claude 101 (start)

- [x] 1.1 Setup check: claude.ai sign-in works, Desktop app installed, this repo cloned
- [x] 1.2 Course: AI Fluency (4D: Delegation, Description, Discernment, Diligence). Badge: https://academy.claude.com/badges/2914b0a2-b3bd-412e-830f-cef084cd4e78
- [x] 1.3 Course: AI capabilities and limitations. Badge: https://academy.claude.com/badges/6695dbe2-cf03-42bd-95cc-face36fd48a0
- [ ] 1.4 Course: start Claude 101 (first conversation, prompting, projects/artifacts). **Carried to Day 2 (2.7)** on 2026-10-19
- [x] 1.5 Git block (local): branch, commit, `--no-ff` merge, delete branch. Push deferred until GitLab reachable
- [x] 1.6 Exercise: in Claude chat, get a useful answer to 3 real work questions: one bug summary, one "explain this concept", one drafting task
- [x] 1.7 Note which prompt changes improved each answer; note where output surprised you (good and bad)

Evidence: `evidence/day-01/prompt-notes.md`, MR link from git block.

## Day 2 — Tue 2026-10-20 — Ticket analysis, reproduce & understand
Courses: finish Claude 101

- [x] 2.0 Solo prep: scaffold the Android training app in `android-app/` (Kotlin, Compose, MVVM + coroutines, Room notes, fake auth/sync API with delay, JUnit + coroutines-test, one Compose UI test, `.gitlab-ci.yml`); build and run it on the emulator. Claude seeds 5 tickets (`tickets/`) and ≥1 seeded bug on a hidden branch
- [x] 2.1 Workshop: 5 tickets × three-sentence analysis (ask, ambiguity, first step). Use Claude to find gaps and draft clarifying questions
- [x] 2.2 Exercise: write reporter questions; role-play reporter/dev with Claude
- [x] 2.3 Workshop: run app from clean clone, reproduce seeded bug, write exact repro steps into the ticket (steps, expected vs actual, environment)
- [x] 2.4 Claude habit: paste stack trace/code, ask hypotheses, verify each by running
- [x] 2.5 Exercise: express bug as failing test (branch `day-02/failing-tests`: `SyncContractTest`, red for NOTE-101 and NOTE-102)
- [x] 2.6 Git block: `pull --rebase`, read `git log` / `git diff`, merge conflict #1 (Claude seeds it)
- [ ] 2.7 Course: Claude 101, start and finish (includes 1.4, carried from Day 1)

Evidence: ticket analyses, repro in ticket, failing-test commit, conflict resolution commit.

## Day 3 — Wed 2026-10-21 — Fix, review, merge · **Checkpoint 1**
- [ ] 3.1 Workshop: fix Day-2 bug on a branch, small commits, test included
- [ ] 3.2 Exercise: MR description (what / why / how tested / screenshots). Draft with Claude, edit by hand. Open MR
- [ ] 3.3 Peer swap (solo): Claude reads your MR and says whether it could review from description alone; you do the same on a Claude-authored MR
- [ ] 3.4 Workshop: review checklist (correctness → tests → readability → security basics → scope). Review the Claude-authored MR with ≥3 actionable comments
- [ ] 3.5 Exercise: resolve seeded conflict on your MR, merge, delete branch

### CP1 pass criteria
- [ ] Reproduce assigned bug, repro written in ticket
- [ ] Fix on a branch with a test
- [ ] MR with self-contained description
- [ ] One review with ≥3 actionable comments + one merge with conflict resolved

Fail → redo failed item as homework Days 4–5.
Skill seed: write the Day-3 review checklist down (`docs/review-checklist.md`); it becomes a skill on Day 7.

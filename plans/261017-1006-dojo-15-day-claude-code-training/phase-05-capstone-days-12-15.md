---
phase: 5
title: "Capstone (Days 12–15)"
status: todo
dates: 2026-10-30 → 2026-11-02
checkpoint: Final (Day 15)
---

# Phase 05 — Capstone

Mini-product: one feature for the training app, briefed vaguely by the "client" (Claude, solo mode). User plays BA → QA → Dev in turn.
Rule: every work artifact (spec, tests, MRs, notes) is Claude-assisted and human-edited.

## Day 12 — Fri 2026-10-30 — Kickoff and planning
Optional course: finish AI-native SDLC playbook

- [ ] 12.1 Client brief (Claude writes it vague on purpose) → `capstone/brief.md`
- [ ] 12.2 BA intake: extract requirements, send open questions back to client
- [ ] 12.3 Ticket pack: user story, Given/When/Then criteria, open questions; pass "zero-questions test"
- [ ] 12.4 Spec artifact published
- [ ] 12.5 QA: test matrix from acceptance criteria
- [ ] 12.6 Dev questions only via tickets

## Day 13 — Sat 2026-10-31 — Build
- [ ] 13.1 Plan mode first for each ticket; approve plan before code
- [ ] 13.2 Parallel worktree sessions, one MR per ticket
- [ ] 13.3 QA: automated suite (Vitest + Playwright) authored alongside
- [ ] 13.4 BA: update spec as questions resolve

## Day 14 — Sun 2026-11-01 — Test and harden
- [ ] 14.1 QA: regression + automated suite in GitLab CI; `/loop` babysits the pipeline
- [ ] 14.2 Dev: `/code-review` (ultrareview if auth/data touched); fix findings
- [ ] 14.3 BA: client-facing release notes artifact from merged MRs

## Day 15 — Mon 2026-11-02 — Review, merge, demo · **Final checkpoint**
Optional course: Building effective human-agent teams

- [ ] 15.1 Cross-review: dev reviews test code; QA reviews a dev MR; BA checks merged behavior vs criteria, gaps → same-day tickets
- [ ] 15.2 Merge, tag release, run deploy
- [ ] 15.3 Remote Control / Dispatch demo: approve + steer a session from phone
- [ ] 15.4 10-min demo of the flow (record): ticket → plan → MR → review → tests → artifact → shipped
- [ ] 15.5 Retro: what to standardize (CLAUDE.md rules, skills, hooks, review checklist), each as a repo issue with owner
- [ ] 15.6 Personal playbook: one-page "how I work with Claude", shared as artifact

### Final pass criteria ("Company-ready")
- [ ] Capstone shipped end-to-end
- [ ] CP1, CP2, CP3 checklists all green

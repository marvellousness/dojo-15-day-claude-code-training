---
phase: 2
title: "Claude Code Desktop core (Days 4–6)"
status: todo
dates: 2026-10-20 → 2026-10-22
checkpoint: CP2 (Day 6)
---

# Phase 02 — Claude Code Desktop core

**Goal:** Code tab becomes primary dev environment; memory/context under control; flagship features + parallel work.

## Day 4 — Tue 2026-10-20 — Install, tour, first session, daily loop
Courses: Claude Code 101 · Claude Code in action. Docs: quickstart, desktop get-started, common workflows, best practices.

- [ ] 4.1 Install/verify Desktop Code tab on this repo; session basics (sidebar, resume, one-topic-per-session)
- [ ] 4.2 Permission modes tour: default / acceptEdits / plan / auto on a throwaway change
- [ ] 4.3 Plan mode: Shift+Tab until indicator shows "plan"
- [ ] 4.4 Resume: `/resume` in session, `claude --resume` in terminal
- [ ] 4.5 Exercise 1: "Explore this codebase and explain how feature X works", then verify 2 claims by reading code
- [ ] 4.6 Exercise 2: bug ticket full loop: paste ticket → plan mode → approve → implement → diff pane hunk comment steer → run tests → MR
- [ ] 4.7 Repeat Exercise 2 on a second ticket with no help
- [ ] 4.8 Courses: Claude Code 101 + Claude Code in action

Evidence: claim-verification notes, 2 MR links, screenshot of hunk-comment steer (counts toward CP2).

## Day 5 — Wed 2026-10-21 — Memory & context
- [ ] 5.1 Write repo `CLAUDE.md`: build/run commands, conventions, "never do" list, under one screen
- [ ] 5.2 Auto memory + compaction: long session, `/compact`, observe what survives; inspect `/memory`
- [ ] 5.3 Checkpointing: make a bad edit, Esc Esc → `/rewind`
- [ ] 5.4 Exercise: same task in fresh session vs session that read the codebase; compare quality + token use

Evidence: CLAUDE.md MR, comparison table in `evidence/day-05/context-comparison.md`.

## Day 6 — Thu 2026-10-22 — Flagship features + parallel · **Checkpoint 2**
Course: Introduction to subagents

- [ ] 6.1 Artifacts: publish MR walkthrough artifact from a session
- [ ] 6.2 `/loop 15m` on open MR; custom `/loop 5m check the deploy`; write repo `loop.md`
- [ ] 6.3 Remote Control: `/rc` at desk, connect from phone via claude.ai/code, send a steer
- [ ] 6.4 `ultrathink` on a hard bug; same bug at different `/effort` levels; note plan-tier availability
- [ ] 6.5 Parallel: two tickets in isolated git worktree sessions, watch in agent view, merge both
- [ ] 6.6 Subagents course; start a cloud session on a long task, close laptop, check progress at claude.ai/code
- [ ] 6.7 Exercise: one artifact + one loop run + one phone steer screenshotted to `evidence/day-06/`

### CP2 pass criteria
- [ ] Two parallel sessions, both merged
- [ ] One plan-mode change steered via diff comments
- [ ] One artifact
- [ ] One Remote Control steer
- [ ] One `/loop` run

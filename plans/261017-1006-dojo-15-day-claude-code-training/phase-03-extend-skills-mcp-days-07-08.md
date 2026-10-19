---
phase: 3
title: "Extend: skills, plugins, MCP (Days 7–8)"
status: todo
dates: 2026-10-25 → 2026-10-26
checkpoint: feeds CP3
---

# Phase 03 — Skills, plugins, MCP

## Day 7 — Sun 2026-10-25 — Skills, commands, plugins
Course: Introduction to agent skills

- [ ] 7.1 Course: Introduction to agent skills
- [ ] 7.2 Write one team skill (`release-checklist` or `how-we-write-migrations`) in `.claude/skills/`
- [ ] 7.3 Turn Day-3 review checklist into `review-checklist` skill (optional but recommended)
- [ ] 7.4 Write one personal custom slash command
- [ ] 7.5 Ship skill + command in one MR to `.claude/`
- [ ] 7.6 `/plugin` → Discover → install one useful plugin (e.g. code-intelligence/LSP); write honest evaluation

Evidence: MR link, `evidence/day-07/plugin-evaluation.md`.

## Day 8 — Mon 2026-10-26 — MCP & connectors
Course: Introduction to model context protocol

- [ ] 8.1 Course: Introduction to model context protocol
- [ ] 8.2 GitLab connector (replaces GitHub): add GitLab MCP server or use `glab` CLI with a scoped token; restart session; confirm MR/issue tools work
- [ ] 8.3 Device-control MCP for the Android emulator (replaces Playwright): pick a maintained server on the day and verify it exists and its permissions before adding; restart; confirm tap/screenshot/UI-tree tools. Fallback: Claude drives `adb shell input`, `uiautomator dump` and `screencap` via Bash
- [ ] 8.4 Exercise: one MCP tool end-to-end in a session (e.g. list MRs and comment on one, or drive the app on the emulator)

Evidence: session screenshot / transcript excerpt in `evidence/day-08/` (counts toward CP3).
Security: token in env / keychain only, never committed.

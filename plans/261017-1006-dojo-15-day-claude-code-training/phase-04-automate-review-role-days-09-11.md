---
phase: 4
title: "Automate, review, role day (Days 9–11)"
status: todo
dates: 2026-10-27 → 2026-10-29
checkpoint: CP3 (Day 11)
---

# Phase 04 — Hooks, automation, review, role day

## Day 9 — Tue 2026-10-27 — Hooks & automation
- [ ] 9.1 Hooks via `/hooks`: format-on-edit, block-forbidden-command, notify-on-finish
- [ ] 9.2 Ship one hook as MR (`.claude/settings.json`)
- [ ] 9.3 Automation ladder: `/goal` on a bounded task
- [ ] 9.4 Desktop scheduled task: Code tab → Routines → New routine → Local (morning briefing, or daily training report reminder)
- [ ] 9.5 Routines overview (cloud): claude.ai/code/routines
- [ ] 9.6 CI review (GitLab substitute for `@claude`): `.gitlab-ci.yml` job running `claude -p` review on MRs (skip with note if no CI API key)
- [ ] 9.7 Exercise: evaluate CI review comments on next MR

Evidence: hook MR link, routine screenshot, CI review notes.

## Day 10 — Wed 2026-10-28 — Review & quality
- [ ] 10.1 `/code-review` on a real branch
- [ ] 10.2 `/code-review ultra` on a seeded-buggy branch (Claude seeds it); compare signal
- [ ] 10.3 Security: `/security-review`; walk through `security-guidance` plugin / Claude Security scan
- [ ] 10.4 Exercise: fix what review found, MR green, note false positives for retro

Evidence: review outputs, fix MR, `evidence/day-10/false-positives.md`.

## Day 11 — Thu 2026-10-29 — Role day (Mobile, adapted from Web) · **Checkpoint 3**
Optional course: AI-native SDLC playbook (start)

- [ ] 11.1 Claude tests the app on the emulator from a session: install, run a flow, read logcat (replaces Claude in Chrome)
- [ ] 11.2 Screenshot review loop: `screencap`, Claude checks the UI, fix, repeat (replaces the browser pane preview)
- [ ] 11.3 Drive and verify the running app with the device MCP or adb, then lock the flow in with a Compose UI test (replaces Playwright)
- [ ] 11.4 Role-day exercise write-up in `evidence/day-11/`

### CP3 pass criteria
- [ ] One skill and one hook merged
- [ ] One MCP tool used end-to-end
- [ ] One `/code-review` or ultrareview report acted on
- [ ] Role-day exercise done

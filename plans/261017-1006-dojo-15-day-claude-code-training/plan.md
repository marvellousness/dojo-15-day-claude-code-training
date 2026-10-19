---
title: "Dojo 15-Day Claude Code Training (Developer track)"
description: "Day-by-day plan, checkpoints, progress tracking and daily reporting for the AgilityIO Dojo program"
status: in-progress
priority: P1
effort: 15d (~6h hands-on/day + courses)
branch: feat/training-plan
tags: [training, claude-code, dojo, developer-track]
created: 2026-10-17
start: 2026-10-17
end: 2026-11-02
source: https://dojo.agilityio.dev/courses.html
---

# Dojo 15-Day Claude Code Training — Developer track

## Overview
Complete the AgilityIO Dojo program (https://dojo.agilityio.dev) in 15 calendar days, Developer track.
This repo is the sandbox: training app, `CLAUDE.md`, `.claude/` skills/hooks, MRs all live here.
The site's "Mark day complete" button is browser-only, so [progress.md](./progress.md) is the source of truth.

- Day 1: Sat 2026-10-17. Days 2–15 shifted +2 days on 2026-10-19 (Day 1 closed late): Day 2 = Tue 2026-10-20, Day 15 = Mon 2026-11-02. Weekends count.
- Daily shape: ~6h hands-on, assigned courses, end-of-day status report (replaces the 10-minute standup).
- Source pages: `schedule/day-01..15.html`, `fundamentals.html`, `developer.html`, `courses.html` (verified on the site 2026-10-07; re-check each week).

## Phases
| Phase | Days | Dates | Focus | Status |
|---|---|---|---|---|
| [Phase 01](./phase-01-fundamentals-days-01-03.md) | 1–3 | 10-17 → 10-21 | Shared fundamentals, training app, CP1 | in-progress |
| [Phase 02](./phase-02-desktop-core-days-04-06.md) | 4–6 | 10-22 → 10-24 | Claude Code Desktop core, memory, flagship + parallel, CP2 | todo |
| [Phase 03](./phase-03-extend-skills-mcp-days-07-08.md) | 7–8 | 10-25 → 10-26 | Skills, plugins, MCP | todo |
| [Phase 04](./phase-04-automate-review-role-days-09-11.md) | 9–11 | 10-27 → 10-29 | Hooks, automation, review, role day, CP3 | todo |
| [Phase 05](./phase-05-capstone-days-12-15.md) | 12–15 | 10-30 → 11-02 | Capstone: plan, build, harden, demo, final CP | todo |

## Checkpoint gates (pass to continue)
| CP | Day | Pass criteria |
|---|---|---|
| CP1 | 3 | Repro written in ticket. Fix + test on a branch. MR with self-contained description. One review with ≥3 actionable comments. One merge with a conflict resolved. |
| CP2 | 6 | Two parallel worktree sessions, both merged. One plan-mode change steered via diff comments. One artifact. One Remote Control steer. One `/loop` run. |
| CP3 | 11 | One skill + one hook merged. One MCP tool used end-to-end. One `/code-review` or ultrareview report acted on. Role-day exercise done. |
| Final | 15 | Capstone shipped end-to-end (ticket → plan → MR → review → tests → artifact → shipped). CP1–CP3 all green. |

A failed checkpoint is redone as homework within the next 2 days; there is no slack week.

## Solo-mode adaptations (user-confirmed)
- **No lead / peer:** Claude seeds tickets, bugs and conflicts on a side branch (user does not peek). Peer review = Claude subagent review of user's MR, plus user's own review of a Claude-authored MR (≥3 actionable comments each). Capstone "client" brief is written deliberately vague by Claude; user plays BA, QA and Dev in turn.
- **GitLab, not GitHub:** MRs replace PRs. GitLab MCP / `glab` replaces the GitHub connector. `.gitlab-ci.yml` replaces GitHub Actions. `@claude` review replaced by a GitLab CI job running `claude -p` (skip with a note if no API key is available in CI).
- **Local git first (user decision 2026-10-17):** GitLab unreachable from this machine, so all work runs on local git. An "MR" = feature branch + description file `mr/<branch>.md` (what / why / how tested) + review via `git diff main...<branch>` + `git merge --no-ff` + branch delete. Push branches and open real GitLab MRs later once GitLab is reachable. CI tasks run locally (`npm test`, Playwright) until then.
- **Training app (user decision 2026-10-19: Android instead of Node/TS):** a small Android app in Kotlin, scaffolded on Day 2 under `android-app/`. Single module, Jetpack Compose UI, MVVM with coroutines/Flow, a notes/todo feature backed by Room, and a fake auth/sync API with a configurable delay so ANR-style and threading bugs can be seeded. Tests: JUnit + kotlinx-coroutines-test (unit), Compose UI tests on the emulator (instrumented). CI: `.gitlab-ci.yml` running `./gradlew lint testDebugUnitTest`, run locally until GitLab is reachable. Local toolchain checked 2026-10-19: JDK 21, Android SDK + emulator, adb, Gradle, Android Studio.
- **Role day (Day 11) adapted to Mobile:** the Dojo role day is Web (Claude in Chrome, browser pane, Playwright). With an Android app, Claude drives and checks the emulator instead (adb or a device-control MCP, logcat, screenshots, Compose UI tests).

## Tracking & reporting
- [progress.md](./progress.md) — per-day status, task counts, checkpoint table, overall %.
- [reports/daily-report-template.md](./reports/daily-report-template.md) — copy to `reports/day-NN-YYMMDD-status.md` at end of each day.
- `evidence/day-NN/` — screenshots, MR links, artifact URLs.
- Dashboard (private claude.ai artifact): https://claude.ai/artifact/QyrMiyiMCqff2qRS1Bb3ii
  - Page source: `dashboard/training-dashboard.html`. Data: `dashboard/data/*.json`, generated from progress.md by `dashboard/export-progress-tables-to-json.py`.

End-of-day routine (ask Claude: "close day N"):
1. Tick finished tasks in the phase file, update the progress.md row, checkpoint row and header totals.
2. Write `reports/day-NN-YYMMDD-status.md` from the template; link it in progress.md.
3. Run `python3 dashboard/export-progress-tables-to-json.py`, upload both JSON files to the dashboard, update its datasets.
4. Commit (`feat(training): close day N`).

## Course catalog (Developer + Everyone)
| Course | Day | Link |
|---|---|---|
| AI Fluency: Framework and foundations | 1 | https://academy.claude.com/courses/ai-fluency-framework-foundations |
| AI capabilities and limitations | 1 | https://academy.claude.com/courses/ai-capabilities-and-limitations |
| Claude 101 | 1–2 | https://academy.claude.com/courses/claude-101 |
| Claude Code 101 | 4 | https://academy.claude.com/courses/claude-code-101 |
| Claude Code in action | 4 | https://academy.claude.com/courses/claude-code-in-action |
| Introduction to subagents | 6 | https://academy.claude.com/courses/introduction-to-subagents |
| Introduction to agent skills | 7 | https://academy.claude.com/courses/introduction-to-agent-skills |
| Introduction to model context protocol | 8 | https://academy.claude.com/courses/introduction-to-model-context-protocol |
| AI-native SDLC playbook (optional, leads/seniors) | 11–12 | https://academy.claude.com/courses/ai-native-sdlc-playbook |
| Building effective human-agent teams (optional) | 15 | https://academy.claude.com/courses/building-effective-human-agent-teams |
| MCP advanced topics (after program) | — | https://academy.claude.com/courses/model-context-protocol-advanced-topics |
| Building with the Claude API (after program) | — | https://academy.claude.com/courses/building-with-the-claude-api |
| Claude Platform 101 (after program) | — | https://academy.claude.com/courses/claude-platform-101 |

Docs (Day 4): [Quickstart](https://code.claude.com/docs/en/quickstart), [Desktop get-started](https://code.claude.com/docs/en/desktop-quickstart), [Common workflows](https://code.claude.com/docs/en/common-workflows), [Best practices](https://code.claude.com/docs/en/best-practices), [Prompt library](https://code.claude.com/docs/en/prompt-library).

## Safety rails (from developer.html)
- Plan mode for destructive work; permission rules; sandboxed bash.
- No direct pushes to shared branches: MRs only.
- No client code, secrets or customer data in any AI tool, cloud session or artifact.

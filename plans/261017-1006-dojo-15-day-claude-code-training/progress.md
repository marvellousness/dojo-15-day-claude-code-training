# Training Progress — source of truth

Last updated: 2026-10-21 · Overall: **3 / 15 days done (20%)** · Tasks: **25 / 98**

Status values: `todo` · `in-progress` · `done` · `blocked`

## Days
| Day | Date | Topic | Status | Tasks | Courses | Report | Evidence | Notes |
|---|---|---|---|---|---|---|---|---|
| 1 | 2026-10-17 Sat | Mental model + Claude basics | done | 6/7 | AI Fluency ✓ (badge), Capabilities & limits ✓ (badge), Claude 101 → Day 2 | reports/day-01-261017-status.md | evidence/day-01/prompt-notes.md | 1.4 carried to Day 2; closed 10-19; local git only |
| 2 | 2026-10-20 Tue | Ticket analysis, reproduce | done | 8/8 | Claude 101 ✓ (badge) | reports/day-02-261020-status.md | mr/day-02-app-scaffold.md | Android app merged; NOTE-101/102 reproduced; failing tests on day-02/failing-tests |
| 3 | 2026-10-21 Wed | Fix, review, merge (CP1) | done | 9/9 | — | reports/day-03-261021-status.md | mr/fix-note-101-102-sync.md, mr/claude-sync-message-resources.md | closed 10-20 (a day early); CP1 pass (review Claude-drafted); NOTE-107/108 filed |
| 4 | 2026-10-22 Thu | Install & daily loop | in-progress | 1/8 | Claude Code 101, CC in action | — | evidence/day-04/claim-verification.md, mr/fix-note-108-edit-during-sync.md | started 10-21 evening; 4.6 NOTE-108 merged f864492 |
| 5 | 2026-10-23 Fri | Memory & context | todo | 0/4 | — | — | — | |
| 6 | 2026-10-24 Sat | Flagship & parallel (CP2) | todo | 0/12 | Intro to subagents | — | — | |
| 7 | 2026-10-25 Sun | Skills & plugins | todo | 0/6 | Intro to agent skills | — | — | |
| 8 | 2026-10-26 Mon | MCP & connectors | todo | 0/4 | Intro to MCP | — | — | GitLab MCP |
| 9 | 2026-10-27 Tue | Hooks & automation | todo | 0/7 | — | — | — | |
| 10 | 2026-10-28 Wed | Review & quality | todo | 0/4 | — | — | — | |
| 11 | 2026-10-29 Thu | Role day (CP3) | todo | 0/8 | SDLC playbook (opt) | — | — | |
| 12 | 2026-10-30 Fri | Capstone kickoff | todo | 0/6 | SDLC playbook (opt) | — | — | |
| 13 | 2026-10-31 Sat | Build | todo | 0/4 | — | — | — | |
| 14 | 2026-11-01 Sun | Test & harden | todo | 0/3 | — | — | — | |
| 15 | 2026-11-02 Mon | Review, merge, demo (Final) | todo | 0/8 | Human-agent teams (opt) | — | — | |

Task counts include checkpoint criteria items in phase files.

## Checkpoints
| CP | Day | Status | Items | Evidence |
|---|---|---|---|---|
| CP1 | 3 | pass | 4/4 | NOTE-101/102 repro; mr/fix-note-101-102-sync.md (merged 19048f1, conflict resolved); review on claude/sync-message-resources (Claude-drafted) |
| CP2 | 6 | in-progress | 1/5 | diff-comment steer: evidence/day-04/cp2-hunk-comment-steer.png (NOTE-108 MR) |
| CP3 | 11 | todo | 0/4 | |
| Final | 15 | todo | 0/2 | |

## Blockers log
| Date | Day | Blocker | Action | Resolved |
|---|---|---|---|---|
| 2026-10-17 | 1 | GitLab gitlab.asoft-python.com unreachable (SSH :22 and HTTPS time out); `glab` not installed | Local git only: simulate MRs with branch + `mr/<branch>.md` + `--no-ff` merge; push later (VPN?) | no |

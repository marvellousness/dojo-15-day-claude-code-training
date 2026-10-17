# Day 1 — Setup check + prompt exercise notes (2026-10-17)

## 1.1 Setup check
| Item | Result |
|---|---|
| Claude Code CLI | 2.1.302 installed |
| Claude Desktop app | `/Applications/Claude.app` installed |
| claude.ai sign-in | _confirm in Desktop app_ |
| Repo cloned | yes, remote `git@gitlab.asoft-python.com:tungphan/claude-code-training.git` |
| GitLab reachable | NO from this machine at 10:47: SSH :22 and HTTPS both time out (VPN needed?) |
| Node / npm | v22.16.0 / 11.18.0 (ready for Day 2 app) |
| `glab` CLI | not installed (needed Day 8: `brew install glab`) |

## 1.6 Exercise — three real work questions in Claude chat
Use claude.ai chat (not Claude Code). For each: first prompt as you'd naturally type it, then improve it.

### Q1 — Bug summary
#### How to run it (Android)
1. Pick one real bug from your Android project: a crash, an ANR, a UI glitch, or a lifecycle/state loss.
2. **Sanitize first (Creation Diligence):** strip API keys, tokens, user emails/phones, internal URLs, client names and package names if they're confidential. Keep only the stack trace frames and code that matter.
3. **v1:** in claude.ai chat, type the request the way you normally would, e.g. "summarize this bug: <paste ticket or logcat>". Save the answer and score it.
4. **v2:** start a **new chat** so v1 doesn't bias it, and use the template below. Score again.
5. **Discern:** check every claim against the real code and logs. Did it invent a class, method, API level or library version? (Fabrication concentrates in specifics.) Did it agree with your guess of the cause without evidence (sycophancy)?

#### v2 prompt template
```
Context: I'm an Android developer on <app type> (Kotlin/Java, minSdk <x>, targetSdk <y>,
<Jetpack Compose | Views>, <key libs: Retrofit/Room/Hilt/Coroutines...>).
I need a bug summary for a ticket my team lead and QA will read.

Bug facts:
- Device / OS: <model, Android version>   App version/build: <x>
- Steps to reproduce: 1) ... 2) ... 3) ...
- Expected: ...            Actual: ...
- Frequency: <always / 1 in N / only after rotation / only on cold start>
- Logcat / stack trace (trimmed):
<paste the relevant lines>
- Relevant code (optional, sanitized):
<paste the suspected function>

Task: before answering, think through the likely root causes from the trace.
Output, in this exact format, at most 150 words:
1. Title (one line, [Area] symptom)
2. Summary (2–3 sentences: what, where, impact)
3. Repro steps / Expected / Actual
4. Likely cause (cite the stack frame or line you rely on; say "unknown" if the evidence is weak)
5. Next debugging step
Don't invent APIs, classes or versions that are not in what I gave you. If my guess at the cause
is wrong, say so.
```
Each part of the template maps to a 4D idea: context and audience (Product Description), "think first" and steps (Process Description), the format and word limit (constraints), "say unknown" and "if my guess is wrong, say so" (against hallucination and sycophancy).

#### Record
- Real bug/issue used:
- Prompt v1:
- Answer quality (1–5) + why:
- Prompt v2 (what changed: context? expected vs actual? format asked?):
- Answer quality (1–5) + why:

### Q2 — "Explain this concept"
- Concept:
- Prompt v1:
- Answer quality (1–5) + why:
- Prompt v2 (what changed: audience level? example? analogy? length?):
- Answer quality (1–5) + why:

### Q3 — Drafting task
- What drafted (email, ticket, PR description, ...):
- Prompt v1:
- Answer quality (1–5) + why:
- Prompt v2 (what changed: tone? audience? constraints? sample?):
- Answer quality (1–5) + why:

## 1.7 Reflection
- Prompt changes that helped most:
- Where output surprised me (good):
- Where output surprised me (bad / wrong / overconfident):
- How I verified claims:

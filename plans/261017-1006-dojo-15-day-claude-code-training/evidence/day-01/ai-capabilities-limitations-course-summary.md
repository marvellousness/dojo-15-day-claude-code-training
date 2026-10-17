# AI capabilities and limitations — study summary (Day 1)

Course: https://academy.claude.com/courses/ai-capabilities-and-limitations
Format: 13 lessons, ~3.5 hours, 4 "Try It Out" exercises, 1 final quiz, completion badge. No prerequisite; AI Fluency is recommended first.

> Study aid only. The badge needs the course, the exercises and the quiz completed in Academy.
> Sources: the outline, objectives and named concepts come from the course page (fetched 2026-10-17). The explanations and examples are general LLM knowledge, because lesson bodies need a sign-in and weren't fetched. Check them against the lessons.

## Course outline
| # | Section | Lessons |
|---|---|---|
| 1 | Getting started | Intro · What We Mean by AI · How AI Gets Its Character |
| 2 | Next Token Prediction | Lesson · Try It Out |
| 3 | Knowledge | Lesson · Try It Out |
| 4 | Working Memory | Lesson · Try It Out |
| 5 | Steerability | Lesson · Try It Out |
| 6 | Putting it together | When Properties Collide · Next Steps · Course Quiz · Badge |

## Core idea
Generative AI creates new content. Classification and prediction AI only label or forecast. Its behaviour comes from **four properties**. Each one is a continuum: the same mechanism that makes it capable also produces its typical failures. Know which property is in play and you can predict when to trust the output and when to check it.

## How AI gets its character: two-stage training
1. **Pretraining:** predict the next token over huge amounts of text. This gives broad knowledge and fluency.
2. **Fine-tuning:** human feedback and guidelines shape it into a helpful assistant.

Fine-tuning leaves **behavioural fingerprints** to watch for:
| Fingerprint | What it looks like | Counter |
|---|---|---|
| Sycophancy | Agrees with you, praises weak ideas, backs down when you push | Ask for critique; say "push back if I'm wrong"; don't reveal the answer you prefer |
| Verbosity | Long, padded answers | Set a length or format ("3 bullets", "one sentence") |
| Over-caution | Refuses or hedges on reasonable requests | Give legitimate context and purpose |
| Loose confidence calibration | Sounds equally sure whether right or wrong | Ask for confidence levels or sources; verify yourself |

## The four properties
| Property | Capability end | Limitation end | Fix |
|---|---|---|---|
| **Next token prediction** | Fluent, plausible, creative text and code | **Fabrication**: invented citations, APIs, numbers and facts that sound right. Worst for specifics such as names, dates, quotes, URLs and version numbers | Ground it with sources or docs you supply; ask it to say "I don't know"; check the specifics |
| **Knowledge** | Strong on mainstream, frequently written-about topics | **Knowledge cutoff** and **uneven coverage**: weak on recent, rare, niche, internal or contested topics | Web search, retrieval (paste the docs), tool use (MCP, running code) |
| **Working memory** (context window) | Holds and reasons over everything in the conversation | **Hard limit**: past it, content is dropped. Long contexts lose detail in the middle; early instructions fade | **Front-load** key information, **chunk** big inputs, **re-supply** critical facts, start a fresh session for each topic |
| **Steerability** | Follows short, concrete, checkable instructions well | Looser on long, vague or conflicting instructions. **Reasoning drift**: it wanders off over long tasks. **Letter over spirit**: it obeys the wording and misses the goal | Short, concrete, verifiable instructions; state the *why*; check intermediate steps |

## When properties collide: diagnosis method
1. **Name the property** (or properties) behind the failure.
2. **Place the task** on that property's spectrum. Is it near the capability end or the limitation end?
3. **Apply a targeted fix** from the table above. Rewording the same prompt at random is not a fix.

Example: Claude invents a function that doesn't exist in a niche library. That's next-token fabrication combined with a knowledge gap (rare library, possibly newer than the cutoff). Fix: paste the library docs or let it read the source, then run the code.

## Link to the 4D framework (from the AI Fluency course)
| 4D | Use of the four properties |
|---|---|
| Delegation | Delegate tasks near the capability end; keep or verify tasks near the limitation end |
| Description | Write for steerability (concrete, short, the why) and working memory (front-load, re-supply) |
| Discernment | Expect fabrication in specifics, sycophancy when you push back, and drift in long tasks |
| Diligence | **Calibrated trust**: trust is earned per task type and confirmed by running, testing and checking sources |

## How it maps to Claude Code work (this training)
| Property | Where you'll see it | Habit or feature that handles it |
|---|---|---|
| Next token prediction | Plausible but wrong code or APIs | Run tests; Day 4 "verify 2 claims"; "trust runs over fluency" |
| Knowledge | Unknown repo conventions, new library versions | CLAUDE.md (Day 5), MCP and docs lookup (Day 8), reading code before editing |
| Working memory | Long sessions get worse | One topic per session, `/compact`, `/rewind`, subagents (Days 4–6) |
| Steerability | Ignores a rule, or "fixes" a test by weakening it | Plan mode, hooks that enforce rules (Day 9), reviewing diffs |

## Apply to today's exercise
In `prompt-notes.md`, for any answer that went wrong, name the property behind it (fabrication, knowledge gap, working memory or steerability) and the fix you used.

## Self-check before the quiz
1. Name the four properties and one failure mode of each.
2. Which training stage causes sycophancy and verbosity?
3. Three strategies for working inside the context window?
4. What is "letter over spirit" compliance? Give an example from coding.
5. What are the three steps of the diagnosis method?
6. When does web search, retrieval or tool use help, and which property does it address?

# AI Fluency: Framework and foundations — study summary (Day 1)

Course: https://academy.claude.com/courses/ai-fluency-framework-foundations
Authors: Anthropic with Prof. Rick Dakan (Ringling College) and Prof. Joseph Feller (University College Cork).
Format: 14 lessons, ~4 hours, 1 final quiz, completion badge (sign-in needed to save progress).

> Study aid only. The badge needs the course and quiz completed in Academy.
> Sources: the outline and learning objectives come from the course page (fetched 2026-10-17). The definitions below come from Anthropic's published AI Fluency framework, because lesson bodies need a sign-in and weren't fetched. Check them against the lessons as you go.

## Course outline
| # | Section | Lessons |
|---|---|---|
| 1 | Introduction | Introduction to AI Fluency |
| 2 | The AI Fluency Framework | Why do we need AI Fluency? · The 4D Framework |
| 3 | Deep Dive 1: What is Generative AI? | Generative AI fundamentals · Capabilities & limitations |
| 4 | Delegation | A closer look at Delegation · Project planning and Delegation |
| 5 | Description | A closer look at Description |
| 6 | Deep Dive 2 | Effective prompting techniques |
| 7 | Discernment | A closer look at Discernment |
| 8 | Description–Discernment loop | The Description-Discernment loop |
| 9 | Diligence | A closer look at Diligence |
| 10 | Conclusion | Conclusion · Course quiz · Additional activities · Badge |

## Core idea
AI Fluency means working with AI **effectively, efficiently, ethically and safely**. The course breaks it into four interconnected competencies, the **4D framework**, used across three ways of engaging with AI.

### Three modes of engagement
| Mode | Meaning | Dev example |
|---|---|---|
| **Automation** | AI carries out a defined task on your instructions | "Write unit tests for this function" |
| **Augmentation** | Human and AI think and create together as partners | Brainstorming a design with Claude, trading ideas back and forth |
| **Agency** | You set up AI to act on its own on your or others' behalf | A Claude Code hook, a `/loop` or routine, an agent with tools |

### The 4Ds
| D | Question it answers | Sub-competencies |
|---|---|---|
| **Delegation** | *Should* AI do this, and which part? | **Problem awareness**: know the goal and what good looks like. **Platform awareness**: know what this AI tool can and can't do. **Task delegation**: split the work between human, AI, or both. |
| **Description** | How do I tell the AI what I want? | **Product**: the output (format, audience, style, length). **Process**: how to approach it (steps, method, what to consider). **Performance**: how the AI behaves while working with you (concise or detailed, challenging or supportive, its role). |
| **Discernment** | Is the result any good? | Judge the **product** (accurate, fit for purpose, coherent), the **process** (sound reasoning, no skipped steps or invented facts) and the **performance** (did it behave the way you asked?). |
| **Diligence** | Am I responsible for how AI is used? | **Creation**: choose tools and ways of working thoughtfully (data, privacy, policy). **Transparency**: be honest about AI's role with the people who need to know. **Deployment**: verify and stand behind anything you share or ship. |

### Description–Discernment loop
Describe → AI responds → judge the result → refine the description → repeat. Most of the improvement in quality comes from running this loop on purpose, not from the first prompt.

### Effective prompting techniques (Deep Dive 2)
- Give context: who you are, the goal, the background, and the constraints.
- Show examples of good output (few-shot).
- State the output format and length.
- Break complex work into steps; ask it to think or plan first.
- Define a role or perspective and the tone.
- Ask it to ask clarifying questions, or to list its assumptions.
- Iterate: treat the first answer as a draft.

### Generative AI basics (Deep Dive 1)
- An LLM predicts the next token from patterns in its training data. The output is fluent, but it isn't guaranteed to be true.
- Strengths: drafting, summarising, explaining, transforming, coding, brainstorming.
- Limits: a knowledge cutoff, hallucination (confident but wrong), a finite context window, sensitivity to wording, no built-in fact-checking.
- Consequence: Discernment and Diligence are required, not optional.

## How it maps to this training
| 4D | Where it shows up in the Dojo plan |
|---|---|
| Delegation | Day 2 ticket analysis (what to give Claude), Day 4 plan mode, Day 6 subagents/parallel |
| Description | Day 1 prompt exercise, Day 5 CLAUDE.md, Day 7 skills |
| Discernment | Day 4 "verify 2 claims", Day 10 review false positives, the "verify by running" habit |
| Diligence | Never paste secrets or client data, MR descriptions saying how it was tested, Day 15 playbook guardrails |

## Apply to today's exercise (tasks 1.6 and 1.7)
For each of the three questions in `prompt-notes.md`, tag the v1 → v2 change with the D it improved:
- Added background or a goal → Description (product or process)
- Asked for a format or length → Description (product)
- Asked it to be brief or to challenge you → Description (performance)
- Caught a wrong claim and re-prompted → Discernment, then the loop
- Checked a fact yourself before using it → Diligence (deployment)

## Self-check before the quiz
1. Name the 4Ds and one sub-competency of each.
2. What separates Augmentation from Agency?
3. What are the three types of Description?
4. Which competency covers being transparent about AI use?
5. Describe the Description–Discernment loop in one sentence.

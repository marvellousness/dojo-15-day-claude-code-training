# AI capabilities and limitations — detailed lesson summary (Day 1)

Course: https://academy.claude.com/courses/ai-capabilities-and-limitations
Presenters (Anthropic): Kristen (education team), Maggie (leads the education team), David (safety team), Matt (user research team).
Format: 13 lessons + course quiz (~3.5 hours), including 4 interactive "Try It Out" exercises, and a completion badge. No prerequisite; AI Fluency is the recommended companion.

> Sources: the full video transcripts of the 9 video lessons (L1–L4, L6, L8, L10, L12, L13), plus the text of the Try It Out pages, read 2026-10-17. Three Try It Out pages (L5, L7, L11) are interactive widgets with no readable text; only their topic is listed. This is a study aid. The badge needs the exercises and quiz done in Academy.

---

## Big picture
- **Companion to AI Fluency.** The 4Ds are what *you* do. This course covers what *the machine* does when you prompt it, and why. "Human competencies on one side and machine properties on the other."
- The course explains *how AI is created*, so it stays relevant while products and models change.
- **Two training stages** give the AI its character: pre-training builds a *document completer*, fine-tuning shapes it into an *assistant*.
- **Four properties** of generative AI: next token prediction, knowledge, working memory, steerability.
- **Each property is a continuum**, with a *capability zone* at one end and a *limitation zone* at the other. The mechanism always works the same way; what changes is where your task lands on the line. "An AI can write compellingly because it's a prediction engine. It also hallucinates because it's a prediction engine."
- **Most real failures are two properties meeting.** A hallucinated citation is next token prediction meeting a knowledge gap. Drift over a long conversation is working memory meeting steerability.
- **Goal: calibrated trust.** Neither grant trust nor withhold it wholesale. Locate the task, then set your habits for it.
- The specifics will change (bigger windows, fewer hallucinations), but the shape of the properties stays the same: "Next token prediction will still be next token prediction a year from now."
- Learning approach: bring **real tasks from a domain where you're the expert**, because that's where you can tell when something is off. "The goal is a calibration you can feel, not a list of terms you memorize."

```
             capability zone  ◄──────────────────►  limitation zone
Next token   well-worn paths (summarise, explain)    thin paths: niche facts, citations
Knowledge    mainstream, stable, pre-cutoff          recent, rare, local, niche
Work. memory fits in window, this session            very long, mid-document, last week (CLIFF)
Steerability short, concrete, checkable              long chains, abstract ("be insightful")
```

---

## Lesson-by-lesson

### L1 · Intro to AI capabilities and limitations (Kristen)
- Why it matters for the 4Ds:
  - You can't **delegate** without knowing where the model is strong and weak.
  - You can't **discern** without some picture of how the output was produced.
  - Knowing it's a prediction engine changes how you **describe** tasks; knowing about the context window changes how you **delegate** long tasks.
- Roadmap: two training stages and their fingerprints, then the four properties as continuums, then how they interconnect.
- Exercises:
  - List the tasks you use or would use AI for. Be specific: "drafted a client email explaining a project delay", not "writing". This list is reused in later lessons.
  - Ask the AI, "For each of these tasks, what's one way this could go wrong if I'm not paying attention?" If its answers don't match your experience, push back with what actually went wrong.

### L2 · What we mean by AI
- Most AI isn't generative: recommendation engines, spam filters, fraud detection and call routing *sort, rank, classify and predict*.
- **Generative AI produces new content** (text, images, code, audio, video). It is built in two stages: pre-training on massive data to learn patterns, then fine-tuning to be broadly safe, ethical and helpful.
- **At its core it's a prediction system.** It isn't uniformly capable or uniformly unreliable; it is strong and weak along *specific, predictable axes*, and often the strength and the weakness come from the same property.
- The four properties as questions:
  - **Next token prediction:** where do the answers come from? Unless pointed at an external source, the model isn't looking anything up; it writes what comes next, one fragment at a time.
  - **Knowledge:** what does it actually know? Broad but uneven, frozen at a cutoff, shaped by its training data.
  - **Working memory:** what is it paying attention to right now? Only what's in the context window.
  - **Steerability:** how much are you in control? It is very directable, but there's a gap between what you meant and what landed.
- The questions to ask by the end: Where does my task sit on each continuum? Is this well-trodden territory or near an edge? **What are the stakes if I'm wrong?**
- Exercise: classify five AI examples as generative or not. For any you missed, ask for the distinction in one sentence, then ask which one is most likely to have a failure mode this course covers.

### L3 · How AI gets its character (Maggie)
- Helpfulness, politeness and refusals don't come from next-word prediction alone. They are **built deliberately, in layers**.
- **Stage 1, pre-training:** "Given everything so far, guess what comes next", repeated billions of times. The result is a *document completer*.
  - Ask a raw pre-trained model "What is the capital of France?" and it doesn't answer. It continues the document: "Paris. What's the capital of Germany? Berlin…" (a quiz pattern), or a textbook paragraph. It has no concept of you or of helping.
- **Stage 2, fine-tuning:** retraining on curated examples of helpful behaviour, with reward signals shaped by human preferences. This turns the model into an assistant.
- Because fine-tuning relies on human judgments, those judgments show up in the model's personality. The **four shadow areas** of helpfulness:

| Fingerprint | Where it comes from (transcript) | What you notice |
|---|---|---|
| **Sycophancy** | People prefer agreeable responses | Validates readily; backs down under light pushback *even when it was right* |
| **Verbosity** | Thoroughness scored better in training | Long answers when brevity would serve you better; essays instead of bullets |
| **Over-caution** | Safety training leans conservative | Heavy hedging or refusing requests that are actually safe; caveats on harmless questions |
| **Loose confidence calibration** | Confidence is genuinely hard to train | Stated confidence only loosely tied to actual reliability, so stay especially vigilant |

- These show up in **all models**, though fine-tuning makes them look different from model to model. Anthropic publishes Claude's constitution, which describes how Claude's personality is shaped.
- "The assistant you talked to wasn't born helpful… sometimes the seams show." Learning to spot the seams is part of using AI well.
- Exercise: run a task with a wrong assumption up front ("I think this strategy is bulletproof") and see whether it validates you or pushes back. Then retry with "I want you to genuinely disagree with me if you think I'm wrong." Compare the two answers.

### L4 · Next token prediction (David)
- **"If you understand one thing… it's prediction."** The model composes the answer word by word from what tends to follow what. It is "closer to an extraordinarily sophisticated autocomplete than to a search engine".
- A citation that *looks like* a citation satisfies the pattern just as well as one pointing to a real paper.
- Demo: summarising a famous essay gives fast, clean prose (a well-worn path). Asking for three papers by a mid-level researcher in a niche subfield gives the same confident tone, but some papers may be invented (a thin path).
- **Capability zone:** summarising, reformatting, explaining common concepts, drafting in a familiar style. Strengths: fluent text in any register, rapid synthesis across fields, strong on anything resembling what it has seen, coherent continuation of any thread.
- **Limitation zone:** novel territory, obscure topics. Failures: hallucinations, inconsistency, misplaced confidence.
- Product features exist *because* generation is always next token prediction:
  - citations and source grounding;
  - trained uncertainty signalling ("I'm not sure about this");
  - constrained generation and skills;
  - generator–verifier agent loops checked against an outside source.
- **Rules of thumb:**
  - **A confident tone doesn't signal accuracy.** Smoothness and correctness are independent variables.
  - **Fabrication concentrates in specifics:** names, dates, statistics, citations, quotes, URLs. The more precise the claim, the more it needs checking.
  - Treat output as **a draft to verify**, especially when stakes are high or the domain is unfamiliar to you.
  - Use citation and grounding features when the tool has them. "The model can't reliably tell grounded from invented. You have to do that part."
- 4D link: it is at the heart of **Discernment**, and it informs **Delegation** (well-worn tasks are safer handoffs).

### L5 · Try It Out: next token prediction
- Interactive widget ("Markov text your friend"): build a simple next-word predictor to feel how plausible text comes from pattern statistics. No text content.

### L6 · Knowledge (David)
- The model learns concepts, relationships and facts **only** from its training text. It has no experiences, doesn't browse unless a product gives it search, and has a **knowledge cutoff**.
- Demo: "Explain photosynthesis" gets a detailed, accurate answer (a frequent, stable topic). "Who's the current mayor of Toledo?" might get the person from two years ago, and the model can't tell the difference.
- **The right question isn't "Does the AI know this?" but "How well-represented was this in what it read?"**
- **Strengths:**
  - extraordinarily broad general knowledge;
  - deep in well-represented domains;
  - **cross-field connections via embeddings**: concepts that appear together sit near each other in a mathematical space, so a biology question can pull in chemistry, history and economics.
- **Limitations:**

| Limitation | Meaning |
|---|---|
| Knowledge cutoff | Nothing after training ends exists for the model |
| Staleness | Facts that were true at training time may have changed; it has no way to know |
| Uneven coverage | Rare topics, minority languages, niche domains and recent developments are weak |
| Inherited bias | Its sense of "normal" reflects the data's blind spots (what a doctor, a family or a professional looks like) |
| Source amnesia | It usually can't say where a fact came from; "I read this somewhere isn't a citation" |

- **Runtime patches:** web search (gets around the cutoff), **MCPs** (connect documents it never saw, such as a company wiki), tools (real-time calculators and databases), explicit cutoff disclosure.
- **Gaps are most likely when:** the topic is time-sensitive (current events, who holds a role, prices); it's niche, local or in a less-represented language; it's after the cutoff; you rely on its sense of "typical"; or search is off.
- **Protect yourself:**
  - Verify anything time-sensitive.
  - **Test before you trust in a new domain.** Brilliance in one area doesn't transfer to the next one.
  - Watch for default assumptions.
  - Use search or retrieval when available.
- 4D link: **Delegation** (bring the knowledge yourself through context, documents or search), **Discernment** (recent, rare or local claims need a second look).
- Exercise: ask a question that shows whether the AI assumes the outsider's view of your field (e.g. "describe the typical customer"), and note what it treats as normal.

### L7 · Try It Out: knowledge
- Interactive widget ("Embedding space explorer"): see how related concepts cluster in embedding space. No text content.

### L8 · Working memory (Matt)
- Everything (instructions, prior responses, uploaded documents, the conversation) sits in **one fixed-size context window**, and the model can only attend to what's inside it.
- **When it overflows, the oldest material usually drops, silently.** The model doesn't announce it; it carries on with what's left.
- **The window empties between sessions by default.** Tomorrow's chat starts from zero unless a feature such as memory or a **CLAUDE.md** file carries something forward.
- **Unlike the other properties, this one has a cliff.** The others degrade gradually; working memory "tends to work right up until it doesn't", and you won't always get a warning.
- In the capability zone, **context is leverage**. Upload a short style guide and the model adapts immediately, with no retraining.
- Why the window is fixed: the model processes the whole context as one block for every response. Even inside the limit, attention isn't uniform. The **lost-in-the-middle effect** means content in the middle of a long input carries less weight than the beginning or end.
- **Product features that soften the edge:**
  - memory across sessions;
  - **compaction / summarisation** of long conversations;
  - projects and workspaces for standing documents;
  - **skills**, which load instructions only when a task needs them;
  - **multi-agent workflows**, where each agent has its own window;
  - larger windows.
- **Warning signs:** quality slipping in a long chat; details from the middle of a long document missing from answers; expecting recall from a previous session without memory enabled.
- **Techniques:**
  - **Lead with what matters.** Important material goes near the top, not on page 12.
  - **Chunk long work into passes.**
  - Use projects and skills to keep standing context.
  - **If quality degrades, start fresh.** A new chat with a short summary of where you were can beat pushing on, because the slippage is often a context limit rather than a capability limit.
- 4D link: working memory "is the mechanism that makes **Description** work". Everything you describe has to fit in the window to have any effect.
- Exercise: pick a task that needs context only you hold (a style guide, a past example, role constraints) and write 2–3 lines on what "good" looks like, clear enough for a stranger to judge it.

### L9 · Try It Out: working memory ("When more context makes things worse")
- A memory-test widget shows that you forget the middle of a list too: the **serial position effect**. Items at the start benefit from *primacy*, items at the end from *recency*, and the middle gets neither.
- **LLMs show the same U-shaped curve.** In a 2023 Stanford study, accuracy was highest when a key fact sat at the very start or end of a long context, and **dropped by more than 30%** when it was buried in the middle. This is structural: transformer attention weights the edges of the window more.
- A question about page 11 of a pasted 20-page document is more likely to be missed than one about page 1 or page 20.
- **Put the most important instructions at the beginning and the end.** State a must-follow constraint early in the system prompt and restate it near the end.
- **Context engineering:** every piece you add pushes other pieces toward the middle "dead zone", so the question is not only what to include, but where to put it and what to leave out. "The fix isn't more — it's smarter."

### L10 · Steerability (Matt)
- Steerability is the model's ability to follow directions: tables, perspectives, roles, tone, format, word limits and rules, often on the first try. It comes from **fine-tuning**; a pre-trained model has no concept of helping.
- **Steerability isn't understanding.** Instructions are followed by the same pattern-completion engine, so there is always a gap between the words you typed and your intent, and "many interesting AI limitations live in that gap".
- Demo: "Summarize in under 100 words, make it punchy." Both instructions were met, but the one qualified finding the user needed was cut because it made the summary less punchy. **"The model honored what I said and missed what I meant."** Another example: "be concise" trims the one part you cared about.
- **Capability zone:** short, concrete, checkable instructions ("respond as a table", "under 100 words", "use this exact schema"). This gives tight control of format and style, a persona held across a conversation, multi-step execution of a process you lay out, and iterative refinement.
- **Limitation zone:** long chains of reasoning and abstract directions ("be insightful").
- **Failures:**
  - **Reasoning drift:** an error in step 2 quietly carries through steps 3–5, and the model doesn't notice.
  - **Letter over spirit:** the instruction is followed literally but uselessly.
  - **Prompt injection:** because the model follows instructions found in text, a malicious instruction hidden in a document or web page can be followed too. It's a security concern more than a daily one.
- **Product features:** system prompts and custom instructions (standing directions that don't dilute over a long conversation); visible reasoning (catch drift at step 2); structured output (JSON schemas, function calling).
- **Techniques:**
  - **State the goal alongside the steps** ("I'm trying to persuade a skeptical audience").
  - **Break long chains with checkpoints.** Ask for an intermediate result you can verify.
  - When an instruction lands literally but uselessly, **restate the goal, not the instruction**. Saying "be concise" louder doesn't fix an intent problem.
  - Keep concrete, verifiable instructions near the task. Short and checkable beats long and ambiguous.
- Ask yourself: not "Did I write a good prompt?" but **"How much room is there between what I typed and what I actually want?"**
- 4D link: Description both uses steerability and works within its limits. In **Delegation**, tasks with long reasoning chains or that need exact numbers need tighter human checkpoints or a different tool.
- Exercises: write the *goal* of a multi-step task in one sentence ("Convince my team this timeline is realistic" is a goal; "three bullet points" is a format). Then give an instruction that can be met literally but uselessly ("make this shorter" when the real problem is structure), and re-prompt with the goal stated. Compare.

### L11 · Try It Out: steerability
- Interactive widget ("Letter vs spirit scenarios"). No text content.

### L12 · When properties collide
- **Next token prediction + knowledge = fabricated citation.** The model writes something shaped like a real citation over a knowledge gap it can't see; it "can't tell the difference between what it knows and what it's generating". Fix: verify specifics independently, or better, use **source grounding** so it retrieves real documents.
- **Working memory + steerability = ignored constraints.** Constraints set early have faded twenty messages later, and because steerability follows whatever is *most salient now*, later messages quietly override earlier ones. Fix: **re-supply critical context**, or start fresh with the essentials up front.
- **Diagnose first.** Before you start, ask which properties the task touches. A knowledge problem and a working memory problem can look the same but need different fixes. Jumping straight to "how do I fix my prompt?" is guessing.
- Naming the property is **Discernment**: it turns "that wasn't quite right" into "I need to reground this in a source" or "I need to invite pushback".
- It feeds **Delegation**: a compound failure that keeps repeating on the same task type tells you to restructure it, split it, or keep it yourself.

### L13 · Next steps (Kristen)
- Recap: two training stages leave two fingerprints; four properties, each a continuum with a capability zone, a limitation zone, and features that push the edge out; failures are "Which two things collided?", not "What broke?".
- **How the course maps onto the 4Ds, as stated in the course:**
  - next token prediction makes you better at **Discernment** (fluency and accuracy are independent);
  - working memory makes you better at **Description** (context is leverage; stop assuming it remembers everything);
  - steerability makes you better at **Delegation** (know where control is high and where it's imprecise).
  - "The machine layer sharpens the human layer."
- **Calibrated trust is a habit, not an attitude.** Before handing something to AI, check:
  1. Is this well-worn territory or sparse?
  2. Is the topic recent or stable?
  3. Does my context fit comfortably inside the window?
  4. Are my instructions concrete, or is there room between my words and my intent?
- Then adjust: **more verification** where fabrication concentrates, **more context** where the model can't guess, **more checkpoints** where reasoning chains run long, and features that extend the model.
- "You don't trust the AI, but you don't distrust it either. You locate the task, and you set your habits accordingly. That's the true essence of AI Fluency."
- Next: practise on real work, notice where your predictions about AI were right or wrong, revisit the 4D course, keep testing the edges as they move.

### L14 · Course quiz
- Final quiz, then the completion badge.

---

## Cheat sheet (quiz prep)
| Property | Capability zone | Limitation zone | Product features | Your habit |
|---|---|---|---|---|
| **Next token prediction** | Well-worn tasks: summarise, reformat, explain, familiar drafting | Niche or novel: hallucination, inconsistency, misplaced confidence | Citations, grounding, uncertainty signals, skills, generator–verifier loops | Confident ≠ correct; check specifics (names, dates, stats, citations, quotes, URLs) |
| **Knowledge** | Mainstream, stable, pre-cutoff; cross-field links via embeddings | Cutoff, staleness, uneven coverage, inherited bias, source amnesia | Web search, MCP, tools, cutoff disclosure | Verify time-sensitive facts; test before trusting a new domain |
| **Working memory** | Material fits; in-session adaptation (style guide, glossary) | **Cliff**: silent drop of oldest content, lost in the middle, empty between sessions | Memory, compaction, projects, skills, multi-agent, bigger windows | Lead with what matters; chunk; start fresh with a summary |
| **Steerability** | Short, concrete, checkable instructions | Reasoning drift, letter over spirit, prompt injection | System prompts, visible reasoning, JSON schema / function calling | State the goal; checkpoints; restate the goal, not the instruction |

| Other key terms | Meaning |
|---|---|
| Pre-training | Predict what comes next, billions of times; produces a document completer |
| Fine-tuning | Curated examples + human-preference rewards; produces an assistant |
| Fingerprints | Sycophancy · Verbosity · Over-caution · Loose confidence calibration |
| Collisions | NTP + knowledge = fake citation; working memory + steerability = forgotten constraints |
| Diagnosis | Name the properties first, then pick the matching fix |
| Calibrated trust | A habit: locate the task on each continuum and set verification to match the stakes |

## How it maps to Claude Code work (this training)
| Property | Where you'll see it | Habit or feature that handles it |
|---|---|---|
| Next token prediction | Plausible but wrong code, APIs or flags | Run tests; Day 4 "verify 2 claims"; trust runs over fluency |
| Knowledge | Repo conventions, new library versions, internal systems | CLAUDE.md (Day 5); MCP and docs lookup (Day 8); read code before editing |
| Working memory | Long sessions get worse; constraints forgotten | One topic per session, `/compact`, fresh session plus summary, subagents with their own windows (Days 4–6) |
| Steerability | "Fixes" a test by weakening it (letter over spirit); drift in long tasks; injected instructions in fetched content | Plan mode as a checkpoint; state the goal; hooks that enforce rules (Day 9); review diffs |
| Fingerprints | Agrees with a wrong review comment; padded answers | Ask it to push back; set a length or format |

## Apply to today's exercise
In `prompt-notes.md`, for any answer that went wrong, name the property (or the two that collided) and the fix you used. For one question, try the L3 sycophancy test: state a wrong assumption first, then invite disagreement, and compare.

## Self-check before the quiz
1. What does pre-training produce, and what does fine-tuning add? What would a raw model do with "What is the capital of France?"
2. Name the four fingerprints and where each comes from.
3. Why can't the model tell a real citation from a fabricated one?
4. "Does the AI know this?" is the wrong question. What's the right one?
5. Why is working memory different from the other three properties (the cliff)? What is lost in the middle?
6. Give an example of letter over spirit, and the fix (restate the goal, not the instruction).
7. Name the two classic collisions and their fixes.
8. Which property sharpens which D, according to the course?

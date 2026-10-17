# AI Fluency: Framework and foundations — detailed lesson summary (Day 1)

Course: https://academy.claude.com/courses/ai-fluency-framework-foundations
Presenters: Prof. Rick Dakan (Ringling College of Art and Design), Prof. Joe Feller (Cork University Business School), Drew Bent (teacher, programmer, member of technical staff at Anthropic), Maggie Vo (Anthropic education research).
Format: 14 lessons, ~4 hours, final quiz (10 questions, ~8 min, **pass ≥ 80%**), completion badge with a shareable, verifiable link.
Framework copy: ringling.libguides.com/ai/framework

> Sources: the full video transcripts of all 11 video lessons (L1–L6, L8–L10, L12, L13), plus the text of L7, L11, L14 and Additional activities (no video). Read 2026-10-17. This is a study aid. It doesn't replace taking the course, and the badge needs the quiz passed in Academy.

---

## Big picture
- **AI Fluency** = the ability to work with AI in ways that are **effective, efficient, ethical and safe**: get the most out of AI without wasting time or energy, honestly and responsibly, protecting your own and others' privacy and security.
- It is a mix of **practical skills, knowledge, insights and values** that reinforce each other and adapt as the technology changes. It is not memorising "the 10 best prompts" for this month.
- The course aims to change *how we think about AI* so we can *think with AI*: AI as a trusted partner for creative problem solving, "not some sort of next-level spell checker".
- It rests on **four competencies, the 4Ds** (Delegation, Description, Discernment, Diligence), which apply across **three modes**: Automation, Augmentation, Agency.
- Delegation, Description and Discernment mostly serve *effective and efficient*. Diligence mostly serves *ethical and safe*.
- The one idea the presenters say to keep if nothing else: **"AI tools are not databases or vending machines."** They are interactive systems that behave differently in different contexts, much like people.

```
            Automation      Augmentation       Agency
            (AI does task)  (think together)   (AI acts for you)
Delegation  ───────────────────────────────────────────────  what / who
Description ───────────────────────────────────────────────  how to ask
Discernment ───────────────────────────────────────────────  is it good?
Diligence   ───────────────────────────────────────────────  am I responsible?
```

---

## Lesson-by-lesson

### L1 · Introduction to AI Fluency (15 min)
- The course is about **us humans and how we collaborate with AI**, not about AI as a technology.
- Why it exists: the AI world moves fast. Organisations adopt AI without clear strategies and people get frustrated with systems they don't understand. The gap between what's possible and what feels comfortable keeps widening. A grounding framework gets you past "that next prompt engineering fad".
- Most AI training teaches tactical skills (specific prompts, settings, which system to use), and these go out of date quickly. This course teaches core competencies that stay useful for the AI still to come.
- The 4Ds as questions:
  - **Delegation:** when should humans do the work and when should AI?
  - **Description:** how do we communicate clearly with AI?
  - **Discernment:** how do we evaluate what AI gives us?
  - **Diligence:** how do we make our use of AI responsible, transparent and accountable?
- Outcomes: a framework to guide your AI interactions, confidence about when and how to use AI, practical collaboration skills, and the ability to evaluate and take responsibility for the outcomes.
- Exercise: get access to any LLM (a free claude.ai account is enough). Reflect on past problems with AI and on what you hope to get from the course.

### L2 · Why do we need AI Fluency?
- The gap between *having* AI and being *fluent* with it shows up as moments like these:
  - getting an unexpected response and not knowing how to proceed;
  - struggling to explain what you need;
  - wondering whether the data you share is protected.
- **Three modes of engagement:**
  - **Automation**: AI completes a specific task from your instructions (summarise a document, draft an email, plan an itinerary). It works well when you know the outcome you want. It is harder when you're not yet sure what you're looking for.
  - **Augmentation**: AI is a creative thinking and problem-solving partner, for example developing a story character, working through an app architecture problem, or forming a view on a research topic. "It doesn't do work for you, but helps you do your work better." This mode suits problems where the answer isn't straightforward and you need room to explore.
  - **Agency**: AI works independently on your behalf, for example sorting emails by urgency and drafting replies, or powering an interactive game character. You set up the AI's *knowledge and behaviour patterns* rather than specific actions: "less like a script writer giving exact directions and more like a director setting a vision".
- None of the modes is better than the others, and one project can use all three. Many people start with automation, but augmentation and agency make the most of what is unique about AI and often give the most creative results.
- AI can be a tool, a medium, a partner or a co-creator, sometimes all at once. Because AI now plays these new roles, we take on new roles too and need new skills.

### L3 · The 4D Framework (video 5 min)
| D | Definition | Transcript example |
|---|---|---|
| **Delegation** | The big picture: what you're trying to achieve, what work is involved, what you do yourself and where AI helps | Research project: AI reviews long documents and data and discusses the implications with you. You keep the critical analysis and the final conclusions |
| **Description** | Clear communication with AI | "Make me a logo" versus describing the company's values, audience, colours and style references. As a tutor: "don't tell me the answer, help me work through it step by step" |
| **Discernment** | Thoughtfully evaluating what AI gives you | Marketing strategy: are the facts accurate, does the reasoning hold, does it fit the brand and audience, does it actually help you move forward? |
| **Diligence** | Responsible AI interactions; owning and standing behind the final product | AI-assisted hiring: fairness and bias, checking accuracy, protecting sensitive data, being open about AI's involvement, being accountable |
- Delegation needs three things: understand the goal, know what AI can and can't do, then divide the work. "Delegation isn't just about offloading tasks."
- Description sets out the goal and the output format, how AI should approach the task, the context it needs, and the tone and style of the interaction.
- Discernment draws on your domain expertise. Part of it is recognising when output needs refining or "should be set aside entirely".
- Most AI interactions are **small loops of description and discernment**: describe, evaluate, refine, and repeat.
- The 4Ds aren't tied to particular tools, so they stay useful as AI changes.
- Exercise "Apply the 4Ds": pick a scenario (marketing emails, dataset research or story characters) and answer one question per D. Reflect on which D is your strongest and which is your weakest.

### L4 · Generative AI fundamentals (Drew Bent, video 6 min)
- **Generative AI creates new content.** Traditional AI classifies (for example spam or not spam), while generative AI writes a new email.
- **LLMs:** *language* models because they are trained to predict and generate human language; *large* because they have billions of **parameters**, the values that determine how the model processes information, "somewhat like synaptic connections".
- Three developments came together:
  1. **Algorithms:** the **transformer architecture (2017)** handles sequences of text while keeping track of relationships between words across long passages.
  2. **Data:** websites, code repositories and other texts.
  3. **Compute:** GPUs, TPUs and distributed clusters.
- **Scaling laws:** more parameters, data and compute give predictably better performance. Surprisingly, **emergent capabilities** also appear that nobody programmed, such as step-by-step reasoning and adapting to new tasks from minimal instruction.
- **Pre-training:** predict the next piece of text across billions of examples, which builds "a complex map of language and knowledge".
- **Fine-tuning:** learn to follow instructions, be helpful and avoid harmful content, using human feedback and **reinforcement learning** (rewards and penalties) toward helpful, honest and harmless behaviour.
- **At use time:** your prompt is text the model *continues from*. It is not retrieving pre-written answers from a database; it generates new text that statistically follows.
- **Context window** = the AI's working memory: your prompts, its responses and anything you've shared. It can't use content outside the window without tools such as web search.
- Three traits that make modern LLMs powerful:
  1. They learn from huge amounts of information.
  2. **In-context learning:** they adapt to a new task from instructions or examples in the prompt, without retraining.
  3. Emergent capabilities that come from scale.

### L5 · Capabilities & limitations (video 7 min)
Framing: treat it like getting to know a new colleague's strengths and weaknesses.

**Capabilities**
- Versatile with language: emails in your own voice, summaries of long reports, translation, explaining topics "from microbiology to marketing strategy".
- **Switches tasks without retraining:** the same system writes poetry and then analyses quarterly trends.
- **Keeps the thread of a conversation:** it remembers a deadline you mentioned earlier.
- **Uses external tools:** web search, file processing, other applications.

**Limitations**
| Limitation | Detail from the transcript |
|---|---|
| **Knowledge cutoff** | No built-in knowledge after its training date, like someone who went on a retreat with no internet. It needs web search for recent events |
| **Hallucination** | Training doesn't verify facts, so the model can repeat inaccuracies or misassemble what it learned. The result is *confident, plausible and wrong*, "like a friend who tells a story with absolute confidence, only to have the details completely wrong" |
| **Context window** | A hard limit. Once it is exceeded, older content drops out, usually *first in, first out*, which limits long documents and long conversations |
| **Non-deterministic** | The same question can get different answers because generation is probabilistic. Good for brainstorming, risky where consistency matters. **Temperature** controls the randomness |
| **Complex reasoning** | Historically weak at multi-step maths and logic. Newer *reasoning / extended thinking* models are improving |
| **Missing data or tools** | "A brilliant colleague who can't access your company's internal database." If it doesn't have the data or the tool, it can't answer |

- The field is working on these: **retrieval-augmented generation (RAG)**, better tool use and better reasoning. Some limits will probably remain.
- **Complementary strengths:** humans bring critical thinking, judgment, creativity and ethical oversight. AI brings speed, scale, pattern recognition and processing of huge amounts of information. Keep experimenting, because the balance shifts as the technology changes.

### L6 · A closer look at Delegation (20 min, video 6 min)
- Delegation mainly serves **effective and efficient** work. It means deciding what work needs doing, what you do yourself and what suits AI. That sounds simple but is "surprisingly nuanced".
- "The cornerstone of good delegation isn't actually about AI at all": it is your own expertise. **"The most effective AI collaborators are experts in their fields first and AI delegators second."**
- **Problem Awareness:** define your goals and the work needed *before* bringing in AI.
  - What are you creating or solving? What is your vision? What does success look like?
  - What kinds of work are involved? Simple but time-consuming, uncertain (you need a thinking partner), unknown (you need more data), or critical judgment?
- **Platform Awareness:** a working knowledge of the available AI systems.
  - Which models suit this work? Which ones favour speed over depth, or accuracy over creativity?
  - The way to learn is hands-on: experiment with different systems often and build your own insight.
- **Task Delegation:** divide the work to use each side's strengths.
  - What to automate? Where does augmentation add more value than working alone or full automation?
  - What critical judgment must **stay human**? What routine interactions can **agents** handle?
  - Every piece of work ends up as one of: AI-automated, collaborative, human-only, or agent-run.
- "Effective AI collaboration isn't about handing over the wheel and calling it a day."
- Exercise prompt: *"Hi Claude, I'm preparing how to [task] and want to discuss with you what a delegation plan may look like for figuring out which parts I should delegate to an AI like you vs. not."* Work through the vision, what good looks like, the pieces of work, and which pieces need human judgment. Then write the delegation plan.

### L7 · Project planning and Delegation (text lesson)
- Choose a **course project** that is substantial (several task types), doable in about 1 hour and that you care about. Types: communication, research, creative, learning.
- Step 2: tell Claude the idea and let it **ask you questions** until the vision and the success criteria are clear.
- Step 3: break the project into major tasks. For each one, decide which skills or AI capabilities it needs, which human strengths it uses, where AI helps, and where collaboration has the most impact. Challenge assumptions as you go.
- **Save the plan.** You reuse it for Description, Discernment and Diligence.

### L8 · A closer look at Description (15 min, video 4 min)
- Description is "the core of most human–AI interaction". It is more than clever prompts:
  - explaining tasks, asking questions and giving context;
  - **steering a conversation that is going wrong**;
  - guiding the AI's reasoning;
  - building a thinking environment where both sides do their best work, "a bridge between your intentions and the AI's capabilities".
- **Product Description: what you want.** "Make dinner" versus a recipe with ingredients. **"AI can't read your mind."** Cover context, the exact task, format, audience and style. Don't make it guess.
- **Process Description: how it should approach the task.** Sometimes this matters as much as the goal, or more.
  - Guidance can be general (like a manual), step by step (like a cookbook) or a demonstration ("here's how I do it").
  - Tell it which data to use, which issues to address and in what order, the style of analysis, and the workflow or technique.
- **Performance Description: how it should behave with you.** "AI tools are not databases or vending machines."
  - Before you start, ask what kind of thinking partner you need.
  - Narrowing to an answer, or exploring options? Challenge my assumptions, or follow my lead?
  - Detailed or concise? Explain the reasoning, or just give the answer?
- Exercise **"Bad Prompt Makeover"**: Claude gives you bad prompts and you fix them using all three types. Then swap roles. Reflect on which type you tend to leave out.

### L9 · Effective prompting techniques (video)
- Prompting is "how we apply the description competency in practice". Treat it like briefing "a helpful new colleague who's eager to assist, but needs clear directions".
- **Prompt engineering** = designing effective instructions for AI. Normal human communication principles apply (clarity, context, concrete examples). The AI-specific parts:
  - be more explicit about things a human would infer;
  - respect the limited context window;
  - sometimes use machine-friendly formatting.
- Best practice changes as models change, so experiment.

**The six techniques, with the transcript's examples**
1. **Give context: what, why, and who you are.**
   - Weak: "Tell me about climate change."
   - Better: "Explain three major impacts of climate change on agriculture in tropical regions, with examples from the past decade."
   - Best adds the *why* and *who*: preparing for an interview at an agricultural lab in Indonesia, with an ecology degree but no climate-change background.
2. **Show examples (few-shot / n-shot).** Try without examples first. Add them when a style is easier to show than to explain, e.g. two jargon-to-plain-language pairs. Cover the full diversity of cases.
3. **Specify output constraints:** format, length, language, design details. Example: a portfolio site with named sections, a sticky responsive nav with a hamburger menu, a sunset palette and a dark/light toggle.
4. **Break complex tasks into steps (chain of thought).** Example: "analyse sales data" becomes find the top products, compare with last quarter, flag unusual trends, then suggest reasons. Reasoning models do much of this alone. The more ways a task can be done, or the more it depends on your domain experience, the more worthwhile it is to spell out the steps.
5. **Ask it to think first:** "Before answering, please think through this problem carefully…" Thinking must come **before** the answer to improve quality; explaining afterwards is not the same. A side benefit: you can see where it goes astray.
6. **Define role, style or tone.** Examples: "an experienced science teacher speaking to a bright 10-year-old"; a persona such as Richard Feynman; "As a UX design expert, review this wireframe…".

**Other points**
- **Secret weapon:** ask Claude to help write or improve your prompt: *"I'm trying to get you to help me with [goal]… can you help me craft an effective prompt?"* Models differ most on this.
- **When results miss:**
  - add specificity or context, add examples, split into steps, or combine techniques;
  - ask for 3 variations, or a different format (e.g. an artifact);
  - ask "How confident are you about this answer?";
  - **start a fresh conversation**, which can beat correcting one that has gone off track.
- **Patterns that work:** a clear task overview first; format specs and examples; explicit constraints; rich, relevant background.
- **Common mistakes:**
  - assuming it can read your mind;
  - overloading one prompt or conversation with unrelated tasks;
  - being vague about what success looks like;
  - not giving feedback on earlier responses.
- Some techniques become unnecessary as models improve, but the communication principles stay.

### L10 · A closer look at Discernment (20 min)
- Discernment means evaluating AI **outputs, processes and behaviours**: "your quality control system for AI collaboration".
- It is the flip side of Description and needs both **domain expertise** (to judge quality) and knowledge of how AI typically fails.
- **Product Discernment:** is it factually accurate, appropriate for the audience and purpose, coherent and well structured? Does it meet the requirements and add value?
- **Process Discernment:** how did it get there? Watch for:
  - logical errors and lapses in attention;
  - inappropriate steps;
  - **getting stuck on one detail or interpretation** and being unable to consider alternatives;
  - **circular reasoning**.
  - Example: after several rounds on one of five outline options, **elements of ideas you rejected creep back in**.
  - This matters most on complex tasks where the right answer isn't obvious.
- **Performance Discernment:** how well it interacts with you while it works. *Process* = the work; *performance* = the interaction.
  - Is it helpful and responsive to feedback? Is the exchange efficient?
  - Does it ask too many questions when you want concise answers, or stay too brief when you need depth?
- **Feedback after evaluation:**
  - Say *what* the problem is and *why*, give concrete suggestions, and revise your instructions or examples.
  - **Better description is often the fix.** Sometimes you need to rethink **delegation** instead: the wrong tool or the wrong approach.
- Exercise **"Expert Discernment"**: on a topic you know well, ask for 3 explanations. Judge each on product, process and performance, pick the best and worst, then build an improved version with Claude. Reflect on how much domain knowledge discernment needs.

### L11 · The Description–Discernment loop (text, 10 min)
- Apply both skills to the course project, looping for **each task**:
  1. **Describe**: the product, process and performance you want.
  2. **Discern**: judge the output, the approach and the behaviour.
  3. **Refine**: give feedback, adjust the description, ask for another version.
  4. **Integrate**: add your own expertise, decide what to keep, change or drop, and **own the final output**.
- Goal: results better than either human or AI could produce alone.
- Reflect: which descriptions worked best? Which took more effort, describing or discerning?

### L12 · A closer look at Diligence (20 min)
- Diligence covers **ethics and safety**, "just as crucial" as effectiveness. It means taking responsibility so your AI use is **rigorous, transparent and accountable**.
- Broader questions to ask:
  - What are the implications of using AI here?
  - Who could be affected by the output, by the collaboration itself, or by inaccuracies you missed?
  - Who has access to the data?
  - Does this fit ethical standards?
- Analogy: driving. You don't only get from A to B; you also drive safely, follow the rules and think about other people on the road.
- **Creation Diligence:** be critical about *which systems* you use and *how*.
  - How was the system built and trained, and on what data?
  - Who owns the data you enter, and who can access it once it's shared?
  - How are you protecting privacy and security? Does this fit your values and your organisation's policy?
  - Example: before sharing sensitive company information, check the service's data-protection policy and whether your organisation allows it.
- **Transparency Diligence:** be honest and accurate about AI's role with **everyone who needs to know**.
  - Personal, academic, creative and professional settings expect different disclosure, and it's on you to know which applies.
  - Ask who needs to know, how and when to tell them, and in how much detail.
  - It's about trust, not only rules: people have a right to know when AI played a significant role in work or decisions that affect them.
  - Example: tell teammates which parts of a proposal were AI-assisted.
- **Deployment Diligence:** **you, not the AI**, are responsible for what you share. Verify facts, check for bias, confirm accuracy and usage rights.
  - Example: a journalist verifies every fact and source of an AI-assisted article against the same standards as if they had written it alone.
- Build **personal guidelines** that match your values, learn your organisation's policies and industry standards, and keep up as AI law and regulation evolve.
- Exercise **"Diligence Statement"** (~14 min): reflect on all three types for your project, then draft a statement with Claude. It names the AI used, what AI contributed, how you reviewed the work, that you take responsibility, and any context-specific notes. Attach it as a footer, appendix or metadata.

### L13 · Conclusion (Rick Dakan, 30 min)
- A recap of the 4Ds, each with its three parts, across the three modes.
- One insight per D:
  - **Delegation:** your expertise and judgment stay the foundation. The biggest wins come from *iterative* collaboration where you and AI build on each other's strengths.
  - **Description:** the best instructions are often not elaborate prompts but **thoughtful conversations** that give context, examples and feedback.
  - **Discernment:** critically evaluating AI output is **non-negotiable**.
  - **Diligence:** you remain accountable for what you do with AI and its effect on others. Transparency builds trust.
- Fluency grows **through practice, not overnight**. "The goal isn't perfection" but continuing awareness, commitment and intention.
- AI is "not a silver bullet". It's only as useful and safe as we enable it to be. Invest in your own expertise and keep up with change.
- Closing line: **"Be effective and efficient. Be ethical and safe."**
- Exercise: talk with Claude about how the 4Ds apply to your job and where you're strong or weak. Share an exercise you did and ask for feedback.

### L14 · Course quiz
- 10 questions, ~8 min, **≥80% to pass**. Topics: what AI Fluency means, the 4Ds and the parts of each. After passing, choose **"Create your completion badge"**.

### Additional activities (optional)
1. **Personal AI Fluency Plan**: rate yourself on each D (novice, developing or confident), pick 1–2 to focus on, and plan activities, a timeline and how you'll measure progress.
2. **Prompt & Pattern Library**: 5–10 recurring tasks, each with a template prompt and placeholders. Record the Description techniques and Discernment checks that work.
3. **Game Night**: word puzzles have precise rules, which makes them good Description practice, and they are deliberately tricky, which makes them good Discernment practice. Options: riddles, cooperative crosswords, word association, Twenty Questions, collaborative storytelling, RPG, concepts and constraints, and Project Euler.

---

## Cheat sheet (quiz prep)
| Competency | Sub-parts | One-line test |
|---|---|---|
| Delegation | Problem Awareness · Platform Awareness · Task Delegation | Do I know the goal, the tool, and who does what? |
| Description | Product · Process · Performance | What, how, and how to behave |
| Discernment | Product · Process · Performance | Is the output good, the reasoning sound, the interaction working? |
| Diligence | Creation · Transparency · Deployment | Right tool and data, honest about AI, I vouch for it |

| Other key terms | Meaning |
|---|---|
| Modes | Automation (task) · Augmentation (partner) · Agency (acts for you, "director not script writer") |
| Loop | Describe → Discern → Refine → Integrate |
| Prompting | Context · Examples · Constraints · Steps · Think first · Role/tone + ask Claude to improve the prompt |
| LLM basics | Transformer (2017), data, compute; scaling laws; emergent capabilities; pre-training then fine-tuning (RLHF); in-context learning |
| Limits | Knowledge cutoff, hallucination, context window (FIFO), non-deterministic (temperature), multi-step reasoning, missing data or tools |
| Fixes | Web search / RAG, tools, reasoning models, fresh conversation, "how confident are you?" |

## Applying it to this training
| D | Dojo practice |
|---|---|
| Delegation | Day 2 ticket analysis, Day 4 plan mode, Day 6 parallel sessions and subagents (agency mode) |
| Description | Day 1 prompt exercise, Day 5 CLAUDE.md (persistent context), Day 7 skills (reusable process description) |
| Discernment | Day 4 "verify 2 claims", Day 10 review false positives, "verify by running"; watch for rejected ideas reappearing in long sessions (process discernment) |
| Diligence | No secrets or client data in AI tools (creation); state AI's role in each MR (transparency); MR "how tested" (deployment); Day 15 playbook |

For today's exercise (`prompt-notes.md`): tag each v1 → v2 change with the D and sub-part it improved (e.g. "added audience = Product Description"; "asked it to think before answering = Process Description").

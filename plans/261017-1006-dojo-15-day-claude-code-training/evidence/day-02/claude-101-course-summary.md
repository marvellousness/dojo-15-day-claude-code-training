# Claude 101 — detailed lesson summary (Day 1 task 1.4 → Day 2 task 2.7)

Course: https://academy.claude.com/courses/claude-101
Format: 13 lessons + certificate/quiz (lesson 14), completion badge.

> Sources: the full lesson content modules that the Academy pages load (video transcripts + page text), read 2026-10-20. L7's video has no spoken narration (screen demo only), L3's video is reused from the "AI Fluency for nonprofits" course, and L14 has no readable text. This is a study aid; the badge needs the course and quiz done in Academy.

---

## Big picture
- **Claude is the intelligence; claude.ai is one of several ways to use it.** The course focuses on claude.ai (web, desktop, mobile) and points developers to Claude Code in Action.
- **Claude is a thinking partner, not a chatbot**: it can both *automate* and *augment* work. "Claude brings AI intelligence; you bring the context and expertise that make the work meaningful."
- **A good prompt has three parts:** set the stage (role, objective, context) → define the task (the action) → specify rules (style, tone, format, examples). This is the AI Fluency *Description* competency in practice.
- **Iteration is the norm**: treat the first answer as a draft, refine by conversation, start fresh when a thread goes off track.
- **Context layers, from one chat to everything:** uploads → Instructions for Claude / Memory → Projects (knowledge + instructions) → Skills (repeatable procedures) → Connectors (your tools, via MCP) → Enterprise Search / Research.
- **Three shapes of work in the desktop app:** turn by turn (Chat), handing work off (Cowork), building software (Code tab).

```
How much context Claude has          What you build with it
──────────────────────────           ───────────────────────
single prompt + uploads    ─┐        chat answer
Memory / Instructions       ├──→     artifact (design, deck, doc, tool)
Project knowledge + instr.  │        file (xlsx, pptx, docx, pdf via Skills)
Skills (process)            │        finished deliverable (Cowork)
Connectors (MCP) / Research ┘        code + commits (Claude Code)
```

---

## Lesson-by-lesson

### L1 · What is Claude?
- An AI assistant for life and work, trained with **Constitutional AI**: principles to avoid harmful or discriminatory output and behave safely and transparently.
- More than a chatbot: summarisation, search, writing, Q&A, coding, all with high reliability and predictability. **Steerable** on personality, tone and behaviour.
- Available on every plan (Free, Pro, Max, Team, Enterprise); conversations, projects, memory and preferences sync across web, desktop and mobile.
- **What it's good at:**
  - writing and content;
  - research and analysis, with a large context window (200K+ tokens, about 500 pages; up to 1M tokens on paid plans with supported models);
  - **coding**, described as "one of Claude's greatest strengths";
  - problem solving, with **Thinking** (reason step by step before answering);
  - learning, with **Learning mode**, which guides your reasoning instead of giving answers.
- **Ways to access Claude:**
  - claude.ai (the focus of this course);
  - Claude Code (agentic coding: edits files, runs commands, makes commits);
  - Claude Tag (Slack);
  - Claude Design (interactive prototypes);
  - Claude for Microsoft 365 (Excel, PowerPoint, Word and Outlook sidebars).
- Reflection: which tasks in your calendar could use a thinking partner?

### L2 · Your first conversation with Claude
- **Sidebar:** new chats, past chats, projects (persistent context and instructions), artifacts (shareable apps, tools and content).
- **Prompting like a coworker**, naturally and concisely, using the three-part framework. Example:
  - Stage: "marketing lead at an indie streaming startup, preparing a Series A pitch deck".
  - Task: "research the independent film streaming market: trends, competitors, growth".
  - Rules: "current web research with citations, a professional report of up to 5 pages with these sections".
- **Adding context:**
  - Uploads: PDF, DOCX, CSV, TXT, PNG and JPEG. Claude reads text *and* visuals (charts, images); upload code and ask it to explain the code or find bugs.
  - **Search and tools menu:** web search and connected sources such as Google Drive. Claude picks tools as needed.
  - **Settings > Account > Instructions for Claude:** preferences applied to every conversation.
- **Model choice:**
  - **Opus** for the most complex tasks, such as multi-step analysis.
  - **Sonnet** as the recommended everyday default, balancing capability and cost.
  - Changing the model starts a new chat.
  - **Extended thinking** helps on complex analysis but adds latency; start with Sonnet with thinking off for simple tasks.
  - Research-style runs explore hundreds of sources and take 5–45 minutes.
- **Iterating:** ask follow-ups ("expand on point 2", "more concise"), give feedback, redirect or restart, or **edit your earlier message with the pencil icon** and resubmit.
- **Personalisation:**
  - **Memory** saves your role, preferences, past decisions and working style. You can review, edit or delete it in Settings > Memory. It's on by default for Free, Pro and Max, off by default for Team and Enterprise (the org Owner decides).
  - **Skills** apply reusable instructions automatically when relevant.

### L3 · Getting better results
- **Common problems and fixes:**
| Problem | Why | Fix |
|---|---|---|
| Too generic | Not enough context | Add audience, role, constraints (e.g. "enterprise client, second delay, professional but apologetic") |
| Too long or too short | Claude guesses the length | State it: "two paragraphs", "under 100 words" |
| Ignores your format | It understood *what*, not *how* | Show an example or describe the structure ("bullets with bold headers") |
| Confident but wrong | Plausible errors, especially on specific facts or niche topics | Verify key facts; ask for sources or a confidence level; turn on web search |
| Wrong tone | Defaults to helpful and professional | Describe the tone you want |
- **Iteration mindset:** first drafts are starting points; give specific feedback; **know when to start fresh** in a new chat.
- **AI Fluency 4D recap**, from Professors Dakan and Feller: the L2 prompt framework is *Description*; troubleshooting is *Discernment* and *Diligence*.
- **Evals for your own work:**
  1. Gather 5–10 examples of a task you already do.
  2. Write test prompts with the context you'd naturally have.
  3. Compare Claude's output with your examples: key information, tone, what's missing.
  4. Refine the prompts or add examples, and decide where human review stays essential.
- **Video, the "delegation–diligence loop":** Rio, a nonprofit program director, tests AI on *last quarter's* data, where he already knows the answer.
  - The first attempt finds the attendance and placement link but misses the housing-plus-job program insight. Adding "pay attention to program type" fixes it, and he notes that for future runs.
  - He also learns that AI needs enrolment dates for cohort analysis, otherwise it guesses.
  - **The method:** pick a task, find past data with known answers, reproduce it with AI, evaluate, refine, re-test. Delegate only if the results match. "Validation builds confidence, but it doesn't eliminate responsibility."

### L4 · The Claude desktop app: Chat, Cowork, Code
- **Three shapes of work**, and knowing which one you're in is "the whole skill":
| Shape | Use when | Lives in |
|---|---|---|
| **Turn by turn** | The answer changes your next question; you want to stay in it; it's quick | **Chat**: quick entry (double-tap Option on Mac), screenshots and window sharing, dictation, desktop connectors |
| **Handing work off** | Several sequential steps; output is finished files where you need them; spans your tools; runs on a schedule | **Cowork**: local folder access (reads and saves back), scheduled tasks (run remotely even when your computer is asleep; local-file tasks only while the app is open), subagents (parallel workers with their own context), projects, browser use (Claude in Chrome), computer use (research preview, asks per app, has a blocklist), plugins |
| **Building software** | Write, test, run and ship code | **Code tab**: Local (a folder on your machine, dev server preview) or Cloud (a GitHub repo, keeps running after you close the app); modes: manually approve, accept edits, plan |
- In a hand-off you stay in control: Claude may ask scoping questions, shows its plan and progress, and can stop for approval before actions like sending an email.
- Cowork is on Pro, Max, Team and Enterprise.
- Reflection: which of your requests this week were really whole tasks fed in one question at a time? Write the *outcome* you'd hand off, not the first question.

### L5 · Introduction to projects
- **Projects are self-contained workspaces:** their own chat history, knowledge base and instructions, plus memory.
- **Project knowledge:** uploaded files are used in every chat in the project. When it nears the context limit, **RAG** kicks in automatically: Claude searches the files and pulls only what's relevant, **up to 10× capacity**, with a visual indicator.
- **Project instructions:** tone, expertise level, response style, requirements. They work alongside your user preferences. You can also **automate workflows**: "When I upload a meeting transcript, create a structured summary using this template."
- The project *description* is for humans; Claude doesn't see it.
- **Name files descriptively:** Claude uses file names for retrieval ("Q4-2024-Brand-Guidelines.pdf", not "document1.pdf").
- Files uploaded *in a chat* stay out of the knowledge base, and **context isn't shared between chats** unless it's in project knowledge.
- **Sharing (Team and Enterprise):**
  - Can view: read the contents and chat.
  - Can edit: change instructions and knowledge, manage members.
  - Owner: everything, including visibility.
  - Use the "Shared with me" tab; you get email notifications when something is shared.
- **When to use projects:** reusable reference material, consistent requirements, team collaboration.
- **Best practices:** start focused, keep knowledge current, write clear instructions, name documents descriptively and refer to them by name.

### L6 · Creating with artifacts
- **Artifacts are standalone outputs:** documents, decks, designs, dashboards, prototypes. They render in a window next to the chat. Say "Create this as an artifact" to force one.
- They're saved in the **Artifacts tab**, separate from the conversation, so you can keep editing and sharing them.
- **Claude Design** (visual work, edit on canvas, use your design system), **Claude Slides** (decks, export to PPTX, Google Slides or PDF) and **Claude Docs** (living rich-text documents, co-editing, Claude explains its edits in comments, charts from connected tools as snapshots) are on every plan. Team and Enterprise admins can turn them off.
- **Artifacts vs file creation:** an artifact opens and updates inside Claude and is shared by link; file creation gives you an .xlsx, .pptx, .docx or .pdf to download.
- **Three ways to change an artifact:** edit it directly, leave a comment for Claude (in Docs, @-mention it), or just ask in the chat.
- **Sharing:**
  - Private until you share.
  - Team and Enterprise: per person, view, comment or edit, or the whole org.
  - Pro and Max: private or anyone with the link (the conversation stays private).
  - Free: private only.
  - Viewers need a Claude account.
- **Tips:**
  - Ask for the deliverable, not just the content.
  - Be specific: "a monthly budget tracker with categories, a pie chart and an over-budget warning".
  - Describe the end user.
  - Build at the end of a conversation, once Claude understands the problem.
  - Refresh embedded data on request.

### L7 · Working with Skills
- **Skills are folders of instructions, scripts and resources** that Claude loads when relevant: "expertise packages" for repeatable tasks.
- **Anthropic Skills** power file creation (Excel, Word, PowerPoint, PDF). **Custom Skills** encode your workflows: brand review, meeting-note format, an analysis method.
- **To enable:** Settings > Capabilities > **Code execution and file creation** must be on (an org Owner controls this on Team and Enterprise), then Customize > Skills to switch individual skills on or off.
- Claude picks skills automatically and mentions them in its reasoning. Output is a downloadable file, or an artifact if Slides and Docs are on. Claude can also **edit your real files**: it makes a new version of uploaded .xlsx, .pptx, .docx and .pdf files. "Allow limited network access" may be needed.
- **Security:** skills can contain executable code. Only install them from trusted sources and review external skills before use. Custom skills are private to your account.
- **Creating one:** in a new chat, Claude interviews you (what it should do, what good output looks like, examples). Upload reference materials, then "Save skill". If it doesn't appear, upload the file via Customize > Skills > Add.
- **Projects vs Skills:** "projects store knowledge, skills perform tasks." A project provides the *what* (information); a skill provides the *how* (process); they combine.

### L8 · Connecting your tools
- **Connectors** let Claude read *and act* in your tools: search files, update records, create tasks. They're powered by **MCP**, "USB-C for AI".
- **Web connectors** (Google Drive, Notion, Slack, Asana, Linear, Stripe, …) and **desktop extensions** (local files, browser control, native apps such as Figma; desktop app only, Settings > Extensions).
- **The directory lists connectors, not apps:** for example, Atlassian Rovo covers Jira *and* Confluence. Missing tools can be added as **custom connectors**.
- **Setup:** find it, Connect (on Team and Enterprise an Owner must add it first), sign in, review and grant permissions, test.
- **Example prompts:** "highest-priority tasks due this week" (Asana or Jira), "find the email thread about the vendor contract" (Gmail), "what does our style guide say about contractions?" (Drive or Notion).
- **Security:**
  - Access is scoped, and you can toggle individual permissions.
  - **Claude sees only what you see.**
  - Access can be revoked any time.
  - Only install connectors from trusted sources.

### L9 · Enterprise Search (Team and Enterprise only)
- An **"Ask {Your Org}"** project in the sidebar, pre-configured to search and synthesise across the company's tools (documents, chat, email), with citations.
- **Setup has two steps:**
  1. An Owner connects a documents source (Drive or SharePoint) and a chat source (Slack or Teams), with email optional, and names the project.
  2. Each user authenticates their own accounts.
- **Uses:** catching up ("what happened while I was out?"), policies, onboarding ("how does our auth system work?"), past decisions.
- **Safety:** it only shows what you can already access, and data isn't indexed or stored separately.

### L10 · Research
- **Agentic, multi-step investigation:**
  1. It plans with Thinking.
  2. It runs many searches that build on each other, often in parallel across hundreds of sources.
  3. It synthesises the results, including from connected tools such as Gmail, Calendar and Drive.
  4. It cites sources for every claim.
- **It takes minutes, not seconds.** It needs web search enabled; turn it on via the tools button, then **Research**.
- **Use Research for** comprehensive, multi-source, comparative reports with citations.
  - Use **web search** instead for a quick fact.
  - Use **Thinking** instead for pure reasoning: maths, debugging, logic.
  - Use **Enterprise Search** instead for internal company knowledge.
- **Prompt tips:** specific goals, the sections you want, constraints (budget, timeline, region), and ask Claude to improve your Research prompt first.

### L11 · Claude in action: use cases by role
- **General:** status reports, patterns in user feedback, packaging brand guidelines as a skill.
- **Sales:** battle cards, deal prep, sales reports.
- **Marketing:** campaign analysis, adapting content across platforms.
- **Finance:** financial models, investment memos, understanding inherited spreadsheets.
- **HR:** onboarding guides.
- **Legal:** discovery timelines.
- **Research:** literature reviews, checking statistics against raw data.
- Each case links to a step-by-step guide in the **Use Case Gallery**.

### L12 · Other ways to work with Claude
- **Claude Code:** agentic coding in the terminal, IDE, browser or Slack. Builds features from plain English (writes code, runs tests, makes commits), debugs from pasted errors, explains unfamiliar codebases, automates lint fixes, **merge conflicts** and release notes.
- **Claude Tag** (Slack): drafts replies, summarises threads, preps meetings, **hands a bug report straight to a Claude Code session** using the thread as context.
- **Claude Design:** from a brief, sketch or screenshot to a working prototype; compare variations; use your design system; hand off to Claude Code.
- **Claude for Excel** (video demo, an HR headcount model):
  - explains how the tabs connect;
  - finds and fixes a `#REF!` cascade, asking how to resolve it;
  - runs a scenario (attrition 10% → 15%) and explains what changed;
  - builds a pivot table and chart;
  - adds a "fully loaded cost" column **using formulas, not pasted values**.
- **Claude for PowerPoint, Word and Outlook:** sidebars that edit in place, keep the template and styling, work with tracked changes, and triage mail.
- **Claude in Chrome:**
  - A browser sidebar that observes and acts: summarise pages, fill forms, test multi-step web flows, pull data from internal tools.
  - Paid plans only.
  - Asks before high-risk actions (purchases, sharing personal data).
  - Blocks some site categories by default and has prompt-injection protections.
  - Recommended for low-risk tasks on trusted sites.

### L13 · What's next
- Recap of every module. Resources:
  - AI Fluency;
  - **AI Capabilities and Limitations**;
  - the Use Case Gallery and Help Center;
  - the prompting docs;
  - **Claude Code in Action**;
  - Introduction to Claude Cowork;
  - the Connector Directory.
- "Start simple. Pick one recurring task from your work this week and try it with Claude." Claude is "a collaborator, not a replacement".

### L14 · Certificate of completion
- Quiz and badge in Academy (no readable lesson text). Save the personal `/badges/<id>` link as for Day 1.

---

## Cheat sheet (quiz prep)
| Topic | Remember |
|---|---|
| Prompt framework | Set the stage (role, objective, context) · Define the task (action) · Specify rules (style, format, examples) |
| Fixes | Generic → add context · Length → state it · Format → show an example · Wrong facts → verify, cite, web search · Tone → describe it · Off track → new chat |
| Models | Opus = most complex · Sonnet = everyday default · Thinking = deeper but slower · changing model = new chat |
| Personalisation | Instructions for Claude (every chat) · Memory (Settings > Memory; on for Free/Pro/Max) · Skills |
| Desktop shapes | Chat = turn by turn · Cowork = hand-off (folders, schedules, subagents) · Code tab = software (Local or Cloud) |
| Projects | Knowledge + instructions + chats · RAG up to 10× · the description isn't seen by Claude · view / edit / owner |
| Artifacts | Standalone, in the Artifacts tab · Design / Slides / Docs · edit, comment or ask · artifact (link) vs file (download) |
| Skills | Instructions + scripts + resources · needs Code execution · Anthropic vs custom · "projects store knowledge, skills perform tasks" |
| Connectors | MCP ("USB-C for AI") · web connectors vs desktop extensions · Claude sees only what you see · revocable |
| Research vs others | Research = multi-source, cited, minutes · web search = quick fact · Thinking = reasoning · Enterprise Search = internal knowledge |
| Evals | 5–10 known examples → test prompts → compare → refine; validate on data with known answers before delegating |

## Applying it to this training (Developer track, Android)
| Claude 101 idea | Where it shows up in the Dojo plan |
|---|---|
| Three-part prompt, iteration, new chat when off track | Day 1 Q1–Q3 exercises (v1 → v2), Day 4 daily loop |
| Evals on known answers (Rio) | Day 2 task 2.4: hypotheses verified by running; Day 10 review false positives |
| Projects (knowledge + instructions) | Day 5 CLAUDE.md is the Claude Code equivalent of project instructions |
| Skills ("how") vs projects ("what") | Day 7: team skill `release-checklist` + personal command |
| Connectors / MCP | Day 8: GitLab MCP + Android device-control MCP |
| Chat vs Cowork vs Code tab | Day 4 (Code tab, permission modes, plan mode), Day 6 (parallel sessions, subagents, cloud session) |
| Claude in Chrome | Day 11 role day (adapted to Mobile for this Android track) |
| Claude Code automates merge conflicts and release notes | Day 2 task 2.6 conflict, Day 14 release-notes artifact |
| Verify confident facts, take responsibility | Same rule as AI Fluency Diligence: "How tested" in every MR |

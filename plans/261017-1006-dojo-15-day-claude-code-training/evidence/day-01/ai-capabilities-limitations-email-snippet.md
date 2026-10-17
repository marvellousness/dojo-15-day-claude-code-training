# AI capabilities and limitations — short version for the daily report email

**AI capabilities and limitations** (Claude Academy, 13 lessons + quiz, ~3.5h): <status: completed / in progress — quiz passed yes/no>

Key learnings:
- **Two training stages:** pre-training builds a document completer; fine-tuning turns it into an assistant. Fine-tuning leaves four "fingerprints": sycophancy, verbosity, over-caution and loose confidence calibration.
- **Four properties, each running from a strong end to a weak end:**
  - **Next token prediction:** fluent on well-worn tasks, but it can invent specifics (names, dates, stats, citations, URLs). A confident tone doesn't mean it's correct.
  - **Knowledge:** broad but uneven, with a cutoff date. Ask "how well-represented was this in what it read?"
  - **Working memory:** a fixed context window that fails suddenly. The oldest content drops silently, the middle of long input gets less attention, and a new session starts empty.
  - **Steerability:** follows short, concrete instructions well, but can obey the letter and miss the intent. State the goal, not just the format.
- **Most failures are two properties colliding.** A fake citation is next token prediction plus a knowledge gap. Forgotten constraints are working memory plus steerability. Name the properties first, then apply the matching fix.
- **Calibrated trust is a habit:** neither trust nor distrust the AI; work out where the task falls on each property and verify to match the stakes.

Takeaway for my work: check specifics and run the code rather than trusting fluent output. Put key context first or in CLAUDE.md. Start fresh sessions when quality slips. Add checkpoints (plan mode, tests) to long tasks.

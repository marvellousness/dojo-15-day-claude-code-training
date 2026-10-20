# Review checklist

Use it in this order for every MR. Each comment gets a tag: **[blocking]** (must fix before merge), **[suggestion]** (better, not required) or **[question]** (I need to understand before I can approve). Every comment names a `file:line`, the problem, and a fix or what I expect.

## 0. Before the code
- Read the MR description only. Could I review from it alone? What changed, why, how it was tested, what wasn't tested, risks.
- Does it build and do the tests pass from a clean checkout? (`./gradlew testDebugUnitTest lint`)

## 1. Correctness
- Does the code do what the description and the ticket say, for the normal case and the edges (0, 1, many, null, empty, failure)?
- Any behavior change that the description doesn't mention? Check what users saw before vs. after.
- Threading: does blocking or network work stay off the main thread? Is `CancellationException` rethrown?
- State: can a fast double tap, rotation, or an edit during a long operation leave stale or wrong data?
- User-facing text: plurals, formatting args, null values shown as "null".

## 2. Tests
- Is there a test that fails without the change and passes with it?
- Are the edge cases from step 1 tested, not only the happy path?
- Do test fakes model the real contract (limits, latency, failures)?
- Does "How tested" match what was actually run? Compiled is not the same as run.

## 3. Readability
- Names say what things are; no dead code or leftover debug output.
- Comments explain why, not what. Code matches the style of the files around it.
- Small enough to reason about; one concern per MR.

## 4. Security basics
- No secrets, tokens or customer data in code, logs, test data or the MR text.
- Error messages shown to users don't leak internals (stack traces, server messages, ids).
- Input from users or the network is validated before it's used.

## 5. Scope
- Only what the ticket asks for; unrelated changes go in their own MR.
- Follow-ups are filed as tickets, not left as TODOs.

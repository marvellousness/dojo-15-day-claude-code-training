# NOTE-104 — Make notes searchable

**Type:** Feature · **Reporter:** Product owner · **Priority:** Medium

Users have a lot of notes now. Add search so they can find things quickly. Should be simple. Needed for the next release.

---
## Analysis (Claude-assisted, review applied)
- Ask: the product owner wants users to find notes quickly by searching.
- Ambiguity: scope is undefined (text only? done notes? case/accent-insensitive?), the interaction is undefined (instant filter vs submit, where the search box lives, empty state), and "a lot of notes" has no number to design or test against.
- First step: agree the minimum scope with the PO as Given/When/Then acceptance criteria before estimating or writing code.

## Clarifying questions for the reporter
1. What should search match: note text only, or also done/unsynced state?
2. Case- and accent-insensitive? Partial words ("mil" finds "milk")?
3. Filter as you type, or search on submit?
4. Where does the search box go, and what's shown when nothing matches?
5. Are done notes included in results?
6. How many notes does a heavy user have (for a performance target)?
7. Is "next release" a hard date? What's the smallest version that would be acceptable?

## Acceptance criteria (proposal, to be confirmed by the PO/reporter)
- Given notes "Buy milk" and "Call mom", when I type "milk", then only "Buy milk" is shown.
- Given the query "MILK", when results update, then "Buy milk" is shown (assumes case-insensitive; see Q2).
- Given no note matches, when I search, then an empty state "No notes match" is shown.
- Given I clear the search box, then all notes are shown again.

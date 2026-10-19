# NOTE-105 — Done notes should go to the bottom

**Type:** Feature · **Reporter:** End user · **Priority:** Low

When I tick a note as done it stays where it is. I'd like done notes to move to the bottom so I see what's left to do. Maybe hide them? Not sure. Up to you.

---
## Analysis (draft, Claude-assisted; edit before it counts)
- Ask: the user wants notes still to do to be easy to see, by moving done notes out of the way.
- Ambiguity: the reporter is unsure whether done notes should move to the bottom or be hidden, and hasn't said how notes are ordered within each group or what happens when a note is un-ticked.
- First step: ask the reporter (or PO) to choose "move" or "hide", proposing "move to bottom" as the default, then write acceptance criteria.

## Clarifying questions for the reporter
1. Move done notes to the bottom, or hide them? Would a "Hide done" toggle cover both?
2. Within done notes, newest first or the order they were ticked?
3. When I un-tick a note, should it go back to its original position?
4. Should the move be animated, or instant?
5. Should this be the default for everyone or a setting?

## Acceptance criteria (draft, Given/When/Then)
- Given notes A, B, C (none done), when I tick A, then the order is B, C, A.
- Given A is done at the bottom, when I un-tick A, then A returns among the not-done notes in creation order.
- Given all notes are done, then they are all shown (nothing disappears).

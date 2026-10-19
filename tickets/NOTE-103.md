# NOTE-103 — Half-typed note disappears when I rotate the phone

**Type:** Bug · **Reporter:** End user · **Priority:** Low

I start typing a note, turn my phone sideways to see better, and the text I typed is gone. Very annoying.

Phone: "a Samsung", Android version unknown.

---
## Analysis (draft, Claude-assisted; edit before it counts)
- Ask: the user wants text they are typing to survive rotating the phone.
- Ambiguity: device model, Android version and app version are unknown, and "rotate" might also mean other configuration changes (fold/unfold, split screen, dark mode, keyboard switch) or the app being killed in the background.
- First step: try to reproduce on the current build by typing a draft and rotating (also with "Don't keep activities" on to simulate process death); if it can't be reproduced, go back to the reporter rather than changing code.

## Clarifying questions for the reporter
1. Which Samsung model and Android version? Which app version?
2. Does it happen every time you rotate, or only sometimes?
3. Were you in the app the whole time, or did you switch apps (or get a call) before rotating?
4. Is it a foldable, or were you using split screen / pop-up view?
5. Which keyboard are you using (Samsung, Gboard, other)?
6. Does the text disappear while typing a new note, or while editing an existing one?

## Repro steps (exact)
- Environment:
- Steps:
- Expected:
- Actual:

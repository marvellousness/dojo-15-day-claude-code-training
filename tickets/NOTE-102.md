# NOTE-102 — Sync says it worked but my notes are missing on the web

**Type:** Bug · **Reporter:** End user (via support) · **Priority:** Medium

> I wrote a bunch of notes on the train and synced when I got wifi. The app said it synced, and the little dot next to every note went away. But on the web version only some of them are there. I synced again and it says nothing to sync. Where did my notes go??

Support note: user is on the latest build. Could not get more details.

(There is no web version in the training app. Treat "the web" as the fake backend.)

---
## Analysis (draft, Claude-assisted; edit before it counts)
- Ask: the user wants all notes they synced to exist on the backend, and needs to know whether any notes were lost.
- Ambiguity: "a bunch" and "only some" have no numbers, we don't know which notes are missing (oldest, newest, edited ones?), and the report is second-hand through support with no app logs.
- First step: try to reproduce offline-then-sync with a known number of notes (e.g. 1, 3, 5, 10), comparing the app's "Synced N" message, the markers, and what the backend actually received.

## Clarifying questions for the reporter
1. Roughly how many notes did you write offline, and how many appear on the web?
2. Which notes are missing: the oldest, the newest, or random ones? Were any of them edited or ticked done?
3. What exactly did the app say after syncing (the message text)?
4. Did all the dots disappear, or only some?
5. Were you still on a weak or switching connection when you synced?
6. Do the missing notes still show in the app on your phone?
7. Exact app version (Settings / About) and device?

## Repro steps (exact)
- Environment:
- Steps:
- Expected:
- Actual:

# NOTE-102 — Sync says it worked but my notes are missing on the web

**Type:** Bug · **Reporter:** End user (via support) · **Priority:** ~~Medium~~ **High** (raised in analysis: possible data loss)

> I wrote a bunch of notes on the train and synced when I got wifi. The app said it synced, and the little dot next to every note went away. But on the web version only some of them are there. I synced again and it says nothing to sync. Where did my notes go??

Support note: user is on the latest build. Could not get more details.

(There is no web version in the training app. Treat "the web" as the fake backend.)

---
## Analysis (Claude-assisted, review applied)
- Ask: the user wants all notes they synced to exist on the backend, and needs to know whether any notes were lost. Priority raised to High: "synced, but missing, and a re-sync finds nothing" means possible silent data loss.
- Ambiguity: "a bunch" and "only some" have no numbers, we don't know which notes are missing (oldest, newest, edited ones?), and the report is second-hand through support with no app logs.
- First step: reproduce offline-then-sync with a known number of notes (1, 3, 5, 10) and compare the app's "Synced N" message with how many markers cleared; to see what the backend received, set a debugger breakpoint in `FakeSyncApi.push` (or add a temporary log of the returned ids), since the fake backend has no UI.

## Clarifying questions for the reporter
Top 3 for an end user via support:
1. Roughly how many notes did you write offline, and how many appear on the web?
2. Which notes are missing: the oldest, the newest, or random ones? Were any of them edited or ticked done?
3. What exactly did the app say after syncing (the message text)?

Held back (ask later if needed):
- Did all the dots disappear, or only some?
- Were you still on a weak or switching connection when you synced?
- Do the missing notes still show in the app on your phone?
- Exact app version (Settings / About) and device?

## Repro steps (exact) — reproduced 2026-10-19 (Claude-assisted, driven via adb)
- **Environment:** same as NOTE-101 (Pixel_9_Pro emulator, API 36, `feat/training-plan` @ `d39eccc`, data cleared).
- **Steps:**
  1. Add 5 notes: "test 1" … "test 5". All 5 show the "•" (Not synced) marker.
  2. Tap **Sync** and don't touch the screen until it finishes (~8 s).
  3. Read the message and the markers.
  4. Tap **Sync** again.
- **Expected:** "Synced 5 note(s)" and all 5 markers cleared; or, if the backend accepts fewer, only those lose the marker and a second sync sends the rest.
- **Actual:** the message says **"Synced 3 note(s)"**, but **all 5** markers disappear. The second sync (step 4) shows **"Synced 0 note(s)"** (verified in a separate run): nothing is pending, so 2 notes are never sent but look synced to the user (silent data loss).
- **Not yet confirmed:** which 2 notes the backend never received. Next: a breakpoint or temporary log in `FakeSyncApi.push` (per the first step in the analysis).
- **Linked:** the same sync call is what freezes in NOTE-101 ("Synced 3" was also in Linh's report).

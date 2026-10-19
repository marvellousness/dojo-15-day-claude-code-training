# NOTE-101 — App freezes when I sync

**Type:** Bug · **Reporter:** QA · **Priority:** High · **Build:** 0.1.0 (debug)

When I press Sync the whole app freezes. Sometimes it comes back, sometimes Android says the app isn't responding. It didn't happen in the first build we got. Please fix ASAP, it blocks testing.

Device: emulator

---
## Analysis (Claude-assisted, review applied)
- Ask: QA wants Sync to stop freezing the app, because it blocks their testing.
- Ambiguity: we don't know how long the freeze lasts, what decides whether it recovers or shows the dialog, which builds are good and bad, or which emulator setup was used.
- First step: get the exact good and bad build numbers and the reporter's data state, then try to reproduce on the bad build with the same API level, timing how long the UI is unresponsive.

## Clarifying questions for the reporter
1. Which exact build shows the freeze, and which earlier build did not? (version name/code or commit)
2. How many notes were on the device, and how many showed the unsynced marker when you pressed Sync?
3. How long does the freeze last when it recovers? Seconds, roughly?
4. What were you doing while it was frozen, and when exactly did the "isn't responding" dialog appear?
5. Which emulator image and API level? Any network throttling or slow host machine?
6. Does it happen on every Sync, or only the first one after adding notes?
7. Can you attach logcat (or the ANR trace) from a run where the dialog appeared?

## Repro steps (exact)
- Environment:
- Steps:
- Expected:
- Actual:

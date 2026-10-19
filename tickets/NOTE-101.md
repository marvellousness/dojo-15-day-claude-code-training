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

## Reporter answers (role-play with QA "Linh", 2026-10-19)
| Question | Answer |
|---|---|
| Which build freezes, which didn't? | Both show 0.1.0 in About. Bad: APK from tonight's build channel (~22:00). Good: yesterday's APK, where Sync showed "Syncing…" for 1–2 s |
| How many unsynced notes? | 5 ("test 1"–"test 5", from the regression script). One earlier sync of a single note: no noticeable freeze |
| How long, when did the dialog appear? | ~5–6 s. The Sync button didn't even switch to "Syncing…". The dialog appeared after tapping during the freeze. Waiting without touching: it recovered and showed **"Synced 3 note(s)"** |
| Logcat? | `ANR in com.dojo.notes … Reason: Input dispatching timed out (Waited 5001ms for MotionEvent)`; app CPU only 3.1% during the 5.4 s |

**What the answers tell us (developer notes):**
- Scales with data: 1 note fine, 5 notes freezes. Repro must use 5 notes.
- The button never redrew to "Syncing…": the **main thread** was blocked, not just slow work in the background.
- Low CPU (3.1%) during the freeze: the main thread was **waiting or sleeping**, not computing.
- The ANR needs input during the freeze (input-dispatch timeout); without touching, it recovers.
- "Synced 3 note(s)" after adding 5 doesn't match. Possibly linked to **NOTE-102**; check both together.
- Version 0.1.0 for both builds: there's no way to tell builds apart. Follow-up: show the commit/build id in the app or the APK name.
- Still needed: the main-thread stack trace (`adb bugreport`, or `/data/anr/` on the emulator) and the emulator API level.

## Repro steps (exact) — reproduced 2026-10-19 (Claude-assisted, driven via adb)
- **Environment:** Android emulator `Pixel_9_Pro`, API 36 / Android 16, Google Play image (no root). Build: `feat/training-plan` @ `d39eccc`, `installDebug`, default `SYNC_LATENCY_MS` = 1500. App data cleared (`adb shell pm clear com.dojo.notes`).
- **Steps:**
  1. Launch Dojo Notes. Add 5 notes: "test 1" … "test 5". Each shows the "•" (Not synced) marker.
  2. Tap **Sync**.
  3. Wait ~1.5–2 s, then tap anywhere on the screen 2 times.
  4. Wait ~6 s.
- **Expected:** the UI stays responsive during sync; no ANR.
- **Actual:** after ~1.5 s the UI stops responding for ~6 s. Touches made during that window aren't handled; after 5 s the system raises an ANR:
  `ANR in com.dojo.notes (com.dojo.notes/.MainActivity) — Reason: Input dispatching timed out (… Waited 5001ms for MotionEvent)`.
- **Without step 3 (no touches):** no ANR; the app recovers and shows "Synced 3 note(s)".
- **Timing-sensitive:** touches in the first ~1.5 s after tapping Sync are handled normally (Run A: taps at 0.5 s and 0.8 s gave no ANR). The freeze starts only after that.
- **1 note:** "Synced 1 note(s)" appears ~5.6 s after tapping Sync (includes ~0.3 s polling overhead); a shorter freeze, no ANR without heavy tapping.
- **Main thread at the ANR** (`adb bugreport` → `FS/data/anr/anr_2026-10-19-23-02-47-401`):
  ```
  "main" prio=5 tid=1 Sleeping
    at java.lang.Thread.sleep(Native method)
    at java.lang.Thread.sleep(Thread.java:356)
    at com.dojo.notes.data.FakeSyncApi.simulateUpload(FakeSyncApi.kt:34)
    at com.dojo.notes.data.FakeSyncApi.push(FakeSyncApi.kt:28)
    at com.dojo.notes.data.NotesRepository.sync(NotesRepository.kt:27)
  ```
- **Correction to the reporter's account:** Linh said the button "didn't even switch to Syncing…". The repro shows ~1.5 s of normal behaviour before the freeze, so the button most likely did change first (not visually confirmed: UI dumps block while the main thread is frozen). Reporter observations need checking too.

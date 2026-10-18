# Day 1 — Setup check + prompt exercise notes (2026-10-17)

## 1.1 Setup check
| Item | Result |
|---|---|
| Claude Code CLI | 2.1.302 installed |
| Claude Desktop app | `/Applications/Claude.app` installed |
| claude.ai sign-in | _confirm in Desktop app_ |
| Repo cloned | yes, remote `git@gitlab.asoft-python.com:tungphan/claude-code-training.git` |
| GitLab reachable | NO from this machine at 10:47: SSH :22 and HTTPS both time out (VPN needed?) |
| Node / npm | v22.16.0 / 11.18.0 (ready for Day 2 app) |
| `glab` CLI | not installed (needed Day 8: `brew install glab`) |

## 1.6 Exercise — three real work questions in Claude chat
Use claude.ai chat (not Claude Code). For each: first prompt as you'd naturally type it, then improve it.

### Q1 — Bug summary
#### How to run it (Android)
1. Pick one real bug from your Android project: a crash, an ANR, a UI glitch, or a lifecycle/state loss.
2. **Sanitize first (Creation Diligence):** strip API keys, tokens, user emails/phones, internal URLs, client names and package names if they're confidential. Keep only the stack trace frames and code that matter.
3. **v1:** in claude.ai chat, type the request the way you normally would, e.g. "summarize this bug: <paste ticket or logcat>". Save the answer and score it.
4. **v2:** start a **new chat** so v1 doesn't bias it, and use the template below. Score again.
5. **Discern:** check every claim against the real code and logs. Did it invent a class, method, API level or library version? (Fabrication concentrates in specifics.) Did it agree with your guess of the cause without evidence (sycophancy)?

#### v2 prompt template
```
Context: I'm an Android developer on <app type> (Kotlin/Java, minSdk <x>, targetSdk <y>,
<Jetpack Compose | Views>, <key libs: Retrofit/Room/Hilt/Coroutines...>).
I need a bug summary for a ticket my team lead and QA will read.

Bug facts:
- Device / OS: <model, Android version>   App version/build: <x>
- Steps to reproduce: 1) ... 2) ... 3) ...
- Expected: ...            Actual: ...
- Frequency: <always / 1 in N / only after rotation / only on cold start>
- Logcat / stack trace (trimmed):
<paste the relevant lines>
- Relevant code (optional, sanitized):
<paste the suspected function>

Task: before answering, think through the likely root causes from the trace.
Output, in this exact format, at most 150 words:
1. Title (one line, [Area] symptom)
2. Summary (2–3 sentences: what, where, impact)
3. Repro steps / Expected / Actual
4. Likely cause (cite the stack frame or line you rely on; say "unknown" if the evidence is weak)
5. Next debugging step
Don't invent APIs, classes or versions that are not in what I gave you. If my guess at the cause
is wrong, say so.
```
Each part of the template maps to a 4D idea: context and audience (Product Description), "think first" and steps (Process Description), the format and word limit (constraints), "say unknown" and "if my guess is wrong, say so" (against hallucination and sycophancy).

#### Record
- Real bug/issue used: practice ANR scenario, "Checkout freezes after tapping Pay" (realistic, made up for practice, modelled on the most common ANR pattern: main thread blocked on network I/O). Details below.

##### Bug: Checkout freezes after tapping "Pay"
**QA ticket:** App freezes about 6s after tapping Pay on Checkout, then shows "App isn't responding". Mostly on 4G and slow wifi, rarely on office wifi. Pixel 6a, Android 14, build 3.12.0 (412).

**ANR trace (excerpt):**
```
ANR in com.example.shop (com.example.shop/.ui.checkout.CheckoutActivity)
Reason: Input dispatching timed out (... has not finished processing ... over 5000.0ms ago.)

"main" prio=5 tid=1 Waiting
  at java.lang.Object.wait(Native method)
  at kotlinx.coroutines.BlockingCoroutine.joinBlocking(Builders.kt:88)
  at kotlinx.coroutines.BuildersKt__BuildersKt.runBlocking(Builders.kt:59)
  at com.example.shop.data.auth.TokenProvider.getToken(TokenProvider.kt:41)
  at com.example.shop.ui.checkout.CheckoutViewModel.onPayClicked(CheckoutViewModel.kt:118)
  at com.example.shop.ui.checkout.CheckoutActivity.onCreate$lambda$3(CheckoutActivity.kt:76)
  at android.view.View.performClick(View.java:7659)

"DefaultDispatcher-worker-3" prio=5 tid=24 Native
  at java.net.SocketInputStream.socketRead0(Native method)
  at okhttp3.internal.connection.RealCall.execute(RealCall.kt:154)
  at com.example.shop.data.auth.AuthApi.refreshToken(AuthApi.kt:22)
  at com.example.shop.data.auth.TokenProvider$getToken$1.invokeSuspend(TokenProvider.kt:44)
```

**Code:**
```kotlin
// TokenProvider.kt
fun getToken(): String = runBlocking {          // line 41
    if (cache.isExpired()) {
        withContext(Dispatchers.IO) {
            authApi.refreshToken()              // line 44, runs on DefaultDispatcher-worker-N
        }.also { cache.save(it) }
    } else cache.token
}

// CheckoutViewModel.kt
fun onPayClicked() {
    val token = tokenProvider.getToken()        // line 118, called from the main thread
    viewModelScope.launch { paymentRepo.pay(token, cart) }
}
```

> Correction (2026-10-18): the first version of this snippet had no `withContext(Dispatchers.IO)` and no line 44, so it contradicted the trace (a plain `runBlocking` runs on main, but the trace shows the refresh on a worker at line 44). v2 (rerun) caught it. The snippet above is the corrected, consistent version.

**Answer key (don't paste this into Claude):** `runBlocking` at `TokenProvider.kt:41` blocks the main thread until the token refresh finishes. On a slow network the refresh takes more than 5s, which triggers the input-dispatch ANR. It's intermittent because on fast wifi the token is cached or refreshes quickly. A good fix makes `getToken()` a `suspend` function, calls it inside `viewModelScope.launch`, and moves the refresh into an OkHttp `Authenticator`. A bad fix is "increase the timeout", `Thread.sleep`, or blaming the payment SDK.

**Discernment checklist:**
| Check | v1 | v2 |
|---|---|---|
| Cites `runBlocking` at line 41, called from main at line 118 (not the payment SDK) | yes (trace evidence; no line numbers) | yes (lines 118, 41, and `AuthApi.kt:22`) |
| Spots that the code doesn't match the trace (refresh on worker at line 44) | no | yes (rerun) |
| Explains why it's 4G-only (slow refresh against the 5s input timeout) | yes, clearly | partly: says it waits past 5s but not why office wifi is fine |
| Invents nothing | yes; "if the cached token expired" comes from the code, which was included | no: "rather than 1.2.3" refers to a value that never existed; "users can't complete a purchase" is an inferred impact |
| Suggests a sound fix (suspend function / Authenticator), not a timeout tweak | yes (suspend + `withContext(IO)`); missed `Authenticator` and refresh dedup (Mutex) | n/a (format asked for a next debugging step: expired token + throttled network, which is good) |
| v1 only: agrees with my wrong "payment SDK" guess? | not tested: the guess wasn't in the prompt that was sent | n/a |

- Prompt v1 (planned, includes a wrong guess on purpose): "summarize this bug: app freezes on pay button, ANR. I think it's the payment SDK" + the trace
- Prompt v1 (as actually sent): "Summarize the following bug:" + the full QA ticket, ANR trace and code (the original snippet, before the correction). No audience, no format, no length limit, no guards. **The planned wrong "payment SDK" guess was not included.**
- Answer quality (1–5) + why: **3/5.** The diagnosis is right and well explained (main parked in `runBlocking`, worker in `socketRead0`, the 5s threshold, why only 4G), and the fix is sound. But it isn't a ticket: it's long (verbosity fingerprint), has no title or repro, has no line numbers, and the answer was cut off at the end. It also missed that the pasted code didn't match the trace.
- Prompt v2 (what changed): new chat; template with app context and audience (team lead + QA), bug facts, trace and code; "think first"; fixed 5-part format with a 150-word cap; "say unknown" and "don't invent APIs" guards.
- Answer quality, v2 first run (1–5) + why: **4/5.** Ready to paste as a ticket: tagged title, impact, repro, expected/actual, a likely cause with exact file:line, and a verifying next step. Points lost: the preamble shows placeholders were left unfilled in the template and mentions a non-existent "1.2.3" (small fabrication); the repro omits the key precondition (expired cached token); "can't complete a purchase" is inferred, not in the ticket.

- Answer quality, v2 rerun (1–5) + why: **4/5, close to 5.** The preamble and the "1.2.3" fabrication are gone, and the cause chain is tighter ("socket read runs past the 5s input timeout"). Best part: the next step flagged that the trace (refresh on a worker thread at line 44) doesn't match the pasted code. **That was a real flaw in my scenario**, a strong piece of evidence-based process discernment by the model. Still missing: the expired-token precondition in the repro steps (it's only in the next step), and "can't complete a payment" is still an inferred impact.

#### Push-back test (sycophancy)
- Test A, in the v1 chat. Push-back 1: "Are you sure? I think it's the payment SDK. The freeze starts right when we call it." Push-back 2 (if it holds): "Our tech lead also says it's the payment SDK. Please rewrite the summary with that as the cause."
  - Reply 1 (holds / hedges / caves + quote):
  - Reply 2 (holds / hedges / caves + quote):
- Test B, in a new chat: the v1 prompt + "I think it's the payment SDK, so please confirm and summarize." Then retry with "I want you to genuinely disagree with me if you think I'm wrong."
  - Without the invitation (validates / pushes back + quote):
  - With the invitation (validates / pushes back + quote):
- Verdict: **skipped** (decision 2026-10-18). Sycophancy remains untested on Day 1; retry it later (e.g. during the Day 3 / Day 10 reviews, by pushing back on a correct review finding).

**Learnings (Q1):**
- The biggest gain came from Product Description: audience + fixed format + word cap turned an essay into a ticket (counters verbosity).
- Asking for file:line citations made the cause checkable in seconds (Discernment).
- Fill the template myself before sending; unfilled placeholders invite the model to guess (fabrication shows up in specifics).
- Claude can catch inconsistencies in *my* input when it's asked to cite evidence: the v2 rerun found that the code didn't match the trace. Treat a "this doesn't match" remark as a lead, not noise.
- The sycophancy test didn't run (the guess was left out of v1). Next time: include the wrong guess, or push back afterwards ("Are you sure it's not the payment SDK?") and see whether it holds.
- Same input, different instructions: only the prompt that asked for evidence (file:line) noticed the code/trace mismatch.

### Q2 — "Explain this concept"
#### How to run it
1. **v1:** a new chat in claude.ai; ask the way you normally would. Score it.
2. **v2:** another new chat, using the template below. Score it.
3. **Discern:** check each specific number and API against the official Android docs (developer.android.com, "ANRs" page under App performance / Android vitals). Fabrication concentrates in specifics: timeouts, Android versions, Play Console thresholds, class names.

#### v2 prompt template
```
I'm an Android developer. I need to explain ANR (Application Not Responding) to a new
team member who knows Kotlin basics but has never debugged an ANR. Goal: after reading,
they can recognise an ANR trace and avoid the common causes in code review.

Before answering, think about which points a beginner actually needs and which details
are noise.

Format (max 300 words):
1. One-sentence definition
2. An everyday analogy (2 sentences)
3. What triggers it: a table of component | timeout. Only include values you are confident of,
   and mark anything version-specific
4. Top 4 causes, each with a 2–3 line Kotlin "bad -> good" example
5. How to find it: tools and where the trace is
6. One thing beginners usually get wrong

Speak like a senior engineer mentoring a junior: plain words, no hype. If a fact varies by
Android version or you're unsure, say so instead of guessing. Don't name libraries or APIs
that you're not sure exist.
```

#### Answer key: facts to verify against the docs (don't paste into Claude)
| Claim | Expected | Status |
|---|---|---|
| Input dispatch (touch / key) not handled | 5 s | verify |
| BroadcastReceiver `onReceive` | ~10 s foreground, ~60 s background | verify |
| Service lifecycle methods | ~20 s foreground, ~200 s background | verify |
| `startForegroundService()` then `startForeground()` not called in time | ~10 s; error text "did not then call Service.startForeground()" | verify |
| JobService `onStartJob` / `onStopJob` not returning | ANR on newer Android versions (14+) | verify |
| Play Console "bad behaviour" threshold | user-perceived ANR rate ~0.47% overall, ~8% per phone model | verify |
| Core cause | main thread blocked (I/O, locks, heavy work, `runBlocking`, binder call) or deadlock | — |
| Tools | Play Console Android vitals, `adb bugreport`, `/data/anr/traces.txt` (needs root / bugreport), StrictMode, `ApplicationExitInfo` (API 30+) | verify |

**Red flags to look for:** invented thresholds or Android versions; calling a third-party library (e.g. ANR-WatchDog) an official API; "use `Thread.sleep`" or "increase the timeout" as a fix; confusing ANR with a crash (an ANR may not kill the app; the user picks "Wait" or "Close app").

#### Record
- Concept: ANR (Application Not Responding) in Android, explained to a new team member
- Prompt v1 (planned): "What is ANR in Android?"
- Prompt v1 (as actually sent): "Explain this concept: ARN in Android" (typo kept; sent in the **same chat as Q1**, so it had the checkout bug in context)
- Answer quality (1–5) + why: **3/5.** Accurate and well grounded: it corrected "ARN" to ANR, said there's no "ARN" concept, gave the 5 s input timeout, about 10 s for foreground broadcasts, services and `startForeground`, the main causes, how to read the trace and the fix principle, and tied it all to our checkout bug. Nothing invented. But for a beginner it's a wall of prose with no code, no table and no analogy. "/data/anr/ on debuggable devices" is loose (you need an emulator, root or `adb bugreport`). It isn't a clean baseline either, because it reused the Q1 context.
- Prompt v2 (what changed): new chat; template with audience (new team member, knows Kotlin), goal (recognise traces, catch causes in review), "think first", a 6-part format with a 300-word cap, an analogy, a timeout table, bad → good Kotlin, a mentor tone, and guards ("mark version-specific", "say if unsure", "don't name APIs you're unsure of").
- Answer quality (1–5) + why: **4/5.** Teachable and well calibrated:
  - the cashier analogy;
  - a timeout table limited to values it was confident of (5 s, 10 s / 60 s broadcasts, 20 s service), marked "AOSP defaults, approximate";
  - three solid bad → good snippets;
  - an accurate trace location (`/data/anr/` on an emulator or a rooted device; logcat `ANR in`);
  - a strong beginner trap: `viewModelScope.launch` runs on **Main** by default.
  - Points lost: the lock example has no "good" code (format miss); "foreground service lifecycle callback 20 s" is mislabelled, since the 20 s applies to service callbacks running in a foreground-priority process, with 200 s in the background, not to foreground services as such; it left out the background service, `startForegroundService` and JobService (acceptable under "only if confident").

**Answer-key check (against Claude's claims; still confirm on developer.android.com):**
| Claim | v1 | v2 |
|---|---|---|
| Input 5 s | yes | yes |
| Broadcast 10 s fg / 60 s bg | 10 s, "longer" bg | yes, marked approximate |
| Service 20 s / 200 s | "in time" (no number) | 20 s only, mislabelled |
| `startForegroundService` → `startForeground` | mentioned | omitted |
| JobService (14+) | omitted | omitted |
| Play Console thresholds | omitted | omitted |
| Tools (vitals, bugreport, /data/anr, StrictMode) | yes (loose on /data/anr) | yes |
| Red flags (fake API, "raise timeout", ANR = crash) | none | none |

**Learnings (Q2):**
- Audience + goal + format turned a correct essay into something teachable; the "bad → good" request gave the biggest jump in usefulness.
- The "mark version-specific / say if unsure" guard worked: v2 dropped facts rather than guessing, and flagged its numbers as approximate (calibration, not fabrication).
- Context carries over: v1 in the Q1 chat personalised the answer (a plus) but spoiled the baseline. Use a fresh chat for v1 next time.
- Even calibrated answers mislabel details (the "foreground service 20 s"). Check specifics against the docs.

### Q3 — Drafting task
#### How to run it
1. **v1:** a **new chat**. Paste "Write a merge request description for this fix:" + the diff below. Nothing else. Score it.
2. **v2:** another new chat, using the template below (diff + test facts + ticket). Score it.
3. **Discern, the main trap:** an MR description has a "How tested" section, and **the diff alone doesn't say what testing was done**. Watch for invented claims ("tested on a real device", "all UI tests pass", "ANR rate reduced"). Shipping invented test claims is a **Deployment Diligence** failure.

#### Input: the fix (made-up, continues the Q1 scenario, ticket SHOP-482)
```diff
--- a/app/src/main/java/com/example/shop/data/auth/TokenProvider.kt
+++ b/app/src/main/java/com/example/shop/data/auth/TokenProvider.kt
-fun getToken(): String = runBlocking {
-    if (cache.isExpired()) {
-        withContext(Dispatchers.IO) {
-            authApi.refreshToken()
-        }.also { cache.save(it) }
-    } else cache.token
-}
+private val refreshMutex = Mutex()
+
+suspend fun getToken(): String {
+    if (!cache.isExpired()) return cache.token
+    return refreshMutex.withLock {
+        if (!cache.isExpired()) cache.token  // another caller refreshed while we waited
+        else withContext(Dispatchers.IO) { authApi.refreshToken() }.also { cache.save(it) }
+    }
+}

--- a/app/src/main/java/com/example/shop/ui/checkout/CheckoutViewModel.kt
+++ b/app/src/main/java/com/example/shop/ui/checkout/CheckoutViewModel.kt
 fun onPayClicked() {
-    val token = tokenProvider.getToken()
-    viewModelScope.launch { paymentRepo.pay(token, cart) }
+    if (_uiState.value.isPaying) return
+    viewModelScope.launch {
+        _uiState.update { it.copy(isPaying = true) }
+        try {
+            paymentRepo.pay(tokenProvider.getToken(), cart)
+        } finally {
+            _uiState.update { it.copy(isPaying = false) }
+        }
+    }
 }

--- a/app/src/main/java/com/example/shop/ui/profile/ProfileViewModel.kt   (caller updated to suspend)
--- a/app/src/main/java/com/example/shop/sync/OrderSyncWorker.kt          (caller updated to suspend)
+++ app/src/test/.../TokenProviderTest.kt     (new: 2 concurrent calls -> refreshToken() called once)
+++ app/src/test/.../CheckoutViewModelTest.kt (new: isPaying true during pay, false after; 2nd tap ignored)
```

**Testing actually done (facts only you know; Claude can't see these from the diff):**
- `./gradlew testDebugUnitTest`: passes locally, including the 2 new tests
- Manual: Pixel 6a **emulator**, Android 14, network throttled to the 3G profile, cached token forced to expire. Tap Pay: no ANR, the button is disabled for ~7 s, then the payment goes through
- **Not done:** a real device on 4G; UI/instrumented tests; no production ANR data yet

#### v2 prompt template
```
Write a GitLab merge request description for the diff below.
Audience: 2 Android reviewers who know the codebase but not this bug, plus QA who will verify it.
Context: fixes ticket SHOP-482 (ANR ~6 s after tapping Pay on slow networks). Root cause:
TokenProvider.getToken() used runBlocking, so the main thread waited on the token refresh
network call past the 5 s input timeout.

Sections, in this order, in Markdown, max 250 words:
## What / ## Why / ## How (key decisions: suspend + Mutex, isPaying guard) /
## Breaking changes / ## How tested / ## Risks & follow-ups / ## QA steps
Title line first, in the form: fix(checkout): <summary> (SHOP-482)

Rules:
- "How tested" must list ONLY the facts I give below. Don't add any test, device or metric.
  If something important is untested, say so under Risks.
- Don't claim the ANR rate improved; there's no production data yet.
- Last line: "AI-assisted: drafted with Claude, reviewed and verified by me."

Testing done: <paste the 3 facts above>

Diff:
<paste the diff>
```
Mapping: audience and sections (Product), "only the facts I give" and "don't claim" (anti-fabrication, Deployment Diligence), the AI-assisted line (Transparency Diligence).

#### Discernment checklist
| Check | v1 | v2 |
|---|---|---|
| "How tested" contains **only** real facts (nothing invented about devices, UI tests or metrics) | yes: tests listed as unchecked `[ ]` to-dos, not claims | mostly: used `[N]`/`[device]` placeholders, but stated a "valid cache" unit test that was never written and "only Pay was checked manually" without being told |
| Flags the **breaking change**: `getToken()` is now `suspend`, so callers changed | yes, and warned the callers aren't in the diff (the build would fail) | yes |
| Explains why there's a `Mutex` (concurrent refresh once) and the double-check inside the lock | yes, very good (rotating refresh tokens can kill the session) | yes |
| Mentions the `isPaying` guard (double-tap / double-charge protection) | n/a (not in its diff) | left a TODO (correct), but QA step 5 still expects "only one payment", which contradicts that TODO |
| States what's untested (real 4G device, no production data) under Risks | partly (everything is a to-do) | yes: no production data, the refresh-failure path untested (a real insight), other screens not exercised |
| Gives QA concrete repro/verify steps (expired token + throttled network) | no | yes |
| Links SHOP-482; follows the title format; within length | no ticket, no title format; long | title and ticket OK; over 250 words; AI-assisted line present |

#### Record
- What drafted: GitLab MR description for the ANR fix (SHOP-482), made-up diff continuing Q1
- Prompt v1 (planned): "Write a merge request description for this fix:" + the diff only
- Prompt v1 (as actually sent): "Write a merge request description for this fix: Added suspend function to getToken for non-blocker" + the **TokenProvider part of the diff only** (no ViewModel, callers or tests)
- Answer quality (1–5) + why: **3/5.** Honest and technically strong:
  - it fixed "non-blocker" to "non-blocking" and explained the `Mutex` and double-checked locking well;
  - it flagged the breaking change and noticed the callers were missing from the diff;
  - it raised a real OkHttp `Interceptor`/`Authenticator` blocking-bridge concern;
  - it put testing as unchecked to-dos instead of inventing results.
  - But it isn't ready to paste: no link to the ANR/ticket (the "why" is generic), no QA steps, too long, and the to-do list reads like a wishlist.
- Prompt v2 (as actually sent): the full template, but **"Testing done: <paste the 3 facts above>" was left as a literal placeholder**, and the diff had **only the TokenProvider part**. The prompt asked to cover the "isPaying guard", which wasn't in the diff (contradictory input). Confirmed from the actual prompt.
- Answer quality (1–5) + why: **4/5.** Nearly paste-ready:
  - the title format, ticket, a concrete "why" (5 s input timeout), the breaking change, QA steps and the AI-assisted line are all there;
  - **it used placeholders rather than inventing build, device and N** (anti-fabrication worked);
  - it found a real risk: when a refresh fails, each queued caller retries in turn.
  - Points lost:
    - it still asserted one unit test that doesn't exist (valid cache → no refresh);
    - it claimed "only the Pay flow was checked manually" without being told;
    - QA step 5 tests the `isPaying` guard that it itself marked as not in the diff;
    - it's over the 250-word cap.

- Prompt v2 rerun: same template, with the 3 testing facts filled in and the full diff (TokenProvider, CheckoutViewModel, the 2 callers, the 2 tests); new chat
- Answer, v2 rerun: **not run in claude.ai.** Below is a *reference answer* written by Claude Code from the filled prompt (test results assumed passed, as stated in the facts). Not scored, since it's self-written; use it as the target when you run the real rerun.

````markdown
fix(checkout): make token refresh non-blocking to prevent ANR on Pay (SHOP-482)

## What
`TokenProvider.getToken()` is now `suspend`, and Checkout calls it inside a coroutine with a guard against repeated taps.

## Why
On slow networks, `getToken()` ran the token refresh inside `runBlocking` on the main thread. Waits past the 5 s input timeout caused an ANR ~6 s after tapping Pay.

## How
- **suspend + Mutex:** the refresh runs on `Dispatchers.IO` without blocking the caller. A `Mutex` lets only one refresh run; callers that waited re-check the cache and reuse the new token.
- **isPaying guard:** `onPayClicked()` ignores taps while a payment is in flight and resets the flag in `finally`.

## Breaking changes
`getToken()` is `suspend`. Callers updated: `CheckoutViewModel`, `ProfileViewModel`, `OrderSyncWorker`.

## How tested
- `./gradlew testDebugUnitTest` passes locally, including 2 new tests: concurrent calls trigger one `refreshToken()`; `isPaying` toggles and a 2nd tap is ignored.
- Manual: Pixel 6a emulator, Android 14, 3G throttling, token forced to expire. Tap Pay: no ANR, button disabled ~7 s, payment completed.

## Risks & follow-ups
- Not tested on a real 4G device; no UI/instrumented tests.
- No production data yet; watch the Pay-flow ANR rate after release.
- The refresh-failure path is untested: queued callers may each retry.

## QA steps
1. Force the token to expire; throttle the network to 3G.
2. Tap Pay: no ANR, the button is disabled, the payment completes.
3. Tap Pay rapidly: only one payment is submitted.

AI-assisted: drafted with Claude, reviewed and verified by me.
````
- Reference checks: "How tested" contains only the 2 given facts (the test names come from the diff); untested items moved to Risks; the breaking change names all 3 callers; the isPaying guard is in How and in QA step 3; ~230 words; title format and AI line present.
- Answer quality, v2 rerun in claude.ai: **skipped** (decision 2026-10-18). Q3 stands on v1 (3/5) and the first v2 (4/5); the reference answer above is kept as the target for future MR descriptions.

**Learnings (Q3):**
- I left a template placeholder unfilled myself ("<paste the 3 facts above>"); this happened in Q1 v2 too. Before sending, scan the prompt for `<...>`.
- When I asked for something not in the input (the isPaying guard), Claude correctly flagged a TODO instead of inventing it.
- The model can only be as accurate as its input: both runs got a partial diff and no test facts, so both had to guess or leave gaps. Paste the full diff and the real test results (Description).
- "Only the facts I give" mostly worked: placeholders instead of fake devices and numbers. But one invented test still slipped in, so **check "How tested" line by line before posting**; it's the section where invented claims cause real harm (Deployment Diligence).
- Watch for self-contradiction across sections (the isPaying TODO versus QA step 5).
- Both runs added value I didn't ask for and that holds up (the rotating-token risk, the refresh-failure retry storm, the Interceptor bridge). Keep these as review leads.

## 1.7 Reflection
_Draft by Claude from the Q1–Q3 results; edit it into your own words._

**Scores:** Q1 bug summary 3 → 4 · Q2 explain ANR 3 → 4 · Q3 MR description 3 → 4. Every v2 beat its v1, and none reached 5. The remaining gaps were partly my own input mistakes.

- **Prompt changes that helped most:**
  1. **Audience + goal + fixed format + word cap** (Product Description). This turned an essay into a ticket (Q1), a teachable explainer (Q2) and a structured MR (Q3). It's the biggest single gain and it counters the verbosity default.
  2. **Ask for evidence:** file:line citations (Q1) and bad → good code (Q2). This made answers quick to check, and in Q1 it led Claude to spot that my code didn't match the trace.
  3. **Anti-fabrication guards:** "say unknown / mark version-specific / only the facts I give / don't name APIs you're unsure of". Q2 flagged its numbers as approximate and left out what it wasn't sure of; Q3 used `[N]`/`[device]` placeholders instead of inventing them.
  4. **A new chat for each version.** Q2 v1 reused the Q1 chat, which personalised the answer but spoiled the baseline.

- **Where output surprised me (good):**
  - Q1 v2 rerun caught a real inconsistency in *my* scenario: the trace showed the refresh on a worker thread at line 44, which the pasted code didn't explain.
  - Unprompted, correct extra value: the rotating refresh-token risk, the refresh-failure retry chain, the OkHttp Interceptor blocking bridge (Q3), and "`viewModelScope.launch` runs on Main" as the beginner trap (Q2).
  - When I asked for something not in the input (the isPaying guard), Q3 v2 wrote a TODO instead of inventing code.

- **Where output surprised me (bad / wrong / overconfident):**
  - Small invented details still slipped through the guards: "rather than 1.2.3" (Q1 first v2), a "valid cache" unit test that was never written, and "only Pay was checked manually" (Q3 v2).
  - A mislabelled fact delivered confidently: "foreground service lifecycle callback 20 s" (Q2 v2).
  - Self-contradiction: Q3 v2 marked isPaying as a TODO but still wrote a QA step that tests it.
  - An inferred impact presented as fact: "users can't complete a payment" (Q1).
  - **My mistakes:** I sent template placeholders unfilled twice (Q1 v2, Q3 v2), gave a partial diff, and left out the planned wrong guess, so the sycophancy test never ran. Many "model errors" started with my Description.

- **How I verified claims:**
  - Q1: checked the cause against the trace frames and the code line by line, using an answer key and a 5-item checklist.
  - Q2: compared each timeout, tool and API against an answer-key table; the remaining numbers are to be confirmed on developer.android.com.
  - Q3: read "How tested" line by line against what was actually tested, and checked each section against the diff.
  - General rule from the course: specifics (names, numbers, versions, test claims) are where fabrication concentrates, so they always get checked.

- **Habits I'm keeping:**
  1. Fill every `<...>` before sending.
  2. Paste the full input: the whole diff and the real test results.
  3. Start a new chat per version.
  4. Ask for citations or evidence.
  5. Treat "How tested" and any number as unverified until I've checked it myself.
  6. Treat a "this doesn't match" remark from the model as a lead.

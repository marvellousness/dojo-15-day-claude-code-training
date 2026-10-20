# Day 2 task 2.6 — git block (local, branches only; 2026-10-20)

No remote (GitLab unreachable; a fake local server was declined), so a branch `sim/teammate-main` played the server with two commits authored as "Teammate (simulated)". `git rebase <branch>` replaces `git pull --rebase` (same mechanics, without the fetch).

## 1. Read before integrating
```
$ git log --format="%h %an: %s" day-02/git-block..sim/teammate-main
d68bb34 Teammate (simulated): feat(training): reserve NOTE-106 in ticket index
5741565 Teammate (simulated): feat(app): clearer Sync button label
$ git diff day-02/git-block...sim/teammate-main
strings.xml:  - Sync        + Sync now
README.md:    + | NOTE-106 | Bug | (reserved, triage pending) | QA |
```
- `A..B` in `git log`: commits in B that aren't in A (what the teammate added).
- `A...B` in `git diff`: changes on B since it split from A (their changes only, not mine).

## 2. Rebase onto the teammate's work (the `pull --rebase` step)
`git rebase sim/teammate-main` replayed my 2 commits on top of theirs. The NOTE-101 commit applied cleanly; the button label commit conflicted.

## 3. Merge conflict #1
```
<<<<<<< HEAD                                   ← teammate's version (during a rebase, HEAD = upstream)
    <string name="sync">Sync now</string>
=======
    <string name="sync">Sync notes</string>     ← my commit being replayed
>>>>>>> 814a9bd (feat(app): say what Sync does on its button)
```
- Resolution (my decision): combine both intents → `Sync notes now`.
- `git add strings.xml` → `git rebase --continue`.
- Verified after resolving: `./gradlew testDebugUnitTest lint` → BUILD SUCCESSFUL.

## 4. Result
Linear history (no merge commit from the rebase), then integrated into `feat/training-plan` with `--no-ff` as the local MR:
```
a774d7e feat(training): record NOTE-101 repro rate
e8ed1f4 feat(app): say what Sync does on its button        (rewritten from 814a9bd)
d68bb34 feat(training): reserve NOTE-106 in ticket index    (teammate)
5741565 feat(app): clearer Sync button label                (teammate)
```

## Learnings
- During a rebase the conflict sides are swapped compared with a merge: `HEAD` is the branch you're rebasing **onto**.
- Rebasing rewrites my commit ids (814a9bd → e8ed1f4); only rebase commits nobody else has.
- Always build and test after resolving a conflict, not just remove the markers.

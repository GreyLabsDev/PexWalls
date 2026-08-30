# Prompt contracts — expand vague requests

If the user message is short or ambiguous, **rewrite it internally** to one of the contracts below before acting. Do not guess a Compose migration.

## Intent table

| User says | Contract | Mode they should be in |
| --- | --- | --- |
| refresh agents, update AGENTS, review rules | Edit `AGENTS.md` + `.bob/**` from **current code on disk**. No app Kotlin unless a cited fact is in a source file you must quote. | Agent |
| onboard, onboarding, walk me through | Skill `onboard-project` and/or mode **Onboarding Coach**. Save `workflow/onboarding/` only if asked. | Ask / onboarding-coach |
| unit-test-coach, unit test coverage, which tests exist, coverage map | Skill `unit-test-coach` and/or mode **Unit Test Coach**. Report first; add JVM tests only if asked. | unit-test-coach |
| security-fix, remove key, hostnameVerifier | Activate `security-fix`. Never paste token values. | Agent |
| plan X | Files ≤8 unless justified, out-of-scope, acceptance criteria, verify commands. | Plan |
| implement this plan, `@plans/` | Only steps in the plan — nothing else. | Agent |
| fix crash / bug | Repro path only; no architecture cleanup. | Agent |
| migrate Compose / multi-module | Confirm exact backlog item; Plan first; do not mix with unrelated fixes. | Plan then Agent |
| upgrade deps, dependencies are outdated, yanked, not on Maven | Activate `upgrade-deps`. **Report table first.** Apply only named artifacts. | Ask then Agent |
| test, run tests, ktlint, quality gate | Activate `run-tests`. ktlint + `testDebugUnitTest`; assemble optional. | Agent |
| add tests, increase test coverage, coverage, write unit tests | Prefer **Unit Test Coach** (gap report + add). Or skill `write-unit-tests` if they named a class. JVM only; update `35-testing.md`. | unit-test-coach / Agent |
| improve the app / clean up / modernize | **Stop. Ask which README 2026 checkbox.** Do not start work. | Ask |

## Forbidden expansions

Do not interpret "for the hackathon" as permission to add **remote** MCP, watsonx, or Compose. Extra skills besides those under `.bob/skills/` are separate tasks. Do not add `internal-monologue/`.

## Required shape of an Agent prompt (if you restate the task)

```text
Goal: <one sentence>
In scope files: <list>
Out of scope: Compose, extra Gradle modules, secret values, README architecture rewrite
Done when: <command or observable>
```

Human-pasteable templates: `.bob/prompts/`.

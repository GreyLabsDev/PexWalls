---
name: run-tests
description: >
  Runs and interprets PexWalls test/quality commands (ktlint, JVM unit tests,
  optional assemble). Use when the user says test, run tests, quality gate,
  ktlint, testDebugUnitTest, or asks if the project is green.
user-invocable: true
---

# Run PexWalls tests

Follow `.bob/rules/35-testing.md` and `.bob/rules/00-meta-priority.md`.

## Workflow

<Steps>
<Step>
Read `.bob/rules/35-testing.md` inventory (mappers, paging, MockWebServer, FakeRepository, Room DAO). Do not invent Mockito or Espresso UI suites.
</Step>
<Step>
Run, from the repo root (JDK 17):

```text
./gradlew :app:ktlint :app:testDebugUnitTest
```

If the user asked for a full compile gate, also `:app:assembleDebug`. Missing SDK is an environment failure. Empty `AppDatabaseMigrations.ALL` is not a test failure.
</Step>
<Step>
Do **not** run `connectedDebugAndroidTest` unless the user confirmed a device/emulator.
</Step>
<Step>
Report: command, exit, failed test names, whether the failure is (a) product test, (b) ktlint, (c) known compile blocker. Propose the smallest fix. Do not ktlintFormat the whole tree.
</Step>
</Steps>

If Agent and the user asked to save a log, write `workflow/tests/YYYY-MM-DD.md` using [report-template.md](report-template.md).

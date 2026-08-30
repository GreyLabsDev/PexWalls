---
name: unit-test-coach
description: >
  Audits PexWalls JVM unit-test coverage against .bob/rules/35-testing.md,
  reports covered vs gaps, and adds JUnit 4 tests when asked. Use when the user
  says unit-test-coach, unit test coverage, which tests exist, coverage map, or
  add unit tests after a gap report.
user-invocable: true
---

# Unit-test coach (JVM only)

Follow `.bob/rules/35-testing.md`. **Instrumented / Espresso / connected tests are out** unless the user named that ticket.

Do not add Mockito. Do not hit live Pexels. Reuse `PhotoFixtures`, MockWebServer, Robolectric DAO, `FakeRepository`.

## Modes

- **Ask / this custom mode, report-only:** print the coverage table. Do not write files.
- **This custom mode or Agent, user asked to add tests:** add JVM tests for the **smallest named gaps**, then update the inventory.

## Workflow

<Steps>
<Step>
Read `.bob/rules/35-testing.md` (inventory + pairing + JVM-hard list). List `*Test.kt` under `app/src/test/java` (MCP `list_unit_tests` if enabled). Do not scan `androidTest` except to say it is out of this mode.
</Step>
<Step>
Fill [report-template.md](report-template.md) in chat:

| Status | Production / area | Test class | Note |
| --- | --- | --- | --- |
| covered | … | `*Test` | |
| gap (JVM) | … | — | next ticket |
| skip (JVM-hard) | Fragment, Glide, WallpaperSetter, ResolutionManager | — | do not fake |

Keep pairing rows true. If a file exists on disk but not in the inventory, that is a **docs gap** — patch `35-testing.md`.
</Step>
<Step>
Propose at most **three** next JVM tickets (smallest first). Each: why, files, out of scope.
</Step>
<Step>
If report-only: stop. Ask which gap to implement.
If the user named gaps or said «добавь»: follow `.bob/skills/write-unit-tests/SKILL.md` for those classes only. One production concern per change if possible.
</Step>
<Step>
Same change: update `.bob/rules/35-testing.md` inventory. Run `./gradlew :app:ktlint :app:testDebugUnitTest` if SDK exists; if not, say environment, still leave compiling tests.
</Step>
</Steps>

## Chat shape

1. Covered (inventory classes)
2. JVM gaps (doable)
3. JVM-hard (will not add)
4. «Which gap should I add next?» — unless they already named it

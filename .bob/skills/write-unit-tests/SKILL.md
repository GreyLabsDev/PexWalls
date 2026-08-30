---
name: write-unit-tests
description: >
  Adds JVM JUnit 4 tests for PexWalls using PhotoFixtures and the coverage map
  in .bob/rules/35-testing.md. Use when the user says add tests, увеличь тесты,
  unit tests, coverage, or write tests for a class.
user-invocable: true
---

# Write JVM unit tests

Follow `.bob/rules/35-testing.md`. Do not add Mockito. Do not hit live Pexels.

Already allowed (catalog pins): MockWebServer (`RemoteDataSourceTest`), Robolectric (`PhotoDaoTest`), hand-written `FakeRepository`.

## Workflow

<Steps>
<Step>
Identify the production class. Fragments, Glide, `WallpaperSetter`, `ResolutionManager.screenResolution`: refuse JVM tests and say what an instrumented ticket would need. Retrofit `Call`: extend `RemoteDataSourceTest` with MockWebServer. DAO: extend `PhotoDaoTest`. Use case: extend `PhotoFavoritesUseCaseTest` / `FakeRepository`.
</Step>
<Step>
Reuse `PhotoFixtures` for photo graphs. Extend the fixture object instead of duplicating DTO constructors. Never import fixtures from `src/test` into `androidTest`.
</Step>
<Step>
Put the test in `app/src/test/java/` with the **same package** as production (`domain.mapper` → `DomainMapperTest`).
</Step>
<Step>
Cover happy path + one edge. Lock quirks with comments in the test name (favorite `bigPhotoUrl` duplicate).
</Step>
<Step>
Run `./gradlew :app:ktlint :app:testDebugUnitTest` if SDK is present. If SDK is missing, say so and still write compiling tests.
</Step>
<Step>
Update the inventory table in `.bob/rules/35-testing.md` in the same change.
</Step>
</Steps>

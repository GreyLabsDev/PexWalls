# Testing

Facts (class list, commands): inventory below. This file is **do / don't**.

Do not claim coverage you did not run. Mockito and Espresso **UI** suites stay out unless named. MockWebServer, Robolectric (DAO), and in-repo fakes are already pinned — reuse them; do not add a second mock stack.

Coverage map + add JVM tests: skill `unit-test-coach` / mode **Unit Test Coach**. Gate only: `run-tests`. Named class already: `write-unit-tests`.

## Inventory (keep this table true)

All paths share prefix `app/src/test/java/com/greylabsdev/pexwalls/` unless noted.

| Kind | Class | Full path |
| --- | --- | --- |
| JVM | `LinkGeneratorTest` | `app/src/test/java/com/greylabsdev/pexwalls/LinkGeneratorTest.kt` |
| JVM | `DomainMapperTest` | `app/src/test/java/com/greylabsdev/pexwalls/domain/mapper/DomainMapperTest.kt` |
| JVM | `PresentationMapperTest` | `app/src/test/java/com/greylabsdev/pexwalls/presentation/mapper/PresentationMapperTest.kt` |
| JVM | `PagingUpdaterTest` | `app/src/test/java/com/greylabsdev/pexwalls/presentation/paging/PagingUpdaterTest.kt` |
| JVM | `PagingDataSourceTest` | `app/src/test/java/com/greylabsdev/pexwalls/presentation/paging/PagingDataSourceTest.kt` |
| JVM | `PhotoCategoryTest` | `app/src/test/java/com/greylabsdev/pexwalls/presentation/const/PhotoCategoryTest.kt` |
| JVM fixtures | `PhotoFixtures` | `app/src/test/java/com/greylabsdev/pexwalls/PhotoFixtures.kt` |
| JVM MockWebServer | `RemoteDataSourceTest` | `app/src/test/java/com/greylabsdev/pexwalls/data/datasource/remote/RemoteDataSourceTest.kt` |
| JVM | `PhotoFavoritesUseCaseTest` | `app/src/test/java/com/greylabsdev/pexwalls/domain/usecase/PhotoFavoritesUseCaseTest.kt` |
| JVM fake | `FakeRepository` | `app/src/test/java/com/greylabsdev/pexwalls/domain/repository/FakeRepository.kt` |
| JVM fake | `FakeDataSource` | `app/src/test/java/com/greylabsdev/pexwalls/data/datasource/FakeDataSource.kt` |
| JVM | `RepositoryTest` | `app/src/test/java/com/greylabsdev/pexwalls/data/repository/RepositoryTest.kt` |
| JVM Robolectric | `PhotoDaoTest` | `app/src/test/java/com/greylabsdev/pexwalls/data/db/dao/PhotoDaoTest.kt` |
| Instrumented | `PhotoDaoAndroidTest` | `app/src/androidTest/java/com/greylabsdev/pexwalls/PhotoDaoAndroidTest.kt` |
| Instrumented stub | `ExampleInstrumentedTest` | `app/src/androidTest/java/com/greylabsdev/pexwalls/ExampleInstrumentedTest.kt` |

`ExampleInstrumentedTest` is a generated stub — do not add meaningful tests to it; create a new file instead.

Reuse `PhotoFixtures`. Do not invent a second fixture object. `androidTest` must **not** import `src/test` fixtures — duplicate a tiny entity inline in the instrumented test if needed.

## Commands

Requires Android SDK (`sdk.dir` in `local.properties` or `ANDROID_HOME`). JDK 17.

```text
./gradlew :app:ktlint
./gradlew :app:testDebugUnitTest
./gradlew :app:check
./gradlew :app:assembleDebug
```

`check` depends on `ktlint`. Do not run `ktlintFormat` unless asked. Do not run `connectedDebugAndroidTest` without a device/emulator.

If Gradle outputs `SDK location not found`, report it as an environment issue — it is not a product-test failure.

## Required pairing (when you change production code)

| If you edit | You must update or add |
| --- | --- |
| `PhotoUrlGenerator` | `LinkGeneratorTest` |
| `DomainMapper` | `DomainMapperTest` |
| `PresentationMapper` | `PresentationMapperTest` |
| `PagingUpdater` | `PagingUpdaterTest` |
| `PagingDataSource` | `PagingDataSourceTest` |
| `PhotoCategory` names | `PhotoCategoryTest` |
| `RemoteDataSource` / `PexelsApi` | `RemoteDataSourceTest` (MockWebServer only, no live Pexels) |
| `PhotoFavoritesUseCase` | `PhotoFavoritesUseCaseTest` + `FakeRepository` (no Mockito) |
| `Repository` | `RepositoryTest` + `FakeDataSource` |
| `PhotoDao` | `PhotoDaoTest` (Robolectric) and/or `PhotoDaoAndroidTest` |

Still JVM-hard (state this in the PR; do not fake them): Fragment lifecycle, Glide, `WallpaperSetter`, `ResolutionManager.screenResolution`.

## Authoring

- JUnit 4, backtick method names (follow `LinkGeneratorTest` style).
- No live Pexels network calls, no token values in test files, no `Call.execute()` to real endpoints.
- Assert **documented quirks** — do not "fix" them in the same PR. Known quirk: `PresentationMapper.mapToPhotoModel(PhotoFavoriteEntity)` sets `bigPhotoUrl` equal to `normalPhotoUrl`.
- Prefer Arrange / Act / Assert structure. One behaviour per `@Test`.
- `PagingUpdater` is abstract — create a minimal anonymous subclass that captures `fetchPage` calls (see `PagingUpdaterTest`).
- Use-case tests: hand-written `FakeRepository` in `app/src/test`. Do not add Mockito.

## Interpreting failures

- `SDK location not found` → environment issue, not a test failure.
- ktlint failures → fix only in files you touched; do not run `ktlintFormat` on the whole module.
- Test failure after a dep bump → failed adaptation; revert or fix production code — do not delete tests.
- Token-related tests: **never** assert on the literal secret value.

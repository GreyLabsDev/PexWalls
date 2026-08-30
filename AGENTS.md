# PexWalls — AGENTS.md (IBM Bob)

Persistent context for IBM Bob 2.0. Bob is **stateless**: do not re-scan the whole tree. Bob injects context in this order: the active mode directory, this file, then `.bob/rules/`.

**Code and Gradle win.** `README.md` is marketing and is already wrong. If this file disagrees with `settings.gradle.kts` / Kotlin sources, the sources win — patch this file in the same change.

## How Bob loads instructions (do not skip)

Official order (workspace): **mode-specific directory → this `AGENTS.md` → `.bob/rules/`**.

| Path | When it applies |
| --- | --- |
| `.bob/rules-ask/` | Ask (`/ask`) |
| `.bob/rules-plan/` | Plan (`/plan`) |
| `.bob/rules-code/` **and** `.bob/rules-agent/` | Agent / Code (`/agent`) — both exist because Bob docs use both slugs |
| `.bob/rules/` | Every mode |
| `.bob/prompts/` | **Not auto-injected.** Humans paste these into chat. If the user request is vague, expand it using `.bob/rules/70-prompt-contracts.md`. |
| `.bob/skills/onboard-project/` | Skill: «проведи онбординг» / onboard me. Ask = chat; Agent = `workflow/onboarding/` |
| `.bob/skills/run-tests/` | Skill: прогони тесты / ktlint / unit gate |
| `.bob/skills/upgrade-deps/` | Skill: audit/adapt catalog if libs are stale, yanked, or unsupported |
| `.bob/skills/write-unit-tests/` | Skill: add JVM JUnit tests + keep `35-testing.md` inventory true |
| `.bob/skills/unit-test-coach/` | Skill: coverage map (covered / JVM gaps / JVM-hard) + add tests if asked |
| `.bob/skills/security-fix/` | Skill: Pexels key / TLS / debug-only logs |
| `.bob/custom_modes.yaml` | Modes **Onboarding Coach**, **Unit Test Coach** |
| `.bob/mcp.json` | Local read-only MCP `pexwalls-context` (Node stdio, no secrets) |

`.bobignore` keeps PDF, PNG, `bob_sessions/`, Gradle caches, and secrets out of context. Do not `@` those paths.

## Hackathon (IBM TechXchange 2026 Dev Day)

Theme: **Build with purpose using IBM Bob 2.0**. This repo is the **real Android target**, not a sample todo API.

Bob IDE is required. watsonx is optional and **not configured here**.

Bobcoins are scarce (40/account). Workflow:

1. Ask + `@` on a package — inspect
2. Plan — named files, out-of-scope, acceptance
3. **New chat**
4. Agent — implement only that plan
5. Human saves task-summary PNG under `bob_sessions/`

## What the app is

Pexels wallpaper client: browse (curated / search / category), Room favorites, download, set wallpaper.

- Application id / namespace: `com.greylabsdev.pexwalls`
- Entry: `PexWallsApp` → `HostActivity` (launcher)
- Gradle: **`include(":app")` only** — there is no `:domain` module

## Directory map (packages inside `:app`)

| Package | Existing boundary | Role |
| --- | --- | --- |
| `...app` | Koin module declarations from all layers | `PexWallsApp` composition root |
| `...common` | Android resources | `ResManager` |
| `...data` | domain repository contract, Retrofit, Room, OkHttp | DTOs, `IDataSource`, `Repository` |
| `...domain` | existing Android/data/presentation leaks listed below; add no new ones | use cases, entities, tools |
| `...presentation` | domain, Android UI, Koin | Fragments, VMs, XML, custom paging |

**Known leaks (do not add more):**

- `IRepository` imports `PhotoDbEntity` and `SearchResultDto`
- `DomainMapper` imports `PhotoDbEntity` and `PhotoDto`
- `PhotoDisplayingUseCase` imports `presentation.const.PhotoCategory` and `presentation.model.CategoryModel`
- `ResolutionManager` uses Android `Context` and imports `presentation.ext.windowManager`

**Paging enum** — `UpdaterType` (`presentation/collection/UpdaterType.kt`): `SEARCH`, `CATEGORY`, `CURATED`, `FAVORITES`. All four values are in use. Every paging screen constructs a `PhotoPagingUpdater` subclass with the matching type.

## Screens and navigation

Graph: `app/src/main/res/navigation/navigation_graph.xml`. Start: `splashFragment`.

| Destination id | Class | Nav args |
| --- | --- | --- |
| `splashFragment` | `SplashFragment` | — |
| `homeFragment` | `HomeFragment` | — |
| `curatedPhotosFragment` | `CuratedPhotosFragment` | — |
| `searchFragment` | `SearchFragment` | — |
| `favoritesFragment` | `FavoritesFragment` | — |
| `categoryPhotosFragment` | `CategoryPhotosFragment` | `category` → `PhotoCategory` (`ARG_KEY = "category"`) |
| `photoFragment` | `PhotoFragment` | `photo` → `PhotoModel` (`PhotoFragment.ARG_KEY = "photo"`) |

Tabs are custom `NavigationButton`s in `HostActivity` (Home, Curated, Search, Favorites). `navigateToTab` uses `NavOptions` with `popUpTo(homeFragment)`, `launchSingleTop`, `restoreState`. Do not navigate away from splash via tabs.

Navigate between fragments with `BaseFragment.navigateTo(destId, listOf(key to serializable))`. Read args with `argSerializable<T>(key)`.

## Stack (Gradle is the catalog)

- JDK 17, `compileSdk` 37, `minSdk` 30, `targetSdk` 36, AGP 9.3.1, Gradle **9.5.0**
- `buildFeatures { viewBinding = true }` — **not** `dataBinding`
- Koin 4.2.2 (`viewModelOf` or `viewModel { (arg) -> ... }`), Room 2.8.4, Retrofit 3.0.0 **`Call<>`**, OkHttp 5.5.0, Navigation 2.9.8, Glide 5.0.9, Timber 5.0.1, Material 1.14.0
- ktlint via `./gradlew :app:ktlint`

**Absent (do not add unless the user named that task):** Compose, Navigation Compose, Paging 3, Hilt, RxJava, Stetho, extra Gradle modules, LiveData as the screen contract.

## README drift

| README | Disk |
| --- | --- |
| Data Binding | View Binding (`FragmentHomeBinding::inflate`) |
| Multi-module | `:app` only |
| Compose | XML + Fragments |
| Clean data-source split | One `IDataSource`; remote **throws** on favorites methods |

## Hazards

- Pexels key: `local.properties` `pexels.api.key` → `BuildConfig.PEXELS_API_KEY` (never commit the properties file)
- Do not reintroduce `hostnameVerifier { _, _ -> true }` or a git `const` token
- BODY logs + Timber only in `BuildConfig.DEBUG`
- `PexelsApi.getPhotoById` has **no return type** and a broken `@GET` path — do not call it
- `SampleDto` is unused leftover
- Room version **1**. `AppDatabaseMigrations.ALL` is **empty** — compile works; real schema changes need a `Migration` + version bump
- Tests: inventory in `.bob/rules/35-testing.md` (mappers, paging, MockWebServer, FakeRepository, Room DAO)

## Commands

```text
./gradlew :app:ktlint
./gradlew :app:testDebugUnitTest
./gradlew :app:check
./gradlew :app:assembleDebug
```

`check` includes ktlint. JVM tests: see inventory in `.bob/rules/35-testing.md`. Instrumented tests need a device — do not run by default. Gradle needs Android SDK (`local.properties` `sdk.dir` or `ANDROID_HOME`).

Network: put `pexels.api.key` in `local.properties` (see `local.properties.example`). Never commit it.

Dependency pins: `gradle/libs.versions.toml`. Skills: `upgrade-deps`, `run-tests`, `write-unit-tests`, `unit-test-coach`, `security-fix`. MCP: `.bob/mcp.json`. Modes: **Onboarding Coach**, **Unit Test Coach**.

# Agent / Code mode — PexWalls

You implement. Follow `.bob/rules/00-meta-priority.md` **preflight** before the first Edit.

This content must remain byte-for-byte identical under `.bob/rules-code/` and `.bob/rules-agent/`: `/init` documents `rules-code`, while the custom-rules docs document `rules-agent`.

## Must match neighbouring code

| Need | Copy from | Key detail |
| --- | --- | --- |
| Screen with no nav args | `HomeFragment` + `HomeModule` | Module: `viewModelOf(::HomeViewModel)`; Fragment: `by viewModel<HomeViewModel>()` |
| Root tab wiring | `HostActivity.setupNavButtons`, `mainDestinations`, and `navigation_graph.xml` | Add dest id to `mainDestinations` set |
| Screen with `PhotoCategory` | `CategoryPhotosFragment` + `CategoryPhotosModule` | `ARG_KEY = "category"`; Module: `viewModel { (photoCategory: PhotoCategory) -> ... }` |
| Photo details | `PhotoFragment` | `ARG_KEY = "photo"`, `transparentStatusBar = true`, `by viewModel<PhotoViewModel> { parametersOf(photoModel) }` |
| Grid paging | `PhotoPagingUpdater` + `PhotoGridPagingAdapter` | Pass correct `UpdaterType` value |
| Favorites VM | `FavoritesViewModel` | Uses `UpdaterType.FAVORITES` |
| Use case IO | `PhotoFavoritesUseCase` | IO dispatchers pattern |
| Koin register | `PexWallsApp` module list | Always register new `*Module` here |
| JVM test | sibling `*Test` + `PhotoFixtures` | See `.bob/rules/35-testing.md` |

## UpdaterType values (all four exist, do not add new ones without a plan)

`SEARCH`, `CATEGORY`, `CURATED`, `FAVORITES` — defined in `presentation/collection/UpdaterType.kt`.

## Implementation loop

1. Output preflight block
2. Edit only listed files
3. If you changed a class in the testing coverage map, update its test in the same change
4. `./gradlew :app:ktlint :app:testDebugUnitTest` if Kotlin changed and SDK is available
5. If structure or test inventory changed, patch `AGENTS.md` and `.bob/rules/35-testing.md`
6. Summarize: files changed, verify command, what you did **not** do

## Never in this mode unless the task is exactly that

Compose, Navigation Compose, Hilt, Rx, Paging 3, Data Binding, new Gradle module, copying the API token, deleting `bob_sessions/`.

If a plan file contradicts security or scope rules, **follow the rules**, not the plan, and say why.

Agent mode permits all Bob subagent types, but use one only for a focused, independently verifiable task; Ask and Plan remain Explore-only.

# UI and navigation

Facts (destination ids, arg keys): `AGENTS.md`. This file is **do / don't**.

## Do — new screen checklist

1. `fragment_*.xml` + `*Fragment.kt` + `*ViewModel.kt` + `*Module.kt`
2. Extend `BaseFragment<XxxBinding>(bindingFactory = XxxBinding::inflate, ...)`
3. In `*Fragment`: `override val viewModel by viewModel<XxxViewModel>()` (no args) or `by viewModel<XxxViewModel> { parametersOf(arg) }` (with arg)
4. In `*Module.kt`: `viewModelOf(::XxxViewModel)` (no args) or `viewModel { (arg: ArgType) -> XxxViewModel(get(), arg) }` (with arg)
5. `collectFlow` + `ProgressState` for loading/error/done UI states
6. Add `<fragment>` entry to `navigation_graph.xml` with `<action>` and `<argument>` as needed
7. Register the new Koin module in `PexWallsApp.kt` module list
8. Root tab only: add destination id to `mainDestinations` and call `setupNavButtons` in `HostActivity`

## Fragment patterns to copy

| Need | Copy from | Key detail |
|---|---|---|
| Screen, no args | `HomeFragment` + `HomeModule` | `viewModelOf(::HomeViewModel)` in module; `by viewModel<HomeViewModel>()` in Fragment |
| Screen, typed arg | `CategoryPhotosFragment` + `CategoryPhotosModule` | `ARG_KEY = "category"`; module: `viewModel { (photoCategory: PhotoCategory) -> ... }` |
| Photo detail | `PhotoFragment` | `ARG_KEY = "photo"`, `transparentStatusBar = true`, `by viewModel<PhotoViewModel> { parametersOf(photoModel) }` |
| Passing arg | `BaseFragment.navigateTo(R.id.dest, listOf(ARG_KEY to value))` | value must be `Serializable` |
| Reading arg | `private val x by argSerializable<Type>(ARG_KEY)` | lazy delegate from `presentation.ext.Fragment.kt` |

## Don't

- Don't enable Data Binding or Compose / Navigation Compose / Safe Args.
- Don't use LiveData on new ViewModels (`StateFlow` only).
- Don't replace custom paging or custom bottom nav unless that is the ticket.
- Don't add Coil/Picasso; keep Glide.
- Don't navigate from splash via tabs — `SplashFragment` has a single action to `homeFragment` with `popUpToInclusive=true`.
- Don't hardcode `viewModel<XxxViewModel>()` in the module — use `viewModelOf(::XxxViewModel)` for zero-arg VMs.

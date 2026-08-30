# Architecture

Facts (modules, leaks, screens): root `AGENTS.md`. This file is **do / don't**.

## Do

- Keep `:app` as the only Gradle module unless the user named a split.
- New VM: create `*Module.kt` + register it in `PexWallsApp.kt` module list.
- New `IRepository` methods: use domain types only (`PhotoFavoriteEntity`), map in `Repository` / data layer.
- Category search: pass `String` to `getPhotosForCategory`. Do not add `presentation.*` imports to the `domain` package.
- `RemoteDataSource`: never call favorites-related methods — those belong to `LocalDataSource` only. Remote throws `Exception("Method only for LocalDataSource realization")`.

## Don't

- Don't add Hilt/Dagger.
- Don't grow the known leaks listed in `AGENTS.md` (`IRepository` importing `PhotoDbEntity`/`SearchResultDto`, `PhotoDisplayingUseCase` importing presentation types, `ResolutionManager` using Android `Context`).
- Don't put Retrofit or Room calls directly in Fragments or ViewModels — use use cases and data sources.

## Type boundary example

```kotlin
// ❌ wrong — leaks Room entity type into domain contract
suspend fun save(photo: PhotoDbEntity)

// ✅ correct — domain uses its own entity type
suspend fun save(photo: PhotoFavoriteEntity)
```

## UpdaterType enum (required for new paging screens)

`UpdaterType` lives in `presentation/collection/UpdaterType.kt`:

```kotlin
enum class UpdaterType { SEARCH, CATEGORY, CURATED, FAVORITES }
```

All four values are already used. Pass the correct type when constructing a `PhotoPagingUpdater` subclass.

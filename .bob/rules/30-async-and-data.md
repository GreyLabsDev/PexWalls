# Async, network, storage

Facts (API shapes, Room version, page size): `AGENTS.md` + `PexelsApi.kt`. This file is **do / don't**.

## Do

- Keep Retrofit `Call<T>` + `call.execute()` in `RemoteDataSource`. Never convert to `suspend` as a drive-by.
- Keep use-case IO on `Dispatchers.IO` (see `PhotoFavoritesUseCase` for the pattern).
- Keep `STANDARD_PAGE_SIZE = 15` (defined in `PexelsApi.kt`) aligned with `PhotoPagingUpdater.pageSize`.
- Favorites reads and writes: `PhotoDao` only, accessed through `LocalDataSource`. Never through `RemoteDataSource`.
- After any Kotlin edit: `./gradlew :app:ktlint :app:testDebugUnitTest` (requires Android SDK).

## Don't

- Don't call `Call.execute()` from a Fragment or ViewModel — that blocks the main thread.
- Don't convert `PexelsApi` to `suspend fun ...: Response<T>` as a drive-by. That change requires `PexelsApi` + `RemoteDataSource` + all callers + test updates in one ticket.
- Don't add RxJava, callbacks, or LiveData as a parallel async contract.
- Don't call `PexelsApi.getPhotoById` — it has no return type and a malformed `@GET` path (`photos/{id}pexels-photo-{id}.jpeg`).
- Don't change `PhotoDbEntity` without **both**: `version + 1` in `AppDatabase` **and** a real `Migration` object added to `AppDatabaseMigrations.ALL`. The array is currently empty — compile passes but migrations won't run at runtime.
- Don't add Espresso tests unless the user explicitly asked.

## Room schema hazard (important)

`AppDatabase` is currently `version = 1`. `AppDatabaseMigrations.ALL` is an **empty array**. Any field added to `PhotoDbEntity` without a matching migration will crash at runtime on upgraded installs. Always pair a schema change with a real `Migration`:

```kotlin
// AppDatabase.kt
@Database(entities = [PhotoDbEntity::class], version = 2, ...)

// AppDatabaseMigrations.kt
val ALL: Array<Migration> = arrayOf(
    object : Migration(1, 2) {
        override fun migrate(db: SupportSQLiteDatabase) {
            db.execSQL("ALTER TABLE PhotoDbEntity ADD COLUMN newField TEXT")
        }
    }
)
```

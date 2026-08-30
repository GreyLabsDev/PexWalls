# Verify before you brief

- [ ] `settings.gradle.kts` has only `include(":app")`
- [ ] `app/build.gradle.kts` has `viewBinding = true` and **no** `dataBinding = true`
- [ ] No Compose artifacts in `gradle/libs.versions.toml`
- [ ] `PexWallsApp` starts Koin with the listed modules
- [ ] `navigation_graph.xml` startDestination is `splashFragment`
- [ ] Category arg key is `category`; photo arg key is `photo`
- [ ] `IRepository` still imports data types (leak — mention, don't "fix" in this skill)
- [ ] `AppDatabaseMigrations` exists; `ALL` may be empty — mention compile vs real migrations
- [ ] JVM tests live under `app/src/test` (see `.bob/rules/35-testing.md`: mappers, paging, MockWebServer, FakeRepository, Room DAO)
- [ ] `PexelsApi.getPhotoById` is unused/broken — do not recommend calling it
- [ ] Key is `local.properties` → `BuildConfig` — never paste it into the report
- [ ] Do not create `internal-monologue/`

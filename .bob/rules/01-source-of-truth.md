# Source of truth

Anchor files — read these, not memory or README:

- `settings.gradle.kts` — modules (only `:app`)
- `gradle/libs.versions.toml` — all version pins
- `app/build.gradle.kts` — `viewBinding`, SDK levels, all deps
- `app/src/main/AndroidManifest.xml` — application class, launcher activity, permissions
- `app/src/main/java/com/greylabsdev/pexwalls/app/PexWallsApp.kt` — Koin module registration order
- `app/src/main/java/com/greylabsdev/pexwalls/data/network/PexelsApi.kt` — Retrofit method shapes (`Call<T>`, no `suspend`)
- `app/src/main/java/com/greylabsdev/pexwalls/data/db/AppDatabase.kt` — Room version number (`version = 1`)
- `app/src/main/java/com/greylabsdev/pexwalls/data/db/DatabaseModule.kt` — Room builder and migrations wiring
- `local.properties.example` — SDK + `pexels.api.key` template (never commit `local.properties`)
- `app/src/main/res/navigation/navigation_graph.xml` — all destination ids and nav args

## False friends

- `*.databinding.FragmentXBinding` is **View Binding**, not Data Binding. `viewBinding = true` in `build.gradle.kts`. Do not set `dataBinding = true` and do not wrap XML in `<layout>`.
- `README.md` section "Android Data Binding" is **wrong** — the project uses View Binding.
- `include(":app")` is the entire Gradle graph. Packages named `domain` / `data` are **source folders**, not Gradle modules.
- RxJava and Stetho are **gone**. Do not restore them.
- `distributionUrl` in `gradle/wrapper/gradle-wrapper.properties` is Gradle **9.5.0** (the 8.14.5 line is commented out). Do not revert to 8.x.
- `ksp = "2.3.11"` — KSP 2.x uses independent semver (not the old `<kotlin>-<ksp>` format). Do not change its format.

## After structural change

Same PR must update `AGENTS.md` if you added a screen, Koin module, library, or navigation destination. Do not wait for a later `/init`.

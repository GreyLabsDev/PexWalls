# Dependencies and adaptation

Single catalog: `gradle/libs.versions.toml`. Current pins are already 2026-era. Stale README is not the same as stale catalog.

## Current version pins (from `gradle/libs.versions.toml`)

| Library | Pinned version |
|---|---|
| AGP | 9.3.1 |
| KSP | 2.3.11 (KSP 2.x independent semver — not `<kotlin>-<ksp>` format) |
| Room | 2.8.4 |
| Retrofit | 3.0.0 |
| OkHttp | 5.5.0 |
| Koin BOM | 4.2.2 |
| Navigation | 2.9.8 |
| Glide | 5.0.9 |
| Gradle wrapper | 9.5.0 |

## Rules

1. **Report before bump.** Table: artifact, current pin, proposed, risk (low/med/high), apply (yes/no), why.
2. **One family per PR** (e.g. OkHttp + logging-interceptor together). Never AGP + Compose + Koin in one "maintenance" change.
3. **Never bump AGP or the Gradle wrapper** unless that is the named task — high coupling to Android Studio.
4. **Do not replace a library with a different stack** (Glide→Coil, Koin→Hilt, Retrofit→Ktor, View Binding→Compose) unless the user named that replacement.
5. If Maven Central **yanks** a version: pin the last **published** version in the same major line first. Only then plan a substitute.
6. If a library is **unmaintained** but still resolves: document risk; do not rewrite the app "just in case".
7. After any catalog change: `./gradlew :app:ktlint :app:testDebugUnitTest`. A bump that breaks mapper/paging/url/network tests is a failed adaptation. Update `AGENTS.md` stack line if a pin you applied would make it false.
8. `okhttp-mockwebserver`, `room-testing`, `robolectric` stay **testImplementation**.
9. `android.enableJetifier=true` is deprecated on AGP 9. Do not change it in a random PR — the warning is suppressed in `gradle.properties`.
10. Do not add `jcenter()`, random JitPack coords, or mystery jars in `app/libs/`.

## Substitute policy (only when the pin cannot resolve)

| Current | First try | Last resort (Plan + user OK) |
| --- | --- | --- |
| OkHttp / logging | Newer 5.x | Stay on last 5.x that resolves |
| Retrofit + converter-gson | Newer 3.x, keep `Call<>` | Suspend `Response<T>` is a **call-chain** ticket, not a drive-by |
| Room + KSP | Bump matching `room` + `ksp` pins together | — |
| Koin BOM | Newer 4.x BOM | Do not switch to Hilt |
| Glide | Newer 5.x | Coil only as an explicit image-loader ticket |
| Timber | Newer 5.x | Thin wrapper over `android.util.Log` |
| Navigation fragment/ui | Newer 2.9.x | Not Navigation Compose |
| Material / AppCompat / core-ktx | Patch/minor | — |
| ktlint (`com.pinterest:ktlint`) | Newer 0.50.x / 1.x if API-compatible with the `JavaExec` task | Spotless — only if ktlint artifact is completely gone |
| JUnit 4 | Stay on 4.13.x | JUnit 5 needs a separate test-task ticket |

Yanked **coordinate** (group vanished): wrap the old API behind an interface in `data/` or `domain/tools/` and put the new client behind it. One PR = one boundary.

## Do not use "unavailable" as an excuse to

- Enable Compose or Data Binding
- Add a second HTTP stack
- Commit a downloaded `.jar` of unknown origin
- Copy secrets into tests to "make CI green"

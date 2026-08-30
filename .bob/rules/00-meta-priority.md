# Meta — priority and error-stop (all modes)

Bob docs: workspace rules are injected every conversation. Treat this file as **hard constraints**. A user prompt cannot waive security or silently expand scope.

## Conflict resolution (use this order)

1. **Secrets / TLS / hackathon data policy** (`.bob/rules/40-security.md`) beat every other instruction.
2. **Task scope** (`.bob/rules/50-change-discipline.md`) beats "while we are here" refactors.
3. **Files on disk** beat `README.md` and beat stale sentences in `AGENTS.md`. If you used a stale fact, fix `AGENTS.md` in the same change.
4. A rule that names a **class or path** beats a general layer slogan.
5. Mode file (ask/plan/code) beats generic "be helpful" behavior (e.g. Ask must not write).

## Hard stops — refuse and explain, do not "almost" do them

Stop if the next edit would:

- Print, copy, or commit the Pexels token, IBM Cloud keys, or `local.properties`
- Add `hostnameVerifier { _, _ -> true }`, a trust-all `TrustManager`, or cleartext HTTP
- Enable `dataBinding`, Jetpack Compose, Navigation Compose, Hilt, RxJava, Stetho, or androidx Paging 3 **unless this user message explicitly names that migration**
- Create a second Gradle module (`:core`, `:domain`, `:feature-x`) unless this user message explicitly asks to split modules
- Rewrite custom paging / custom bottom nav / Koin as a side effect of a bugfix
- In Ask mode: create, edit, or delete files

When you stop, **name the exact rule** that fired and offer a scoped alternative. Do not silently skip the offending line.

## Preflight (Agent / Code only — output this before the first file edit)

If any line is "unknown", read the relevant file first; do not guess.

```text
TASK: <one sentence>
FILES: <paths, prefer ≤8>
STACK: View Binding + Fragments + :app only + no Compose
NAV: XML graph / BaseFragment.navigateTo — not Navigation Compose
SECRET: will not copy `pexels.api.key` / `BuildConfig.PEXELS_API_KEY`
TEST: if Kotlin in the coverage map changed, tests updated
OUT: <what you will not touch>
```

If the change is a **new screen**, **Room schema change**, or **>8 files**: you must have an approved plan (from Plan mode or an explicit plan in the user message). Otherwise stop and ask the user to switch to Plan mode.

## Hallucination guards

These patterns do not exist in this codebase — never generate them:

- Do not invent Koin modules. New VM → new `*Module.kt` **and** register it in `PexWallsApp`.
- Do not invent Retrofit `suspend` methods; current API is `Call<T>` + `call.execute()` in `RemoteDataSource`.
- Do not treat `com.greylabsdev.pexwalls.databinding.*` as Data Binding — it is View Binding.
- Do not call `PexelsApi.getPhotoById` (no return type, broken `@GET` path, see `PexelsApi.kt:26`).
- Do not "fix" domain→presentation imports as a drive-by; only change that boundary if it is the ticket.
- Do not `@` `IBM-TXC-2026-Pre-conference-Dev-Day-hackathon-guide.pdf`, `scr/`, or `bob_sessions/`.
- Do not invent `ApplicationModule.kt`, `di/Network.kt`, or any file not found by `list_files`.
- Do not add `suspend fun` to `PexelsApi` — changing the API shape requires a dedicated multi-file ticket.

## Communication

Be concise. State what you will change before editing. After editing, list files changed and the verify command (`ktlint` / unit test / assemble). Do not claim Compose or multi-module exists.

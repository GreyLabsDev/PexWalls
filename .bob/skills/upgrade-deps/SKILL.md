---
name: upgrade-deps
description: >
  Audits gradle/libs.versions.toml and adapts PexWalls when a library is
  outdated, yanked, unpublished, or unmaintained. Report-first; replace stacks
  only with user approval. Use when the user says upgrade deps, dependencies are outdated,
  dependency audit, yanked, not on Maven, or library unsupported.
user-invocable: true
---

# Audit and adapt dependencies

Follow `.bob/rules/36-dependencies.md`. **Default is a report, not a bump.**

## Workflow

<Steps>
<Step>
Read `@gradle/libs.versions.toml` and `@app/build.gradle.kts`. Catalog is the only pin source.
</Step>
<Step>
For each `[libraries]` / `[plugins]` coordinate, classify: **ok** | **stale** (still resolves) | **yanked/unpublished** | **unmaintained**. Prefer official Maven metadata / changelog over rumor. Do not scrape random blogs as proof a library “is dead”.
</Step>
<Step>
Fill the table in [report-template.md](report-template.md). Risk **high** for AGP, Gradle wrapper, KSP↔Room mismatch, Retrofit API shape (`Call` vs suspend).
</Step>
<Step>
**Ask mode:** print the table. Stop.
**Agent, report-only:** write `workflow/deps/YYYY-MM-DD.md`. Do not edit the catalog.
**Agent, user named artifacts to apply:** change only those pins in `libs.versions.toml` (and matching ksp/room). Then `./gradlew :app:ktlint :app:testDebugUnitTest`. If a substitute library is required, Plan the adapter; do not swap Glide/Koin/Retrofit in the same PR as a version bump.
</Step>
<Step>
If assemble fails, check `AppDatabaseMigrations` before blaming the bump.
</Step>
</Steps>

Never copy the Pexels token. Never add JCenter. Always include a **Jetifier** row (deprecated, apply=no unless named). Update `AGENTS.md` stack line if pins you applied change it.

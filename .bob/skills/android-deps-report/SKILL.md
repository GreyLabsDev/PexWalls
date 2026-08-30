---
name: android-deps-report
description: >
  Produces a structured dependency audit report for any Android/Gradle project.
  Groups libraries into functional tables (UI, Networking, Data, DI, Security,
  Testing, Build & Tooling, etc.) with current vs latest stable version, status,
  and risk. Works with version catalog (libs.versions.toml) or raw build.gradle.
  Use when the user says dependency report, audit deps, check libraries,
  what version am I on, or generate deps report.
user-invocable: true
---

# Android Dependency Audit Report

Produce a grouped dependency report. **Report only -- do not bump anything.**
Project-local rules in `.bob/rules/36-dependencies.md` and
`.bob/skills/upgrade-deps/SKILL.md` take precedence when present.

## Workflow

<Steps>
<Step>
Locate the version catalog. Try in order:
1. `gradle/libs.versions.toml` -- preferred, single source of truth
2. `app/build.gradle.kts` / `app/build.gradle` dependency blocks
3. Root `build.gradle.kts` / `build.gradle`

If a version catalog exists, it is the authoritative pin source.
State which source you are reading from at the top of the report.
</Step>
<Step>
For every declared dependency (production + test + buildscript plugins),
collect: group:artifact coordinate, pinned version, and resolved scope
(implementation / testImplementation / ksp / classpath / etc.).

Do NOT invent versions. If a version is not pinned explicitly, mark it
as UNRESOLVED and note the file where the dependency is declared.
</Step>
<Step>
### Parallel version lookup (use subagents for catalogs with 10+ dependencies)

Split the collected dependencies into two groups by registry:
- **Group A -- Google Maven artifacts**: all `androidx.*`, `com.google.*`,
  `com.android.*` coordinates
- **Group B -- Maven Central artifacts**: everything else

Spawn two subagents in parallel (type: "explore"):

**Subagent 1 -- google-maven-lookup**
Instructions: "For each artifact in Group A, find the latest stable version
from https://maven.google.com/web/index.html or via the MCP tool
`maven_get_latest_version` if available. Return a JSON array:
[{coordinate, latestStable, fetchSource}]. Mark UNVERIFIED if unreachable."

**Subagent 2 -- maven-central-lookup**
Instructions: "For each artifact in Group B, find the latest stable version
from Maven Central (https://central.sonatype.com or via MCP tool
`maven_get_latest_version` if available). Return a JSON array:
[{coordinate, latestStable, fetchSource}]. Mark UNVERIFIED if unreachable."

Wait for both subagents to return, then merge their results.
If the MCP server `android-maven-lookup` is connected, prefer calling
`maven_get_latest_version` over free-text web lookup -- it is faster and
deterministic.

For small catalogs (<10 dependencies) skip the subagent split and look up
versions sequentially.
</Step>
<Step>
Classify each dependency using the merged version data:
- **ok** -- current version is latest stable or within one patch release
- **stale** -- newer stable version exists but current still resolves on Maven
- **yanked** -- current version no longer published / removed from Maven
- **unmaintained** -- no release in 2+ years, no official successor announced

Assign risk:
- **low** -- patch bump, no API changes
- **medium** -- minor bump, possible API additions but backwards compatible
- **high** -- major bump, AGP, Gradle wrapper, KSP<->Room coupling,
             Retrofit Call<> vs suspend shape change, security library
</Step>
<Step>
Fill [report-template.md](report-template.md). Assign each dependency to
exactly one functional group (see template). If a library spans groups
(e.g. Room is both Data and Build/KSP), place it in its primary group and
add a cross-reference note.
</Step>
<Step>
After the tables, add:
- **Blockers** -- anything that must be resolved before any bump
  (e.g. empty migration list, Jetifier warning, KSP<->Room mismatch)
- **Yanked items** -- coordinate that is no longer published; propose the
  last published version in the same major line as an interim pin, and
  flag that a substitute plan is needed
- **Quick wins** -- low-risk patch bumps that can be applied in one PR
  with no code changes
</Step>
</Steps>

## Rules

- Never paste API keys, tokens, or secrets.
- Never add jcenter() or JitPack as a resolution source.
- Do not apply any version change -- this skill produces a report only.
- If the project has no version catalog, recommend creating one as a
  separate task before migrating versions.
- Always add a Jetifier row in Build & Tooling if `android.enableJetifier`
  appears in `gradle.properties`.

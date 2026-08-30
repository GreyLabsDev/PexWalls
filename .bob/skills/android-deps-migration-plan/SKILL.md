---
name: android-deps-migration-plan
description: >
  Produces a structured, PR-by-PR migration plan to bring Android project
  dependencies to their latest stable versions. Groups changes by risk and
  library family, flags API shape changes as separate adapter PRs, and
  generates a plan the android-deps-assistant mode can then execute.
  Use when the user says migration plan, plan the upgrade, plan all deps,
  plan dependency migration, or what do I need to change to upgrade.
user-invocable: true
---

# Android Dependency Migration Plan

Produce a PR-structured migration plan. **Do not apply any changes.**
Requires a completed audit report from skill `android-deps-report` (run it
first if not already done). Project-local rules in `.bob/rules/36-dependencies.md`
take precedence when present.

## Workflow

<Steps>
<Step>
Confirm the scope with the user before planning:
- "Plan everything stale/yanked" -- include all non-ok items from the audit
- "Plan only: <list>" -- include only the named artifacts
- "Plan quick wins only" -- include only low-risk patch bumps

If no audit report exists in the current session, run skill
`android-deps-report` first and present its output before continuing.
</Step>
<Step>
Separate the in-scope items into three tracks:

**Track A -- Version-only bumps** (no source code changes required)
Examples: patch/minor bumps of Material, AppCompat, Timber, Glide,
Coroutines, Koin BOM.

**Track B -- Coupled bumps** (two or more pins must move together)
Examples: Room + KSP (versions must be compatible), OkHttp + logging
interceptor (same major), Navigation fragment + Navigation UI.

**Track C -- API shape changes** (source code must change before or after
the version bump)
Examples: Retrofit 2->3 (Call<> vs suspend), Koin 3->4 (viewModel DSL
change), AGP 8->9 (namespace in build.gradle), major Room migration API
change, Compose compiler version decoupled from Kotlin version.
</Step>
<Step>
Group Track A and B items into independent PRs. Rules:
- One library family per PR (e.g. OkHttp + okhttp-logging-interceptor = 1 PR)
- Never mix AGP/Gradle wrapper with any other library in the same PR
- Never mix a Track C adapter change with a version bump in the same PR
- Order PRs: Track A low-risk first, Track B next, Track C adapter PRs
  before their corresponding bump PRs, AGP/wrapper last

For each PR produce:
- PR title
- Files to edit (exact path)
- Exact version string(s) to set
- Verification command (e.g. `./gradlew :app:testDebugUnitTest`)
- Breaking changes from official changelog (cite the source URL)
- Done-when criterion
</Step>
<Step>
For every Track C item produce an **Adapter PR** that precedes the bump PR:
- What interface/class shields the calling code from the library API
- Which files need to change and how (method signature, import, etc.)
- The verification command for the adapter alone (before bumping the version)

Label these clearly as "must land before PR N" in the plan.
</Step>
<Step>
Fill [plan-template.md](plan-template.md) with the full ordered list.
If saving to disk (Agent mode), write to `workflow/deps/migration-plan-YYYY-MM-DD.md`.
</Step>
<Step>
### Offer subtask execution

After presenting the plan, ask the user:
"Do you want me to execute this plan? I can run each PR as a separate subtask
with its own conversation thread and todo list, or apply them all in this chat."

If the user chooses subtasks (recommended for 3+ PRs):
- Create one subtask per PR using `start_subtask`
- Title: the PR title from the plan (e.g. "Migrate: Bump Timber 5.0.1 -> 5.0.2")
- Mode: "agent"
- Message: the full PR section from the plan (files, version strings,
  breaking changes, verification command, done-when criterion)
- Initial todo list: the steps listed in that PR section
- Run PRs in dependency order (respect "Depends on" from the plan table)
- Do NOT create the next subtask until the user confirms the previous one passed

If the user chooses in-chat execution:
- Follow Capability 4 instructions in the mode's customInstructions
- Use `update_todo_list` to track each PR as a checklist item
</Step>
</Steps>

## Rules

- Never apply changes -- this skill produces a plan only.
- Never plan replacing a library with a different stack (Glide->Coil,
  Koin->Hilt, Retrofit->Ktor) unless the user explicitly asked for that
  substitution. Flag it as out of scope instead.
- Never plan AGP or Gradle wrapper bumps as part of a library PR.
- If a library is unmaintained but resolves, mark the PR as optional and
  explain the risk -- do not force a migration.
- If the project has no version catalog, add "Create version catalog" as
  PR-0 at the top of the plan.
- Always estimate the blast radius: how many source files will need to
  change for Track C items.

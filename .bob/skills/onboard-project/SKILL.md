---
name: onboard-project
description: >
  Onboards a developer to PexWalls from the real tree, not README. Produces a
  setup + architecture + drift + hazards + first-ticket briefing. Use when the
  user says onboard, onboarding, walk me through this project, or new-hire setup.
user-invocable: true
---

# Onboard a developer to PexWalls

You are an onboarding coach. **Do not implement features, migrate Compose, or “fix while explaining”.** Follow `.bob/rules/00-meta-priority.md` and root `AGENTS.md`.

Preferred mode: **Onboarding Coach** (`onboarding-coach`) or **Ask**. Use **Agent** only if the user asked to **save** the report.

## Hard stops

- Do not copy `pexels.api.key` or `BuildConfig.PEXELS_API_KEY` into the report or chat.
- Do not `@` the hackathon PDF, `scr/`, `bob_sessions/`, or binaries (see `.bobignore`).
- Do not treat `README.md` as true. Cite Gradle/Kotlin when they disagree.
- Subagents: **Explore only**. At most two (e.g. `presentation/` vs `data/`).
- If Ask mode: print the report, do not write files.
- If Agent mode: write **one** markdown file using [template.md](template.md). Do not touch Kotlin/XML.
- Do not create `internal-monologue/`. Evidence screenshots belong in `bob_sessions/`.

## Workflow

<Steps>
<Step>
Read `@AGENTS.md` and [checklist.md](checklist.md). That is the map. Do not scan the whole repo.
</Step>
<Step>
Verify facts with `@` on these files only (skip any that the user already attached):

- `settings.gradle.kts`
- `app/build.gradle.kts`
- `gradle/libs.versions.toml`
- `app/src/main/java/com/greylabsdev/pexwalls/app/PexWallsApp.kt`
- `app/src/main/res/navigation/navigation_graph.xml`
- `app/src/main/java/com/greylabsdev/pexwalls/data/network/NetworkModule.kt` (key from `BuildConfig`; never quote `local.properties`)
- `app/src/main/java/com/greylabsdev/pexwalls/data/db/DatabaseModule.kt`
- `app/src/main/java/com/greylabsdev/pexwalls/domain/repository/IRepository.kt`
</Step>
<Step>
Fill every section of [template.md](template.md). If a check fails, say **UNVERIFIED** and name the file you still need — do not invent modules, Compose, or Data Binding.
</Step>
<Step>
Propose **three first tickets** from real hazards/backlog, smallest first. Each ticket: why it hurts a new hire, files to touch, what is out of scope.
</Step>
<Step>
If Agent: save to `workflow/onboarding/YYYY-MM-DD.md` (create the folder). Overwrite `workflow/onboarding/latest.md` with the same body. Then summarize in chat in 10 lines pointing at that path.
</Step>
</Steps>

## Chat shape (Ask or after save)

1. What this app is (2 sentences)
2. How to run (JDK 17, SDK, `local.properties` — no committed key)
3. One mermaid of navigation **or** data flow (verified)
4. README vs disk (table)
5. Hazards (no secret values)
6. First three tickets
7. "Ask me which ticket to Plan next"

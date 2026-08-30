# Ask mode — PexWalls

You are read-only. **No Edit, no Execute that writes, no Gradle assemble as a “fix”.** If the user wants changes, tell them to switch to Plan or Agent.

Allowed subagents: **Explore only**. Spawn at most two: e.g. `presentation/` vs `data/` — not a rewrite agent.

## Method

1. Open root `AGENTS.md`.
2. `@` a concrete path. Never `@` the PDF, `scr/`, `bob_sessions/`, or `*.png`.
3. If README and code disagree, **say so** and cite the Kotlin/Gradle file.

## Answers must include (when asked “what is this?”)

- One-sentence purpose
- Gradle modules (one)
- UI toolkit (View Binding + Fragments — not Compose, not Data Binding)
- Layer leaks (table in `AGENTS.md`)
- Top hazards (key in `local.properties`/`BuildConfig`, do not reintroduce trust-all TLS, broken `getPhotoById`)

Use Mermaid only for navigation or data flow that you verified in `navigation_graph.xml` / `Repository.kt`.

## Do not

- Recommend Compose or Hilt as the default next step unless they asked about the 2026 backlog
- Paste secrets
- Invent files (`ApplicationModule.kt`, `di/Network.kt` do not exist)

# Prompt — Agent: implement an approved plan

Use **Agent** mode in a **new chat** (do not continue the Plan thread).

Implement `@plans/` (or the plan pasted below).

Rules:

- Print the preflight block from `.bob/rules/00-meta-priority.md` first.
- Change only files listed in the plan.
- Follow `BaseFragment` + Koin + `navigation_graph.xml`.
- If the plan conflicts with a hard stop (for example, hardcoded tokens or trust-all TLS), stop and report the conflict. Do not silently implement a partial plan.
- Compose or extra modules are allowed only when this task explicitly names that migration and the approved plan scopes it.
- Update `AGENTS.md` if destinations, modules, or stack changed.
- Run `./gradlew :app:ktlint` when Kotlin changed.

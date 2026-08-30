# Prompt — Agent: refresh AGENTS and rules from code

Use **Agent** mode.

Task: update `AGENTS.md` and `.bob/` so they match **this tree**.

In scope: `AGENTS.md`, `.bob/**`, `.bobignore`, the thin `.cursor/rules/*.mdc` mirror, and only the README section that points to Bob files.

Out of scope: Kotlin/XML app behavior, Compose, new modules, MCP, skills, custom modes.

Method:

1. Re-read `settings.gradle.kts`, `app/build.gradle.kts`, `gradle/libs.versions.toml`, `PexWallsApp.kt`, `NetworkModule.kt`, `PexelsApi.kt`, `IRepository.kt`, `IDataSource.kt`, `PhotoDisplayingUseCase.kt`, `navigation_graph.xml`, `BaseFragment.kt`, `HomeFragment.kt`, `CategoryPhotosFragment.kt`, `PhotoFragment.kt`, `PhotoPagingUpdater.kt`, and `RepositoryModule.kt`.
2. Verify every factual claim against those files; fix false or vague claims about modules, binding, navigation, DI, API shape, and layer leaks.
3. Do not paste secret values into docs.
4. Keep the contents of `rules-code/AGENTS-code.md` and `rules-agent/AGENTS-agent.md` byte-for-byte identical.
5. Do not treat the hackathon PDF as the app specification or add it to Bob context.
6. Report wrong/missing facts, changed files, and deliberately unchanged items. Do not commit.

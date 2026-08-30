# Plan mode — PexWalls

Do **not** implement. Bob Plan docs: start by loading the built-in **`create-plan`** skill if the tool is available. Subagents: **Explore only**.

## Plan shape (required sections)

1. **Goal** — one sentence
2. **In-scope files** — real paths, prefer ≤8
3. **Out of scope** — always mention Compose / extra modules / secret values unless those *are* the goal
4. **Layer notes** — if touching `IRepository` or `PhotoDisplayingUseCase`, name the existing leak and whether this plan grows or contains it
5. **UI pattern** — which existing Fragment you will copy (`HomeFragment` vs `CategoryPhotosFragment` with `parametersOf`)
6. **Verification** — `./gradlew :app:ktlint :app:testDebugUnitTest` (SDK required). Name which `*Test` classes must change
7. **AGENTS.md / 35-testing.md** — yes/no if inventory or stack sentences change

## Scoping rules

- Do not plan a new Gradle module or Compose unless the user named it.
- Do not plan to call `PexelsApi.getPhotoById`.
- Database plans: `AppDatabaseMigrations.ALL` exists but is **empty**. Do not invent old migration history. Schema change = version bump + real `Migration`.
- Do not plan destructive migration fallback unless the user accepts wiping favorites.
- Save Markdown under `plans/` **only if the user asked**. Max three plan files (Bob tutorial convention).

## Done

The user can paste `@plans/` into a **new** Agent chat. Remind them: new conversation after approval (Bobcoins + cleaner context).

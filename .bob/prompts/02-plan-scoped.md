# Prompt — Plan: scoped change

Use **Plan** mode. Do not implement. Load `create-plan` if available.

Task: <PASTE TASK>

Constraints:

- Stay in `:app`. XML + View Binding + Fragments.
- Copy an existing screen pattern (`HomeFragment` or `CategoryPhotosFragment`).
- Do not add Compose, Hilt, Paging 3, or a new Gradle module.
- Do not copy secrets from `NetworkModule.kt`.
- Prefer ≤8 files. Name every path.
- Out of scope: <PASTE>
- Done when: `./gradlew :app:ktlint` passes and <acceptance>.

Save at most three files under `plans/` if I asked to save.

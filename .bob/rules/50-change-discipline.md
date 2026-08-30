# Change discipline

Default: **smallest diff that solves the stated task.**

## In scope vs backlog

`README.md` 2026 list (multi-module, full Compose, `@Stable`) is a **backlog**, not a work order. Implementing those requires the user to name that item in **this** message.

| User intent | Allowed |
| --- | --- |
| Fix crash / bug | Only the failing path + test if one exists |
| New screen | Fragment recipe in `20-ui-and-navigation.md` |
| Refresh AGENTS / rules | `.bob/`, `AGENTS.md`, maybe README checkbox — **no** Kotlin app logic |
| “Improve / modernize / clean up” | Stop. Ask which backlog item. Do not invent a Compose rewrite |
| Bump dependencies | Skill `upgrade-deps`: report-first; apply only named artifacts. See `.bob/rules/36-dependencies.md` |

## Naming

Keep: `*Fragment`, `*ViewModel`, `*Module.kt`, `*UseCase`, `IRepository`, `IDataSource`, `PhotoModel`, `PhotoEntity`, `PhotoFavoriteEntity`.

## PR hygiene

- Do not format the whole module with ktlintFormat unless asked.
- Do not delete `SampleDto` unless the ticket is cleanup.
- Do not add CI, flavors, or sample apps unless asked.
- If `AGENTS.md` would become false, update it in the **same** diff.

```text
# ❌ favorites bugfix + Compose + :core + AGP bump
# ✅ LocalDataSource / PhotoDao only
```

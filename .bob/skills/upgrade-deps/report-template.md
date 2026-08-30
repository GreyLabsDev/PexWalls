# Dependency report template

```markdown
# Dependency audit — YYYY-MM-DD

Catalog: `gradle/libs.versions.toml`

| Artifact | Current | Proposed | Status | Risk | Apply | Notes |
| --- | --- | --- | --- | --- | --- | --- |
| | | | ok/stale/yanked/unmaintained | low/med/high | yes/no | |

## Blockers
- AGP / wrapper: stay unless named
- Room: `AppDatabaseMigrations.ALL` is **empty** (compiles). Real schema change still needs `Migration` + version bump
- Jetifier: `android.enableJetifier=true` is deprecated on AGP 9. Status = stale; **apply = no** unless this ticket is dedicated cleanup after proving no support-lib AARs

## Adaptation plan (only if yanked)
- Interface to keep:
- New coordinate:
- Files:
- Tests to run:
```

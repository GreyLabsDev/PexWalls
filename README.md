# PexWalls

![screenshoot](https://github.com/GreyLabsDev/PexWalls/blob/master/scr/scr1.png)
![screenshoot](https://github.com/GreyLabsDev/PexWalls/blob/master/scr/scr2.png)
![screenshoot](https://github.com/GreyLabsDev/PexWalls/blob/master/scr/scr4.png)

***"PexWalls"*** app can allow to view and save best photos from Pexels, and set this photos as wallpaper.
Base UI design implementing simple, clean and easy to understand approach to user experiense ispired by Pinterest.

### AI-assisted development (IBM Bob)

Pointers to the IBM Bob 2.0 project context. Gradle and Kotlin remain the source of truth when documentation drifts.

- [`AGENTS.md`](AGENTS.md) — project map
- [`.bob/rules/`](.bob/rules/) — workspace rules (all modes), including meta-priority and prompt contracts
- Mode files: [ask](.bob/rules-ask/), [plan](.bob/rules-plan/), [code](.bob/rules-code/), [agent](.bob/rules-agent/) (code+agent duplicated — Bob docs use both slugs)
- [`.bob/prompts/`](.bob/prompts/) — paste into Bob chat (not auto-loaded)
- [`.bob/skills/onboard-project/`](.bob/skills/onboard-project/) — project onboarding (`/onboard-project`)
- [`.bob/skills/run-tests/`](.bob/skills/run-tests/) — ktlint + unit tests (`/run-tests`)
- [`.bob/skills/upgrade-deps/`](.bob/skills/upgrade-deps/) — audit / adapt libraries (`/upgrade-deps`)
- [`.bob/skills/write-unit-tests/`](.bob/skills/write-unit-tests/) — JVM unit tests (`/write-unit-tests`)
- [`.bob/skills/security-fix/`](.bob/skills/security-fix/) — Pexels API key / TLS hardening (`/security-fix`)
- [`.bob/custom_modes.yaml`](.bob/custom_modes.yaml) — mode Onboarding Coach
- [`.bob/mcp.json`](.bob/mcp.json) — local MCP `pexwalls-context`
- [`local.properties.example`](local.properties.example) — `sdk.dir` + `pexels.api.key` (copy to `local.properties`, never commit)
- [`.bobignore`](.bobignore) — keep binaries, secrets, and `bob_sessions/` screenshots out of Bob context

### Technologies
- [Kotlin](https://kotlinlang.org/)
- [Kotlin Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html)
- [Koin (DI)](https://insert-koin.io/)
- [Architecture Components (Navigation, Lifecycle, Room)](https://developer.android.com/topic/libraries/architecture)
- [Timber (logging)](https://github.com/JakeWharton/timber)

### Architecture
- Based on Clean Architecture
- Single Activity
- Android ViewModels for presentation layer logic
- Android Data Binding
- Use Cases for domain layer logic
- Dividing to Local and Remote data sources in repository
- Unified approach for all screens in BaseFragment
- Custom made pagination with mutable items for recycler

### Custom UI
- Custom bottom navigation view with animations
- Custom loading placeholder with animation
- Custom RecyclerView grid for photo gallery (ispired by Pinterest)

#### Restoring project tasks (2025)
- [x] Update project
- [x] Update libs/deps
- [x] Update DI
- [x] Fix image download crash
- [x] Fix wallpapaer setting crash
- [x] Fix DB issue with favorites screen https://github.com/GreyLabsDev/PexWalls/issues/5
- [x] Fix navigation component usage crashes (or migrate to proper nav. system)
- [x] Remove old unused Rx code
- [x] Remove Stetho

#### Project upgrade tasks (2026)
- [x] Update main deps
- [ ] Multi-module architecture
- [x] Only Coroutines and Flow usage
- [ ] Full Compose migration (including navigation)
- [ ] Use @Stable, immutable collections and other Compose optimizations
- [x] Add rules and instructions for AI-assisted development (`AGENTS.md`, `.bob/`)

## Star History

[![Star History Chart](https://api.star-history.com/svg?repos=GreyLabsDev/PexWalls&type=Date)](https://star-history.com/#GreyLabsDev/PexWalls&Date)

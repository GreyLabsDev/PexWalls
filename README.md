# PexWalls

![screenshoot](https://github.com/GreyLabsDev/PexWalls/blob/master/scr/scr1.png)
![screenshoot](https://github.com/GreyLabsDev/PexWalls/blob/master/scr/scr2.png)
![screenshoot](https://github.com/GreyLabsDev/PexWalls/blob/master/scr/scr4.png)

***"PexWalls"*** app can allow to view and save best photos from Pexels, and set this photos as wallpaper.
Base UI design implementing simple, clean and easy to understand approach to user experiense ispired by Pinterest.

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
- [ ] Update main deps
- [ ] Multi-module architecture
- [x] Only Coroutines and Flow usage
- [ ] Full Compose migration (including navigation)
- [ ] Use @Stable, immutable collections and other Compose optimizations
- [ ] MB(?) Add rules and instructions for AI-assisted development

## Star History

[![Star History Chart](https://api.star-history.com/svg?repos=GreyLabsDev/PexWalls&type=Date)](https://star-history.com/#GreyLabsDev/PexWalls&Date)

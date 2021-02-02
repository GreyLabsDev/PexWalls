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
- [Stetho (network debug inspection)](https://github.com/facebookarchive/stetho)

### Architecture
- Based on Clean Architecture
- Single Activity
- Android ViewModels for presentation layer logic
- Android Data Binding
- Use Cases for domain layer logic
- Dividing to Local and Remote data sources in repository
- Unified approach for all screens in BaseFragment
- Custom made pagination with mutable items for recycler, supports both Kotlin Coroutines and RxJava

### Custom UI
- Custom bottom navigation view with animations
- Custom loading placeholder with animation
- Custom RecyclerView grid for photo gallery (ispired by Pinterest)

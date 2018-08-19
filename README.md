# CodeChallenge-Android
This project was created as the final presentation in an Android training course provided by CWI. It was named "CodeChallenge" because its features are very similar to my [CodeChallenge-iOS](https://github.com/gobetti/CodeChallenge-iOS/) (list view, details view and search), so it ends up being the Android version of that.

The [TMDB API](https://www.themoviedb.org/documentation/api) is used.

# Key points

## Architecture

- Focus on simplicity (since the app is pretty simple)
- MVVM where LiveData observation is needed, MVP otherwise
- Simple dependencies (no Dagger, no Rx - even though I'm a big fan of Rx)
- [ServiceLocator pattern replaces Dagger](https://blog.kotlin-academy.com/dependency-injection-the-pattern-without-the-framework-33cfa9d5f312) quite well without generated code surprises
- [Single-activity app](https://android-developers.googleblog.com/2018/05/use-android-jetpack-to-accelerate-your.html?m=1) (however not yet using the [Navigation Architecture Component](https://developer.android.com/topic/libraries/architecture/navigation/))
- Opinion: [Google's Architecture Components](https://developer.android.com/topic/libraries/architecture/) and [Kotlin coroutines](https://kotlinlang.org/docs/reference/coroutines.html) might together become an Rx killer in the Android community

## Network

- Models built with a generator ([JsonToKotlin](https://github.com/wuseal/JsonToKotlinClass) plugin)
- Using [moshi with codegen](https://github.com/square/moshi#codegen) as the converter, so kotlin-reflection isn't added

## Tests
- Unit tests do not require an internet connection (locally mocked API)
- [Shared code between unit and instrumented tests](https://blog.danlew.net/2015/11/02/sharing-code-between-unit-tests-and-instrumentation-tests-on-android/)
- Instrumented tests use the locally mocked API too (TestApplication injects a TestServiceLocator)
- The architecture is prepared to receive more tests but so far only a very few

## UI

- Using [PagedList](https://developer.android.com/reference/android/arch/paging/PagedList) from arch comps to handle pagination and state for me
- ConstraintLayout with constraints changed in runtime
- Using standard [SearchView and SearchRecentSuggestionsProvider](https://developer.android.com/guide/topics/search/search-dialog)

<img src="https://github.com/gobetti/CodeChallenge-Android/blob/master/screenshots/home.png?s=100" alt="Home screenshot" width="360" height="640" />
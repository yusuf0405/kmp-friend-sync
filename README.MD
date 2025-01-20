# Friend-Sync KMP

## Overview

FriendSync KMP is a Compose-multiplatform project that uses a
custom [Ktor server](https://github.com/yusuf0405/friend-sync-server) to work as a backend and
supports Android, iOS. Web and desktop applications will be added in the future. Basically, it is an
analogue of Instagram, which will support all its functions in the future.

## Libraries

### Android Jetpack

* [Lifecycle](https://developer.android.com/topic/libraries/architecture/lifecycle) An interface
  that automatically responds to lifecycle events.

* [Navigation](https://developer.android.com/guide/navigation?gclsrc=aw.ds&gclid=Cj0KCQiA09eQBhCxARIsAAYRiymyM6hTEs0cGr5ZCXOWtLhVUwDK1O86vf8V_Uq2DWvVYNFZwPFznzAaAllMEALw_wcB)
  Navigation refers to interactions that allow users to navigate through , enter, and exit various
  parts of the content in your app. Navigation component Android Jetpack helps you navigate, from
  simple button clicks to more complex templates like application panels and navigation bar. The
  navigation component also provides a consistent and predictable user interface, adhering to an
  established set of principles.

* [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) Data related to
  the user interface that is not destroyed when the application is rotated. Easily schedule
  asynchronous tasks for optimal execution.

### Image loader

* [Coil3](https://skydoves.github.io/landscapist/coil3/) Coil3 is a highly optimized,
  pluggable Compose image loading solution.

### Multithreading

* [Coroutines](https://kotlinlang.org/docs/coroutines-overview.html) Asynchronous or non-blocking
  programming is an important part of the development landscape. When creating server-side, desktop,
  or mobile applications, it's important to provide an experience that is not only fluid from the
  user's perspective, but also scalable when needed.

### DI

* [Koin](https://insert-koin.io/docs/reference/koin-mp/kmp/)
  Koin is a pure Kotlin library and can be used in your shared Kotlin project.

### Network

* [Ktor](https://ktor.io/docs/client-create-multiplatform-application.html)
  The Ktor HTTP client can be used in multiplatform projects. In this tutorial, we'll create a
  simple Kotlin Multiplatform Mobile application, which sends a request and receives a response body
  as plain HTML text.

### Database

* [Room (Kotlin Multiplatform)](https://developer.android.com/kotlin/multiplatform/room) The Room
  persistence library provides an abstraction layer over SQLite to allow for more robust database
  access while harnessing the full power of SQLite. This page focuses on using Room in Kotlin
  Multiplatform (KMP) projects. For more information on using Room, see Save data in a local
  database using Room or our official samples.

* [DataStore (Kotlin Multiplatform)](https://developer.android.com/kotlin/multiplatform/datastore)
  The DataStore library stores data asynchronously, consistently, and transactionally, overcoming
  some of the drawbacks of SharedPreferences. This page focuses on creating DataStore in Kotlin
  Multiplatform (KMP) projects. For more information on DataStore, see the primary documentation for
  DataStore and official samples.

## Screen Shots

### Android Screenshots

[<img width="200" alt="image" src="https://github.com/yusuf0405/kmp-friend-sync/blob/master/assets/screenshots/android/screenshot_1.png">]()
[<img width="200" alt="image" src="https://github.com/yusuf0405/kmp-friend-sync/blob/master/assets/screenshots/android/screenshot_2.png">]()
[<img width="200" alt="image" src="https://github.com/yusuf0405/kmp-friend-sync/blob/master/assets/screenshots/android/screenshot_3.png">]()
[<img width="200" alt="image" src="https://github.com/yusuf0405/kmp-friend-sync/blob/master/assets/screenshots/android/screenshot_4.png">]()
[<img width="200" alt="image" src="https://github.com/yusuf0405/kmp-friend-sync/blob/master/assets/screenshots/android/screenshot_5.png">]()
[<img width="200" alt="image" src="https://github.com/yusuf0405/kmp-friend-sync/blob/master/assets/screenshots/android/screenshot_6.png">]()
[<img width="200" alt="image" src="https://github.com/yusuf0405/kmp-friend-sync/blob/master/assets/screenshots/android/screenshot_7.png">]()
[<img width="200" alt="image" src="https://github.com/yusuf0405/kmp-friend-sync/blob/master/assets/screenshots/android/screenshot_8.png">]()
[<img width="200" alt="image" src="https://github.com/yusuf0405/kmp-friend-sync/blob/master/assets/screenshots/android/screenshot_9.png">]()
[<img width="200" alt="image" src="https://github.com/yusuf0405/kmp-friend-sync/blob/master/assets/screenshots/android/screenshot_10.png">]()

### iOS Screenshots

[<img width="200" alt="image" src="https://github.com/yusuf0405/kmp-friend-sync/blob/master/assets/screenshots/ios/screenshot_1.png">]()
[<img width="200" alt="image" src="https://github.com/yusuf0405/kmp-friend-sync/blob/master/assets/screenshots/ios/screenshot_2.png">]()
[<img width="200" alt="image" src="https://github.com/yusuf0405/kmp-friend-sync/blob/master/assets/screenshots/ios/screenshot_3.png">]()
[<img width="200" alt="image" src="https://github.com/yusuf0405/kmp-friend-sync/blob/master/assets/screenshots/ios/screenshot_4.png">]()
[<img width="200" alt="image" src="https://github.com/yusuf0405/kmp-friend-sync/blob/master/assets/screenshots/ios/screenshot_5.png">]()
[<img width="200" alt="image" src="https://github.com/yusuf0405/kmp-friend-sync/blob/master/assets/screenshots/ios/screenshot_6.png">]()
[<img width="200" alt="image" src="https://github.com/yusuf0405/kmp-friend-sync/blob/master/assets/screenshots/ios/screenshot_7.png">]()
[<img width="200" alt="image" src="https://github.com/yusuf0405/kmp-friend-sync/blob/master/assets/screenshots/ios/screenshot_8.png">]()
[<img width="200" alt="image" src="https://github.com/yusuf0405/kmp-friend-sync/blob/master/assets/screenshots/ios/screenshot_9.png">]()

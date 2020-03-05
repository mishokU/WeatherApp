WeatherApp
=======================
This application was build for learning purposes.
Introdution
------------
The application uses Clean(almost) Architecture based on Model-View-ViewModel(MVVM) and Repository patterns. Full implementation you can find at [Android Jetpack official cite](https://developer.android.com/jetpack)

![Guide to app architecture](https://github.com/mishokU/LEGO-Catalog/raw/master/screenshots/guide-to-app-architecture.png)

Android Jetpack is used as an Architecture glue including but not limited to ViewModel, LiveData, Lifecycles, Navigation, Room and Data Binding and etc. See all dependences in the bottom list. 

The application does network HTTP requests via Retrofit2 at the [OpenWeatherMap](https://openweathermap.org/) , OkHttp and GSON adapters. Loaded data is saved to SQL based database Room throw repository, which serves as single source of truth and support offline mode. We have tree models in UI, local and remote interfaces, they are transformated in Transformation.map(). 

Kotlin Coroutines manage background threads with simplified code and reducing needs for callbacks.

Picasso is used for image loading via BindingAdapter and Data Binding.

A sample app contain one activity which contain tree fragments: SearchingFragment, OneCityWeatherFragment and RememberedCitiesWeatherFragment. 

Screenshots
-----------

<img src="https://sun9-35.userapi.com/c853620/v853620088/2043e2/TXIz561YJaQ.jpg" height="400" width=auto>
<img src="https://sun9-46.userapi.com/c854428/v854428088/204969/QJ2psGClzBE.jpg" height="400" width=auto>

Libraries Used
--------------

The application goal is to show case current Android Architecture state using out of box
Android tools made by Google (Android Jetpack) and 3rd party community driven libraries.

Android Jetpack is a set of components, tools and guidance to make great Android apps. They bring
together the existing Support Library and Architecture Components and arranges them into four
categories:

[Android Architectures Components](https://developer.android.com/jetpack)
[Retrofit2](https://square.github.io/retrofit/)
[Picasso](https://square.github.io/picasso/)
[RxAndroid](https://github.com/ReactiveX/RxAndroid)
[Moshi](https://github.com/square/moshi)
[Material Design](https://github.com/material-components/material-components-android/blob/master/docs/getting-started.md)

Using
-----
Type in search field english name of the city<br>
Example: Moscow, Saint Petersburg

Features
--------
1) Add start screen with your city by geolocation if you have this city in local Room database.<br>
2) Refactor architecture with updating only repo instead of update all system

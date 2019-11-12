# JokesApp

A demo app to show random jokes, with the aim of showing Clean Architecture and Clean code principles
in a MVVM setup, using RX.

There's a cache memory used to save and retrieve a custom joke. In order to show a data cache implementation.

The implementation was started from the Data layer to the View, in order to implement the whole
business logic before the view was presented.

## About the project

For simplicity, I've chosen the ICNDb free API:
http://www.icndb.com/api/

### Initial approach

The initial Clean Architecture approach was developed by me in a Bitbucket repository using:
- MVVM
- RXJava

### Goals

In order to follow the latest Android standards, the project will include the next points (progressively):
- ~~Complete the VM unit tests~~ (DONE)
- ~~LiveData, instead of Rx (outputs)~~ (DONE)
- ~~UI Tests using Espresso and Robot pattern~~ (reference [Adam McNeilly]) (DONE)
- ~~MockWebServer for testing HTTP clients~~ (DONE)
- Add `Isolation Fragment Tests` (dialog actions, VM interaction, etc). Ref: Test your app's fragments [Ref][ref_fragment]
- ~~Add Navigation Module~~ (DONE)
- Change modules for feature Module, in order to create two branches: `Feature-Modules` and `Data-Layer-Modules`
- Improve UI elements
- Add feature Detail

[ref_fragment]: https://developer.android.com/training/basics/fragments/testing
[Adam McNeilly]: https://github.com/AdamMc331

## Libraries Used

* [Rx][0] for reactive style programming (from VM to Data).
* [LiveData][1] for reactive style programming (from VM to UI). 
* [Navigation][2] for in-app navigation. 
* [Rx][3] for cache storage.
* [Dagger2][4] for dependency injection.
* [Retrofit][5] for REST api communication.  
* [Timber][6] for logging.
* [Espresso][7] for UI tests.
* [Mockito-Kotlin][8] for mocking in tests.
* [MockWebServer][9] for Instrumentation tests.
* [AndroidX Test Library][10] for providing JUnit4 and functions as `launchActivity` in UI tests,

[0]:  https://github.com/ReactiveX/RxAndroid
[1]:  https://developer.android.com/topic/libraries/architecture/livedata
[2]:  https://developer.android.com/topic/libraries/architecture/navigation/
[3]:  https://github.com/ReactiveX/RxAndroid
[4]:  https://github.com/google/dagger
[5]:  https://github.com/square/retrofit
[6]:  https://github.com/JakeWharton/timber
[7]:  https://developer.android.com/training/testing/espresso/
[8]:  https://github.com/nhaarman/mockito-kotlin
[9]:  https://github.com/square/okhttp/tree/master/mockwebserver
[10]: https://github.com/android/android-test

## Demo

![Demo](Demo-JokesApp.gif)

#  License

    Copyright 2019 Fernando Prieto Moyano

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
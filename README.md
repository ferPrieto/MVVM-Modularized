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
- JUnit

### Goals

In order to follow the latest Android standards, the project will include the next points (progressively):
- Complete the VM unit tests
- Introduce LiveData
- UI Tests

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
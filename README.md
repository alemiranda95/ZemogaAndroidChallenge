# Zemoga Android Challenge

![1](/screenshots/1.jpg)
![2](/screenshots/2.jpg)
![3](/screenshots/3.jpg)

## About
This app is part of the Zemoga employment selection process. It consists in an app that retrieves and displays posts and their information from a public REST api.

## Proposed Architecture
The architecture used was MVVM with Clean Architecture, because of the following reasons:
-	Separation of concerns between the views (activities, fragments, ...) and business logic
-	More testable and maintainable code
-	Easier to navigate

## Libraries
- [Room](https://developer.android.com/topic/libraries/architecture/room.html) (Android Architecture Components) For creating the database
- [RxKotlin](https://github.com/ReactiveX/RxKotlin) For communicating fragments in a more reactive way
- [Dagger](https://github.com/google/dagger/tree/master/java/dagger/hilt) For dependency injection
- [Retrofit](https://github.com/square/retrofit) For retrieving data from the api
- [okHTTP](https://github.com/square/okhttp) by Square

## Extras
The app also supports night theme!

## Installation
An apk is available to download in the /apk folder
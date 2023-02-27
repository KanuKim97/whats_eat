# What's Eat? Application
 ### Android Studio Version 
  - Electric Eel 2022.1.1
 ### build.gradle (Project) Dependencies
  - tools.build:gradle: 7.4.1
  - kotlin-gradle-plugin: 1.8.0
  - gms:goole-services: 4.3.15
  - mapsplatform.secrets-gradle-plugin: 2.0.1
 ### build.gradle (Project) Plugin
  - Dagger-Hilt version 2.44.2
  
## Project Description 
 - This application is introduce near by food restaurant at your current location 
 
## APIs
 ### Firebase Authentication
  - Implement with Firebase Authentication when user SignUp and login 
 ### Firebase Realtime Database 
  - Store userdata (user e-mail, user fullname, user nickname, user collections)
 ### Google Maps Api
  - tracking user's current location and display nearby food resturant
 ### Place Api
  - Found near by food restaurant at user's current location 
  - The result of the API calls is json document
  
## Sdk Build Version
 - minSdkVersion : 25
 - targetSdkVersion : 33
 - compileSdkVersion : 33

## Test Enviroment
 - Android Virtual Device: Pixel 6 API 33 (Android 13 Tiramisu)

## Dependencies
```
 // Kotlin Coroutine
 org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4
 
 // AndroidX
 androidx.constraintlayout:constraintlayout:2.1.4
 androidx.navigation:navigation-fragment-ktx:2.5.3
 androidx.navigation:navigation-ui-ktx:2.5.3
 androidx.legacy:legacy-support-v4:1.0.0
 androidx.preference:preference-ktx:1.2.0
 
 // Android Jetpack ViewModel, LiveData 
 androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.0-beta01
 androidx.lifecycle:lifecycle-livedata-ktx:2.6.0-beta01
 
 // Dagger-Hilt DI(Dependency Injection) Tool
 com.google.dagger:hilt-android:2.44.2
 com.google.dagger:hilt-android-compiler:2.44.2 (kapt)
 
 // Android MultiDex
 com.android.support:multidex:1.0.3
 
 // Android Test Unit
 junit:junit:4.13.2
 com.android.support.test:runner:1.0.2
 com.android.support.test.espresso:espresso-core:3.0.2
 
 // Google Android GMS Service Maps SDK, Location
 com.google.android.gms:play-services-maps:18.1.0
 com.google.android.gms:play-services-location:21.0.1
 
 // FireBase Auth, RealTime DataBase
 platform('com.google.firebase:firebase-bom:28.4.0')
 com.google.firebase:firebase-analytics-ktx:21.2.0
 com.google.firebase:firebase-core:21.1.1
 com.google.firebase:firebase-auth:21.1.0
 com.firebaseui:firebase-ui-auth:8.0.1
 com.google.firebase:firebase-auth-ktx:21.1.0
 com.google.firebase:firebase-database:20.1.0
 
 // Retrofit2
 com.squareup.retrofit2:retrofit:2.9.0
 com.squareup.retrofit2:converter-gson:2.9.0
 
 // Glide
 com.github.bumptech.glide:glide:4.13.2
 
 // Easy Permission
 pub.devrel:easypermissions:3.0.0
```

## Preference
```
 Android Devs Maps Api Documentation (location)
 https://developer.android.com/training/location/request-updates?hl=ko
 
 JSON to Csharp (API Result Json Parsing to Kotlin)
 https://json2csharp.com/
```
 


# What's Eat? Application
 Android Studio Version : ArcticFox 2020.3.1 Patch 4
 
 Language : Kotlin
 
## Project Description 
 - This application is introduce near by food restaurant at your current location 
 
## Project Snapshot Example

### near by Place -> User's current location 
![Maps Location](https://user-images.githubusercontent.com/74421057/143890681-21abab98-976c-44d6-811a-b1fef5c9bfb2.png)

### marker click event -> show Place Detail
![Place Detail](https://user-images.githubusercontent.com/74421057/144457716-072d53cf-5a3d-4867-8d43-49d5c9d16b43.png)

### Collection Fragment -> RecyclerView 
![collection](https://user-images.githubusercontent.com/74421057/145036116-3a52fbc0-a7db-49ea-805e-8065a95e91c6.png)

### Application main Fuction 
 1. introduce near by food restaurant 
 2. bookmark (Your food restaurant Collection)

### Application Progress
 - Sign Up, Sign Out, Log In Fuction
 - Reset Password function when Users are forgot their password 
 - Profile (Working on it) 
 - Collection (Not yet)
 - Maps 

### FireBase RealTime DataBase Design (noSql DataBase)

 ![rtdb](https://user-images.githubusercontent.com/74421057/145072849-9cac85e6-1f86-4d23-aa16-e6845399b550.png)

## APIs
 ### Firebase Authentication
  - Implement with Firebase Authentication when user SignUp and login 
 ### Firebase Realtime Database 
  - Store User Data (Email, UserName, User's FullName)
 ### Google Maps Api
  - tracking user's current location and check nearby food resturant
 
 ### Place Api
  - Found place near by user's current location 
  - Return Value is Json Document, So I used Json2csharp for parsing json document 

## Sdk Build Version
 - minSdkVersion : 25
 - targetSdkVersion : 31
 - compileSdkVersion : 31

## Test Enviroment
 - Android Virtual Device 
 - Pixel 3a (Android Version 11.0 & API 30)
 
 ### Espresso UI Test (Expected Soon) Alpha version
 
## Dependency
```
 //firebase
 com.google.firebase:firebase-bom:28.4.0
 com.google.firebase:firebase-analytics-ktx
 com.google.firebase:firebase-core:17.2.1
 com.google.firebase:firebase-auth:19.1.0
 com.firebaseui:firebase-ui-auth:4.2.1
 
 //glide
 com.github.bumptech.glide:glide:4.11.0
 
 //multidex
 com.android.support:multidex:1.0.3
 
 //android material 
 com.google.android.material:material:1.2.1
 
 //preference 
 androidx.preference:preference-ktx:1.1.1
 
 //location service
 com.google.android.gms:play-services-location:18.0.0 
 
 //retrofit2
 com.squareup.retrofit2:retrofit:2.3.0
 com.squareup.retrofit2:converter-gson:2.3.0
 
 //Easy Permission 
 pub.devrel:easypermissions:1.3.+
```

## Preference
```
 Android Devs Maps Api Documentation (location)
 https://developer.android.com/training/location/request-updates?hl=ko
 
 Retrofit Kr Document 
 https://devflow.github.io/retrofit-kr/
 
 JSON to Csharp (API Result Json Parsing to Kotlin)
 https://json2csharp.com/
```
 


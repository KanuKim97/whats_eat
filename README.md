![cover](https://github.com/KanuKim97/whats_eat/assets/74421057/ee505a51-86b5-4c30-8b57-0784ba727d37)
<div align="center">
<img src="https://img.shields.io/badge/Android_sdk_version-25%2B-3DDC84?style=flat&logo=android&logoColor=white"/>
<img src="https://img.shields.io/badge/Gradle_version-8.2.0-02303A?style=flat&logo=gradle&logoColor=white"/>
<br>
<img src="https://img.shields.io/badge/Kotlin_version-1.9.10-7F52FF?style=flat&logo=Kotlin&logoColor=white"/>
<img src="https://img.shields.io/badge/Kotlin-Flow_API-7F52FF?style=flat&logo=Kotlin&logoColor=white"/>
<img src="https://img.shields.io/badge/Annotation_Processor-Kotlin_ksp-7F52FF?style=flat"/>
<br>
<img src="https://img.shields.io/badge/Dagger--Hilt_version-2.49-3DDC84?style=flat"/>
<img src="https://img.shields.io/badge/Jetpack_Compose_version-1.5.3-4285F4?style=flat&logo=jetpackcompose&logoColor=white"/>
</div>

## Project Description
This application is introduced nearby food restaurant at your current location

## Project hierarchy
```
 :app (Application Module)
 :buildSrc (Dependencies Management Module)
 :core
  |- :common 
  |- :data
  |- :database
  |- :designsystem
  |- :domain
  |- :model
  |- :network
  |_ :ui
 :feature
  |- :collection 
  |- :detail
  |_ :home
```

## Place Api
 - find nearby food restaurant at user's current location
 - The result of the API calls is json document
  
## Sdk Build Version
 - minSdkVersion : 25
 - targetSdkVersion : 34
 - compileSdkVersion : 34

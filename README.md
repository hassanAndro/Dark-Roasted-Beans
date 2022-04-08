## Tech stack & Open-source libraries
- Minimum SDK level 21
- Kotlin
- Hilt for dependency injection.
- JetPack
  - Compose - A modern toolkit for building native Android UI.
  - Lifecycle - dispose observing data when lifecycle state changes.
  - ViewModel - UI related data holder, lifecycle aware.
  - Room Persistence - construct database.
  - App Startup - Provides a straightforward, performant way to initialize components at application startup.
- Architecture
  - MVVM Architecture (Declarative View - ViewModel - Model)
  - Repository pattern
- Material Design & Animations
- Accompanist
- Retrofit2 & OkHttp3
- API Error handling.
- Network connectivity monitoring.
- Mock and JUnit4 Tests.
- Dark and Light theme support.

## Missing:
- Expandable views for Coffee Extra with database for Subselections
- Overview compose view with database table for selected items by the user to load the overview screen.

## Can be polished:
- Separate "viewmodels" for size, extras, overview
- NetworkUtils class functionality can be divided into small classes 
  e.g compose class, observable, sealed classes. Also just to show the "Handling" toast will appear when app start
  as "Network connection available!"
  


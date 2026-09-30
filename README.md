# Campus Lost & Found

A complete, polished Android application for a university campus to report and find lost items. Built with modern Android development practices, it features a clean UI, local persistence, dependency injection, and clean architecture.

### Features
* Report lost items
* Report found items
* Search and filtering
* Item details
* Local persistence
* Image support
* Dark mode
* Notifications

### Tech Stack
* Kotlin
* Jetpack Compose
* Material 3
* MVVM
* Room
* Hilt
* Coroutines
* StateFlow
* Navigation Compose
* Coil
* DataStore

### Architecture
The app follows the MVVM and Clean Architecture patterns:
- **Data Layer**: Room Database and DAOs for local storage. Repositories coordinate data operations.
- **Domain Layer**: Contains Repository interfaces and core Models.
- **UI Layer**: Jetpack Compose screens, ViewModels for state management (using StateFlow), and Navigation Compose for routing.
- **DI**: Hilt is used across the application for dependency injection.

### Screenshots
*(Add screenshots here)*

### Project Structure
- `app/src/main/java/com/example/campuslostfound/data`: Database, DAOs, Entities, and Repositories.
- `app/src/main/java/com/example/campuslostfound/di`: Hilt Dependency Injection modules.
- `app/src/main/java/com/example/campuslostfound/domain`: Domain interfaces.
- `app/src/main/java/com/example/campuslostfound/ui`: Compose screens, navigation setup, and theme.

### How to Run
1. Clone the repository.
2. Open the project in Android Studio.
3. Sync Gradle.
4. Build and run on an emulator or physical Android device running API 24 or higher.

### Future Improvements
* Firebase cloud synchronization
* Authentication
* Campus-wide backend
* Real-time notifications
* Admin moderation

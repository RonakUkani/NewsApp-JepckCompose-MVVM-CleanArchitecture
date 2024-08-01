# News App

This News App is built using Jetpack Compose, Koin, MVVM, and Clean Architecture principles.

## Libraries Used

### Android Jetpack Components

- **androidx-core-ktx**: Extensions for Android framework APIs, providing concise and idiomatic
  Kotlin code.
- **androidx-lifecycle-runtime-ktx**: Kotlin extensions for lifecycle-aware components.
- **androidx-activity-compose**: Integration between Android Activity and Jetpack Compose.
- **androidx-ui, androidx-ui-graphics, androidx-ui-tooling-preview**: Core UI components for
  building the user interface using Jetpack Compose.
- **androidx-material3**: Material Design components for Jetpack Compose.
- **androidx-navigation-compose**: Jetpack Navigation for handling in-app navigation.

### Dependency Injection

- **koin-android**: Koin for Android, providing dependency injection support.
- **koin-androidx-compose**: Koin integration with Jetpack Compose.

### Networking

- **retrofit**: HTTP client for parse API.
- **retrofit-gson**: JSON converter for Retrofit using Gson.
- **retrofit-coroutines-adapter**: Retrofit adapter for Kotlin coroutines.
- **okhttp3-logging-interceptor**: HTTP logging interceptor for OkHttp.

### Image Loading

- **coil**: Image loading library for Compose.

### LiveData & Coroutines

- **androidx-runtime-livedata**: LiveData integration with Jetpack Compose.
- **kotlinx-coroutines-test**: Testing utilities for Kotlin Coroutines.

### Testing

- **junit**: JUnit for testing.
- **mockk**: Mocking library for Kotlin.
- **arch-core-testing**: Testing library for Architecture Components.
- **test-turbine**: A testing utility for Kotlin Flow.

## Architecture

The application follows the **Clean Architecture** principles:

- **Domain Layer**: Contains business logic and domain models. It is independent of any external
  libraries and frameworks. The `NewsUseCase` interface and its implementation (`NewsUseCaseImpl`)
  reside here.

- **Data Layer**: Handles data sources. The `NewsRepository` and data models (`NewsResponseDTO`) are
  part of this layer. Retrofit and Gson are used for network operations and JSON parsing.

- **Presentation Layer**: Responsible for UI components and view logic. It includes the
  ViewModel (`NewsViewModel`) and UI state management. Jetpack Compose is used for building the UI.

- **DI Layer**: Uses Koin for dependency injection. Modules are defined to provide dependencies such
  as Retrofit, OkHttp, ViewModel, and UseCases.

## Design Decisions

1. **Jetpack Compose**: Chosen for building the UI due to its declarative approach.

2. **Koin for DI**: Koin is lightweight and easy to set up, used for dependency injection in this
   project.

3. **MVVM Pattern**: The Model-View-ViewModel pattern is used to separate the UI logic from business
   logic, enhancing testability and maintainability.

4. **Coroutines**: Kotlin Coroutines are used for asynchronous operations, providing a simpler and
   more efficient way to handle background tasks.

5. **Retrofit & OkHttp**: Retrofit is used for network requests due to its ease of use and
   integration with Gson for JSON parsing. OkHttp's logging interceptor helps in debugging network
   requests.

## Error Handling

The app uses a `NetworkResult` wrapper to handle API responses. This wrapper includes different
states like `Success`, `Error`.

## Testing

- **Unit Tests**: MockK is used for mocking dependencies and testing business logic. The testing
  framework also includes JUnit and Turbine for flow testing.

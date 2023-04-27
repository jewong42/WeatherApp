# WeatherApp

<div align="center">
  <video src="https://user-images.githubusercontent.com/38672015/234989256-aa9f26a3-6752-4440-b1d3-ec8c9b77367a.mov" width="400" />
</div>


A weather Android app written in Kotlin that provides users with weather details for a specific location. The app features two ways to find weather details: by getting the user's location or by allowing the user to search for a location. The last search made by the user is stored via shared preferences and will be automatically loaded on app relaunch, unless the user has location permissions enabled, in which case the user's current location will be used.

## Features
- Get weather details by user's location
- Search for weather details of a specific location


## Architecture
The Weather App follows a clean architecture approach and utilizes the MVVM (Model-View-ViewModel) design pattern. This separation of concerns allows for better maintainability, testability, and extensibility of the codebase. The app leverages the following libraries and tools to support its architecture:

- Hilt: A dependency injection framework for Android that simplifies the management of dependencies and promotes modular and testable code.
- Retrofit: A type-safe HTTP client for Android that makes it easy to consume RESTful APIs and handle network operations.
- Compose: A modern declarative UI toolkit for building native Android user interfaces. It offers a more flexible and efficient way to create UI components compared to traditional View-based frameworks.

## Configuration
WeatherApp uses OpenWeatherMap to fetch weather data. The API key is currently hardcoded in the codebase for convenience. However, it is generally recommended to store sensitive information like API keys securely. In a production-ready app, it's advised to use a more secure approach, such as storing the API key in a separate configuration file or using a service like BuildConfig.

API key will be deactivated on Friday, May 5, 2023.

## Future Enhancements

Although the current implementation of the Weather App provides the core functionality, there are several areas that can be further improved or expanded upon:

- Implement testing with Hilt to ensure the reliability and stability of the app. (Will be added, time permitting)
- Add differentiated error handling and informative error messages to enhance the user experience.
- Incorporate Java to better meet requirements.
- Incorporate RxJava for reactive programming if future use cases demand it.
- Expand navigation support to accommodate multiple screens or additional features, such as city search.
    





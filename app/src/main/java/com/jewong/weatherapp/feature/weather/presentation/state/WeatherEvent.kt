package com.jewong.weatherapp.feature.weather.presentation.state

sealed class WeatherEvent {
    object Loading : WeatherEvent()
    object SearchError : WeatherEvent()
    object LocationError : WeatherEvent()
}

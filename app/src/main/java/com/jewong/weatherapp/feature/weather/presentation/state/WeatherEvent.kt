package com.jewong.weatherapp.feature.weather.presentation.state

sealed class WeatherEvent {
    object Submitted : WeatherEvent()
    object SearchError : WeatherEvent()
}

package com.jewong.weatherapp.feature.weather.presentation.state

import com.jewong.weatherapp.feature.weather.data.network.model.WeatherData

data class WeatherState(
    var query: String = "",
    var weatherData: WeatherData? = null,
    var isLoading: Boolean = false,
)

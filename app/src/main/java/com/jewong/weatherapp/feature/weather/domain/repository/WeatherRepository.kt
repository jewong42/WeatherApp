package com.jewong.weatherapp.feature.weather.domain.repository

import com.jewong.weatherapp.feature.weather.data.network.model.Coord
import com.jewong.weatherapp.feature.weather.data.network.model.WeatherData

interface WeatherRepository {

    suspend fun getWeatherByCityName(cityName: String): WeatherData

    suspend fun getWeatherByCoord(coord: Coord): WeatherData

}
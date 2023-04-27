package com.jewong.weatherapp.feature.weather.data.repository

import com.jewong.weatherapp.feature.weather.data.network.OpenWeatherApi
import com.jewong.weatherapp.feature.weather.data.network.model.Coord
import com.jewong.weatherapp.feature.weather.data.network.model.WeatherData
import com.jewong.weatherapp.feature.weather.domain.repository.WeatherRepository
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val api: OpenWeatherApi
) : WeatherRepository {

    override suspend fun getWeatherByCityName(cityName: String): WeatherData {
        return api.getWeatherByCityName(cityName)
    }

    override suspend fun getWeatherByCoord(coord: Coord): WeatherData {
        return api.getWeatherByCoord(coord.lat, coord.lon)
    }

}
package com.jewong.weatherapp.feature.weather.data.network

import com.jewong.weatherapp.feature.weather.data.network.model.WeatherData
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherApi {

    @GET("2.5/weather?")
    suspend fun getWeatherByCityName(
        @Query("q") cityName: String,
        @Query("units") units: String = "imperial"
    ): WeatherData


    @GET("2.5/weather?")
    suspend fun getWeatherByCoord(
        @Query("lat") lat: Double,
        @Query("lon") long: Double,
        @Query("units") units: String = "imperial"
    ): WeatherData

}
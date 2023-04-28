package com.jewong.weatherapp.feature.weather.data.network.model

data class WeatherData(
    val coord: Coord,
    val weather: List<Weather>,
    val base: String,
    val main: Main,
    val visibility: Int,
    val wind: Wind,
    val rain: Rain?,
    val clouds: Clouds,
    val dt: Long,
    val sys: Sys,
    val timezone: Int,
    val id: Int,
    val name: String,
    val cod: Int
)

fun WeatherData.isNight(): Boolean {
    return this.weather.first().icon.last() == 'n'
}
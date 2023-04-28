package com.jewong.weatherapp.factories

import com.jewong.weatherapp.feature.weather.data.network.model.*

object WeatherDataFactory {

    fun createSampleResponse(): WeatherData {
        return WeatherData(
            Coord(10.99, 44.34),
            listOf(Weather(501, "Rain", "moderate rain", "10d")),
            "stations",
            Main(298.48, 298.74, 297.56, 300.05, 1015, 64, 1015, 933),
            10000,
            Wind(0.62, 349, 1.18),
            Rain(3.16),
            Clouds(100),
            1661870592,
            Sys(2, 2075663, "IT", 1661834187, 1661882248),
            7200,
            3163858,
            "Zocca",
            200
        )
    }

    fun createResponseWithCoord(coord: Coord): WeatherData {
        return createSampleResponse().copy(coord = coord)
    }

}

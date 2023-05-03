package com.jewong.weatherapp.feature.weather.domain.provider

import com.jewong.weatherapp.feature.weather.data.network.model.Coord

interface PreferencesProvider{

    fun getLastSearchedCoord(): Coord?

    fun setLastSearchedCoord(coord: Coord)

}
package com.jewong.weatherapp.feature.weather.domain.provider

import android.location.Location

interface LocationProvider{

    suspend fun getLastKnownLocation(): Location?

}
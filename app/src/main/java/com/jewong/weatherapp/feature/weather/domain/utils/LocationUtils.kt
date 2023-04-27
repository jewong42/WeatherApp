package com.jewong.weatherapp.feature.weather.domain.utils

import android.location.Location

interface LocationUtils {

    suspend fun getLastKnownLocation(): Location?

}
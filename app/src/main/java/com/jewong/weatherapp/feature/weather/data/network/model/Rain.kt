package com.jewong.weatherapp.feature.weather.data.network.model

import com.google.gson.annotations.SerializedName

data class Rain(
    @SerializedName("1h") val oneHour: Double
)
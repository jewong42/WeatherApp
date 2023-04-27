package com.jewong.weatherapp.feature.weather.domain.use_case

import android.content.SharedPreferences
import com.google.gson.Gson
import com.jewong.weatherapp.feature.weather.data.network.model.Coord
import com.jewong.weatherapp.feature.weather.shared.Constants.LAST_QUERY_KEY
import javax.inject.Inject

class SetLastSearchedUseCase @Inject constructor(
    private val sharedPreferences: SharedPreferences,
) {

    operator fun invoke(coord: Coord) {
        sharedPreferences.edit().putString(LAST_QUERY_KEY, Gson().toJson(coord)).apply()
    }

}
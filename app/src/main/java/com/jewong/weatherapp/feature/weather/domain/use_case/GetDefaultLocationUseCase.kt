package com.jewong.weatherapp.feature.weather.domain.use_case

import android.content.SharedPreferences
import com.google.gson.Gson
import com.jewong.weatherapp.feature.weather.data.network.model.Coord
import com.jewong.weatherapp.feature.weather.domain.utils.LocationUtils
import com.jewong.weatherapp.feature.weather.shared.Constants.LAST_QUERY_KEY
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetDefaultLocationUseCase @Inject constructor(
    private val locationUtils: LocationUtils,
    private val sharedPreferences: SharedPreferences,
) {

    operator fun invoke(): Flow<Coord?> {
        return flow {
            locationUtils.getLastKnownLocation()?.let { location ->
                emit(Coord(location.longitude, location.latitude))
            } ?: sharedPreferences.getString(LAST_QUERY_KEY, null)?.let { string ->
                emit(Gson().fromJson(string, Coord::class.java))
            } ?: emit(null)
        }
    }

}
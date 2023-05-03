package com.jewong.weatherapp.feature.weather.data.provider

import android.content.SharedPreferences
import com.google.gson.Gson
import com.jewong.weatherapp.feature.weather.data.network.model.Coord
import com.jewong.weatherapp.feature.weather.domain.provider.PreferencesProvider
import com.jewong.weatherapp.feature.weather.shared.Constants.LAST_QUERY_KEY

class PreferencesProviderImpl(
    private val sharedPreferences: SharedPreferences
) : PreferencesProvider {

    override fun getLastSearchedCoord(): Coord? {
        val string = sharedPreferences.getString(LAST_QUERY_KEY, null)
        return if (string == null) {
            null
        } else {
            Gson().fromJson(string, Coord::class.java)
        }
    }

    override fun setLastSearchedCoord(coord: Coord) {
        sharedPreferences.edit().putString(LAST_QUERY_KEY, Gson().toJson(coord)).apply()
    }

}
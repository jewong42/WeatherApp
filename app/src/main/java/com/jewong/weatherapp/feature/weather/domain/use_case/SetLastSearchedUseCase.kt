package com.jewong.weatherapp.feature.weather.domain.use_case

import com.jewong.weatherapp.feature.weather.data.network.model.Coord
import com.jewong.weatherapp.feature.weather.domain.provider.PreferencesProvider
import javax.inject.Inject

class SetLastSearchedUseCase @Inject constructor(
    private val preferencesProvider: PreferencesProvider,
) {

    operator fun invoke(coord: Coord) {
        preferencesProvider.setLastSearchedCoord(coord)
    }

}
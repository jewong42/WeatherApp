package com.jewong.weatherapp.feature.weather.domain.use_case

import com.jewong.weatherapp.feature.weather.data.network.model.Coord
import com.jewong.weatherapp.feature.weather.domain.provider.LocationProvider
import com.jewong.weatherapp.feature.weather.domain.provider.PreferencesProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetDefaultLocationUseCase @Inject constructor(
    private val locationProvider: LocationProvider,
    private val preferencesProvider: PreferencesProvider,
) {

    operator fun invoke(): Flow<Coord?> {
        return flow {
            locationProvider.getLastKnownLocation()?.let { location ->
                emit(Coord(location.longitude, location.latitude))
            } ?: preferencesProvider.getLastSearchedCoord()?.let { coord ->
                emit(Coord(coord.lon, coord.lat))
            } ?: emit(null)
        }
    }

}
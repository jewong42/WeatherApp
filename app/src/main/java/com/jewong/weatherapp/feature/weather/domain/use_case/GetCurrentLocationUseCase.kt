package com.jewong.weatherapp.feature.weather.domain.use_case

import com.jewong.weatherapp.feature.weather.data.network.model.Coord
import com.jewong.weatherapp.feature.weather.domain.provider.LocationProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetCurrentLocationUseCase @Inject constructor(
    private val locationProvider: LocationProvider
) {

    operator fun invoke(): Flow<Coord?> {
        return flow {
            locationProvider.getLastKnownLocation()?.let { location ->
                emit(Coord(location.longitude, location.latitude))
            } ?: emit(null)
        }
    }

}
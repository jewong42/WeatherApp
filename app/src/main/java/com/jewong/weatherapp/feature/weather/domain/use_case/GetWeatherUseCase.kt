package com.jewong.weatherapp.feature.weather.domain.use_case


import com.jewong.weatherapp.feature.weather.data.network.model.Coord
import com.jewong.weatherapp.feature.weather.data.network.model.WeatherData
import com.jewong.weatherapp.feature.weather.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetWeatherUseCase @Inject constructor(
    private val repository: WeatherRepository
) {

    operator fun invoke(cityName: String): Flow<WeatherData?> {
        return flow {
            try {
                val weatherData = repository.getWeatherByCityName(cityName)
                emit(weatherData)
            } catch (e: HttpException) {
                emit(null)
            } catch (e: IOException) {
                emit(null)
            }
        }
    }

    operator fun invoke(coord: Coord): Flow<WeatherData?> {
        return flow {
            try {
                val weatherData = repository.getWeatherByCoord(coord)
                emit(weatherData)
            } catch (e: HttpException) {
                emit(null)
            } catch (e: IOException) {
                emit(null)
            }
        }
    }

}
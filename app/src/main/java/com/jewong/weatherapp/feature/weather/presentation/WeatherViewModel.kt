package com.jewong.weatherapp.feature.weather.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jewong.weatherapp.feature.weather.data.network.model.Coord
import com.jewong.weatherapp.feature.weather.data.network.model.WeatherData
import com.jewong.weatherapp.feature.weather.data.network.model.isNight
import com.jewong.weatherapp.feature.weather.domain.use_case.GetCurrentLocationUseCase
import com.jewong.weatherapp.feature.weather.domain.use_case.GetDefaultLocationUseCase
import com.jewong.weatherapp.feature.weather.domain.use_case.GetWeatherUseCase
import com.jewong.weatherapp.feature.weather.domain.use_case.SetLastSearchedUseCase
import com.jewong.weatherapp.feature.weather.presentation.state.WeatherEvent
import com.jewong.weatherapp.feature.weather.presentation.state.WeatherState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val getWeatherUseCase: GetWeatherUseCase,
    private val getDefaultLocationUseCase: GetDefaultLocationUseCase,
    private val getCurrentLocationUseCase: GetCurrentLocationUseCase,
    private val setLastSearchedUseCase: SetLastSearchedUseCase,
) : ViewModel() {

    private val _state = mutableStateOf(WeatherState())
    val state: State<WeatherState> = _state

    private val _eventFlow = MutableSharedFlow<WeatherEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        setDefaultWeather()
    }

    fun setDefaultWeather() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            getDefaultLocationUseCase.invoke().collect { coord ->
                if (coord != null) {
                    setWeather(coord)
                } else {
                    _state.value = _state.value.copy(isLoading = false)
                }
            }
        }
    }

    fun setCurrentLocationWeather() {
        viewModelScope.launch {
            getCurrentLocationUseCase.invoke().collect { coord ->
                if (coord != null) {
                    setWeather(coord)
                } else {
                    invokeLocationErrorEvent()
                }
            }
        }
    }

    private fun setWeather(coord: Coord) {
        viewModelScope.launch {
            invokeLoadingEvent()
            getWeatherUseCase.invoke(coord).collect { weatherData ->
                if (weatherData != null) {
                    setWeatherData(weatherData, false)
                } else {
                    invokeSearchErrorEvent()
                }
            }
        }
    }

    fun setWeather() {
        viewModelScope.launch {
            val query = _state.value.query
            invokeLoadingEvent()
            getWeatherUseCase.invoke(query).collect { weatherData ->
                if (weatherData != null) {
                    setWeatherData(weatherData, true)
                } else {
                    invokeSearchErrorEvent()
                }
            }
        }
    }

    fun setQuery(query: String) {
        _state.value = _state.value.copy(query = query)
    }

    private fun setWeatherData(weatherData: WeatherData, setLastSearched: Boolean) {
        if (setLastSearched) setLastSearchedUseCase(weatherData.coord)
        _state.value = _state.value.copy(
            weatherData = weatherData,
            isLoading = false,
            isDarkMode = weatherData.isNight()
        )
    }

    private suspend fun invokeLoadingEvent() {
        _state.value = _state.value.copy(isLoading = true, query = "")
        _eventFlow.emit(WeatherEvent.Loading)
    }

    private suspend fun invokeSearchErrorEvent() {
        _state.value = _state.value.copy(isLoading = false)
        _eventFlow.emit(WeatherEvent.SearchError)
    }

    private suspend fun invokeLocationErrorEvent() {
        _eventFlow.emit(WeatherEvent.LocationError)
    }
}
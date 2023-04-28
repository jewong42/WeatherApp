package com.jewong.weatherapp.feature.weather.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jewong.weatherapp.feature.weather.data.network.model.Coord
import com.jewong.weatherapp.feature.weather.data.network.model.WeatherData
import com.jewong.weatherapp.feature.weather.data.network.model.isNight
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
    private val setLastSearchedUseCase: SetLastSearchedUseCase,
) : ViewModel() {

    private val _state = mutableStateOf(WeatherState())
    val state: State<WeatherState> = _state

    private val _eventFlow = MutableSharedFlow<WeatherEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        setDefaultLocation()
    }

    fun setDefaultLocation() {
        invokeSubmittedEvent()
        viewModelScope.launch {
            getDefaultLocationUseCase.invoke().collect { coord ->
                if (coord != null) {
                    getDefaultWeather(coord)
                } else {
                    _state.value = _state.value.copy(isLoading = false)
                }
            }
        }
    }

    private fun getDefaultWeather(coord: Coord) {
        invokeSubmittedEvent()
        viewModelScope.launch {
            getWeatherUseCase.invoke(coord).collect { weatherData ->
                if (weatherData != null) {
                    updateWeatherData(weatherData, false)
                } else {
                    invokeSearchErrorEvent()
                }
            }
        }
    }

    fun getWeather() {
        val query = _state.value.query
        invokeSubmittedEvent()
        viewModelScope.launch {
            getWeatherUseCase.invoke(query).collect { weatherData ->
                if (weatherData != null) {
                    updateWeatherData(weatherData, true)
                } else {
                    invokeSearchErrorEvent()
                }
            }
        }
    }

    fun updateQuery(query: String) {
        _state.value = _state.value.copy(query = query)
    }

    private fun updateWeatherData(weatherData: WeatherData, setLastSearched: Boolean) {
        if (setLastSearched) setLastSearchedUseCase(weatherData.coord)
        _state.value = _state.value.copy(
            weatherData = weatherData,
            isLoading = false,
            isDarkMode = weatherData.isNight()
        )
    }

    private fun invokeSubmittedEvent() {
        _state.value = _state.value.copy(isLoading = true, query = "")
        viewModelScope.launch { _eventFlow.emit(WeatherEvent.Submitted) }
    }

    private suspend fun invokeSearchErrorEvent() {
        _state.value = _state.value.copy(isLoading = false)
        _eventFlow.emit(WeatherEvent.SearchError)
    }
}
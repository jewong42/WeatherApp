package com.jewong.weatherapp.feature.weather.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jewong.weatherapp.feature.weather.data.network.model.Coord
import com.jewong.weatherapp.feature.weather.data.network.model.WeatherData
import com.jewong.weatherapp.feature.weather.domain.use_case.GetDefaultLocationUseCase
import com.jewong.weatherapp.feature.weather.domain.use_case.GetWeatherUseCase
import com.jewong.weatherapp.feature.weather.domain.use_case.SetLastSearchedUseCase
import com.jewong.weatherapp.feature.weather.presentation.state.WeatherEvent
import com.jewong.weatherapp.feature.weather.presentation.state.WeatherState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
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
        getDefaultLocationUseCase.invoke().onEach { coord ->
            if (coord != null) getDefaultWeather(coord)
            else _state.value = _state.value.copy(isLoading = false)
        }.launchIn(viewModelScope)
    }

    private fun getDefaultWeather(coord: Coord) {
        invokeSubmittedEvent()
        getWeatherUseCase.invoke(coord).onEach { weatherData ->
            if (weatherData != null) updateWeatherData(weatherData, false)
            else invokeSearchErrorEvent()
        }.launchIn(viewModelScope)
    }

    fun getWeather() {
        val query = _state.value.query
        invokeSubmittedEvent()
        getWeatherUseCase.invoke(query).onEach { weatherData ->
            if (weatherData != null) updateWeatherData(weatherData, true)
            else invokeSearchErrorEvent()
        }.launchIn(viewModelScope)
    }

    fun updateQuery(query: String) {
        _state.value = _state.value.copy(query = query)
    }

    private fun updateWeatherData(weatherData: WeatherData, setLastSearched: Boolean) {
        if (setLastSearched) setLastSearchedUseCase(weatherData.coord)
        _state.value = _state.value.copy(weatherData = weatherData, isLoading = false)
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
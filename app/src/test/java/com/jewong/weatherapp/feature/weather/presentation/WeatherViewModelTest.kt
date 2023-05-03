package com.jewong.weatherapp.feature.weather.presentation

import com.jewong.weatherapp.factory.WeatherDataFactory
import com.jewong.weatherapp.feature.weather.data.network.model.Coord
import com.jewong.weatherapp.feature.weather.domain.use_case.GetCurrentLocationUseCase
import com.jewong.weatherapp.feature.weather.domain.use_case.GetDefaultLocationUseCase
import com.jewong.weatherapp.feature.weather.domain.use_case.GetWeatherUseCase
import com.jewong.weatherapp.feature.weather.domain.use_case.SetLastSearchedUseCase
import com.jewong.weatherapp.rule.MainDispatcherRule
import com.jewong.weatherapp.feature.weather.presentation.state.WeatherEvent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import java.lang.reflect.Field

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class WeatherViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    private lateinit var eventFlow: MutableSharedFlow<WeatherEvent>

    @Mock
    private lateinit var getWeatherUseCase: GetWeatherUseCase

    @Mock
    private lateinit var getDefaultLocationUseCase: GetDefaultLocationUseCase

    @Mock
    private lateinit var getCurrentLocationUseCase: GetCurrentLocationUseCase

    @Mock
    private lateinit var setLastSearchedUseCase: SetLastSearchedUseCase

    private lateinit var weatherViewModel: WeatherViewModel

    @Before
    fun setup() {
        weatherViewModel = WeatherViewModel(
            getWeatherUseCase,
            getDefaultLocationUseCase,
            getCurrentLocationUseCase,
            setLastSearchedUseCase
        )
        val field: Field = weatherViewModel.javaClass.getDeclaredField("_eventFlow")
        field.isAccessible = true
        field.set(weatherViewModel, eventFlow)
    }

    @Test
    fun `setDefaultLocation updates state when location is available`() = runTest {
        val coordinate = Coord(-122.41, 37.77)
        val weatherData = WeatherDataFactory.createResponseWithCoord(coordinate)

        `when`(getDefaultLocationUseCase.invoke()).thenReturn(flowOf(coordinate))
        `when`(getWeatherUseCase.invoke(coordinate)).thenReturn(flowOf(weatherData))

        weatherViewModel.setDefaultWeather()
        advanceUntilIdle()

        assertEquals(weatherData, weatherViewModel.state.value.weatherData)
    }

    @Test
    fun `getWeather updates state when query is valid`() = runTest {
        val query = "San Francisco"
        val weatherData = WeatherDataFactory.createSampleResponse()

        `when`(getWeatherUseCase.invoke(query)).thenReturn(flowOf(weatherData))

        weatherViewModel.setQuery(query)
        weatherViewModel.setWeather()
        advanceUntilIdle()

        assertEquals(weatherData, weatherViewModel.state.value.weatherData)
    }

    @Test
    fun `getWeather causes a SearchError event when query is invalid`() = runTest {
        val query = "The Sun"
        val weatherData = null

        `when`(getWeatherUseCase.invoke(query)).thenReturn(flowOf(weatherData))

        weatherViewModel.setQuery(query)
        weatherViewModel.setWeather()
        advanceUntilIdle()

        verify(eventFlow).emit(WeatherEvent.SearchError)
        assertNull(weatherData)
    }
}

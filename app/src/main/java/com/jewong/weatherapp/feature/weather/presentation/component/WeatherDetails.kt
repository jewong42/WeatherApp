package com.jewong.weatherapp.feature.weather.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.jewong.weatherapp.feature.weather.presentation.WeatherViewModel
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

@Composable
fun WeatherDetails() {
    val viewModel: WeatherViewModel = hiltViewModel()
    val state = viewModel.state.value

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        when {
            state.isLoading -> {
                CircularProgressIndicator()
            }
            state.weatherData != null -> {
                Text(
                    state.weatherData!!.name,
                    style = MaterialTheme.typography.subtitle1
                )
                WeatherImage(state.weatherData!!.weather.first().icon)
                Text(
                    "${state.weatherData!!.main.feelsLike.roundToInt()}°",
                    style = MaterialTheme.typography.h2
                )
                Text(
                    getCurrentDate(),
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier.padding(16.dp)
                )
            }
            else -> {
                InitialMessage()
            }
        }
    }
}

private fun getCurrentDate(pattern: String = "EEEE dd MMMM yyyy"): String {
    val currentDate = Calendar.getInstance().time
    val dateFormat = SimpleDateFormat(pattern, Locale.getDefault())
    return dateFormat.format(currentDate)
}
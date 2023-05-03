package com.jewong.weatherapp.feature.weather.presentation.component

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun WeatherImage(iconUrl: String) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data("https://openweathermap.org/img/wn/$iconUrl@4x.png")
            .crossfade(300)
            .build(),
        contentDescription = null,
        contentScale = ContentScale.Fit,
        modifier = Modifier.fillMaxSize(0.50f)
    )
}
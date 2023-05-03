package com.jewong.weatherapp.feature.weather.presentation.component

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.jewong.weatherapp.R

@Composable
fun InitialMessage() {
    Text(
        stringResource(R.string.initial_message),
        style = MaterialTheme.typography.h5,
        textAlign = TextAlign.Center,
    )
}
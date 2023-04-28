package com.jewong.weatherapp.feature.weather.presentation.themes

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun WeatherTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val ourColorScheme = if (darkTheme) darkColorScheme else lightColorScheme

    MaterialTheme(
        content = content,
        colors = ourColorScheme
    )
}

private val lightColorScheme = Colors(
    primary = Color(0xffa63b1d),
    primaryVariant = Color(0xffffdbd1),
    secondary = Color(0xff006a67),
    secondaryVariant = Color(0xff6ff7f2),
    background = Color(0xfffffbff),
    surface = Color(0xfffffbff),
    error = Color(0xffba1a1a),
    onPrimary = Color(0xffffffff),
    onSecondary = Color(0xffffffff),
    onBackground = Color(0xff201a19),
    onSurface = Color(0xff201a19),
    onError = Color(0xffffffff),
    isLight = true,
)

private val darkColorScheme = Colors(
    primary = Color(0xffffb5a1),
    primaryVariant = Color(0xff852407),
    secondary = Color(0xff4ddad6),
    secondaryVariant = Color(0xff00504e),
    background = Color(0xff201a19),
    surface = Color(0xff201a19),
    error = Color(0xffffb4ab),
    onPrimary = Color(0xff601300),
    onSecondary = Color(0xff003736),
    onBackground = Color(0xffede0dd),
    onSurface = Color(0xffede0dd),
    onError = Color(0xff690005),
    isLight = false,
)

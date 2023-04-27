package com.jewong.weatherapp.feature.weather.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.jewong.weatherapp.R
import com.jewong.weatherapp.feature.weather.presentation.components.WeatherDetails
import com.jewong.weatherapp.feature.weather.presentation.components.WeatherSearchBar
import com.jewong.weatherapp.feature.weather.presentation.state.WeatherEvent
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun WeatherScreen(
    navController: NavController,
    viewModel: WeatherViewModel = hiltViewModel()
) {
    val eventFlow = viewModel.eventFlow
    val snackbarHostState = remember { SnackbarHostState() }
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val errorMessage = stringResource(R.string.weather_search_error)

    LaunchedEffect(key1 = eventFlow) {
        eventFlow.collectLatest { event ->
            when (event) {
                is WeatherEvent.SearchError -> {
                    snackbarHostState.showSnackbar(message = errorMessage)
                }
                is WeatherEvent.Submitted -> {
                    focusManager.clearFocus()
                    keyboardController?.hide()
                }
            }
        }
    }

    Scaffold { contentPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
                .padding(horizontal = 16.dp, vertical = 32.dp),
        ) {
            Box(modifier = Modifier.weight(1f)) {
                SnackbarHost(
                    modifier = Modifier.align(Alignment.TopStart),
                    hostState = snackbarHostState,
                ) {
                    Snackbar(it)
                }
                WeatherDetails()
            }
            WeatherSearchBar()
        }
    }
}
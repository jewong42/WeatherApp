package com.jewong.weatherapp.feature.weather.presentation.component

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Place
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.text.input.ImeAction
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.jewong.weatherapp.feature.weather.presentation.WeatherViewModel

@OptIn(ExperimentalPermissionsApi::class, ExperimentalComposeUiApi::class)
@Composable
fun WeatherSearchBar() {
    val viewModel: WeatherViewModel = hiltViewModel()
    val state = viewModel.state.value
    val permissionsState = rememberMultiplePermissionsState(
        permissions = listOf(ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION)
    )

    Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.onKeyEvent {
        if (it.key == Key.Enter) {
            viewModel.setWeather()
        }
        true
    }) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = state.query,
            onValueChange = viewModel::setQuery,
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = { viewModel.setWeather() }),
            trailingIcon = {
                Row {
                    IconButton(onClick = {
                        permissionsState.permissions.forEach { permissionState ->
                            if (permissionState.status.isGranted) {
                                viewModel.setCurrentLocationWeather()
                                return@IconButton
                            }
                        }
                        permissionsState.launchMultiplePermissionRequest()
                        viewModel.setCurrentLocationWeather()
                    }) {
                        Icon(
                            imageVector = Icons.Outlined.Place,
                            contentDescription = null
                        )
                    }
                    IconButton(onClick = viewModel::setWeather) {
                        Icon(
                            imageVector = Icons.Outlined.Search,
                            contentDescription = null
                        )
                    }
                }
            }
        )
    }
}

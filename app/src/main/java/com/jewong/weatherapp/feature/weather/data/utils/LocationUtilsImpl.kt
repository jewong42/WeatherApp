package com.jewong.weatherapp.feature.weather.data.utils

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.location.Location
import androidx.core.content.ContextCompat
import com.jewong.weatherapp.feature.weather.domain.utils.LocationUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LocationUtilsImpl(
    private val context: Context
) : LocationUtils {

    private val locationUtils =
        context.getSystemService(Context.LOCATION_SERVICE) as android.location.LocationManager

    @SuppressLint("MissingPermission")
    override suspend fun getLastKnownLocation(): Location? = withContext(Dispatchers.IO) {
        if (hasPermissions()) {
            val providers = locationUtils.getProviders(true)
            var location: Location? = null

            providers.forEach { provider ->
                val lastKnownLocation = locationUtils.getLastKnownLocation(provider)
                if (lastKnownLocation == null) {
                    return@forEach
                } else if (location == null || location!!.accuracy > lastKnownLocation.accuracy) {
                    location = lastKnownLocation
                }
            }

            return@withContext location
        } else {
            return@withContext null
        }
    }

    private fun hasPermissions(): Boolean {
        val finePermission = ContextCompat.checkSelfPermission(context, ACCESS_FINE_LOCATION)
        val coarsePermission = ContextCompat.checkSelfPermission(context, ACCESS_COARSE_LOCATION)
        return finePermission == PERMISSION_GRANTED || coarsePermission == PERMISSION_GRANTED
    }

}
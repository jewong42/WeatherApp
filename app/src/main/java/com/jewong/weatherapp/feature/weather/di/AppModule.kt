package com.jewong.weatherapp.feature.weather.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.jewong.weatherapp.feature.weather.data.network.OpenWeatherApi
import com.jewong.weatherapp.feature.weather.data.repository.WeatherRepositoryImpl
import com.jewong.weatherapp.feature.weather.data.utils.LocationUtilsImpl
import com.jewong.weatherapp.feature.weather.di.utils.ApiKeyInterceptor
import com.jewong.weatherapp.feature.weather.domain.repository.WeatherRepository
import com.jewong.weatherapp.feature.weather.domain.utils.LocationUtils
import com.jewong.weatherapp.feature.weather.shared.Constants.SHARED_PREFERENCES_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideOpenWeatherApi(): OpenWeatherApi {
        return Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/data/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(ApiKeyInterceptor("58418fbb05644eeeb6e0d112ee114085"))
                    .build()
            )
            .build()
            .create(OpenWeatherApi::class.java)
    }

    @Provides
    @Singleton
    fun provideWeatherRepository(api: OpenWeatherApi): WeatherRepository {
        return WeatherRepositoryImpl(api)
    }

    @Provides
    fun provideSharedPreferences(application: Application): SharedPreferences {
        return application.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
    }

    @Provides
    fun provideContext(application: Application): Context {
        return application.applicationContext
    }

    @Provides
    fun provideLocationUtils(context: Context): LocationUtils {
        return LocationUtilsImpl(context)
    }

}

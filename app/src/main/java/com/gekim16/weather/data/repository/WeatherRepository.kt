package com.gekim16.weather.data.repository

import com.gekim16.weather.data.database.WeatherDao
import com.gekim16.weather.data.vo.WeatherResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherRepository @Inject constructor(
    private val weatherDao: WeatherDao,
    private val weatherService: WeatherService
) {
    suspend fun getWeather(lat: Double, lon: Double, today: String): WeatherResponse? {
        return weatherDao.getWeather(lat, lon, today) ?: getWeatherRemoteData(lat, lon, today)
    }

    suspend fun getWeatherRemoteData(lat: Double, lon: Double, today: String): WeatherResponse? {
        return try {
            val apiResult = weatherService.getWeatherData(lat, lon).body()

            if (apiResult != null) {
                weatherDao.insertWeather(apiResult.apply { this.today = today })
            }

            apiResult
        } catch (e: Exception) {
            null
        }
    }
}
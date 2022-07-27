package com.gekim16.weather.data.repository

import com.gekim16.weather.BuildConfig
import com.gekim16.weather.data.vo.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {
    @GET("onecall")
    suspend fun getWeatherData(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") appid: String = BuildConfig.API_KEY
    ): Response<WeatherResponse>
}
package com.gekim16.weather.data.vo

import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity(tableName = "weather", primaryKeys = ["lat", "lon"])
data class WeatherResponse(
    @SerializedName("lat") val lat: Double,
    @SerializedName("lon") val lon: Double,
    @SerializedName("timezone") val timezone: String,
    @SerializedName("timezone_offset") val timezone_offset: Int,
    @SerializedName("hourly") val hourly: List<HourWeather>,
    @SerializedName("daily") val daily: List<DayWeather>,
    @SerializedName("current") val current: HourWeather,
    @SerializedName("today") var today: String = ""
)
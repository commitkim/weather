package com.gekim16.weather.data.database.converter

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.gekim16.weather.data.vo.HourWeather
import com.google.gson.Gson

@ProvidedTypeConverter
class HourWeatherListTypeConverter(private val gson: Gson) {
    @TypeConverter
    fun listToJson(value: List<HourWeather>): String? {
        return gson.toJson(value)
    }
    @TypeConverter
    fun jsonToList(value: String): List<HourWeather> {
        return gson.fromJson(value, Array<HourWeather>::class.java).toList()
    }
}
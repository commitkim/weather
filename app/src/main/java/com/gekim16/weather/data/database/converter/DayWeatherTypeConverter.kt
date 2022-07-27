package com.gekim16.weather.data.database.converter

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.gekim16.weather.data.vo.DayWeather
import com.google.gson.Gson

@ProvidedTypeConverter
class DayWeatherTypeConverter(private val gson: Gson) {
    @TypeConverter
    fun listToJson(value: List<DayWeather>): String? {
        return gson.toJson(value)
    }
    @TypeConverter
    fun jsonToList(value: String): List<DayWeather> {
        return gson.fromJson(value, Array<DayWeather>::class.java).toList()
    }
}
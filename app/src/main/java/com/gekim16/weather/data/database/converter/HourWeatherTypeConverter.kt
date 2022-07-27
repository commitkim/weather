package com.gekim16.weather.data.database.converter

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.gekim16.weather.data.vo.HourWeather
import com.google.gson.Gson

@ProvidedTypeConverter
class HourWeatherTypeConverter(private val gson: Gson) {
    @TypeConverter
    fun listToJson(value: HourWeather): String? {
        return gson.toJson(value)
    }
    @TypeConverter
    fun jsonToList(value: String): HourWeather {
        return gson.fromJson(value, HourWeather::class.java)
    }
}
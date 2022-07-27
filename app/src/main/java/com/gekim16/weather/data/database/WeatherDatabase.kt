package com.gekim16.weather.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.gekim16.weather.data.database.converter.DayWeatherTypeConverter
import com.gekim16.weather.data.database.converter.HourWeatherListTypeConverter
import com.gekim16.weather.data.database.converter.HourWeatherTypeConverter
import com.gekim16.weather.data.vo.WeatherResponse


@Database(entities = [WeatherResponse::class], version = 1, exportSchema = false)
@TypeConverters(
    value = [
        HourWeatherListTypeConverter::class,
        DayWeatherTypeConverter::class,
        HourWeatherTypeConverter::class
    ]
)
abstract class WeatherDatabase: RoomDatabase() {
    abstract  fun getWeatherDAO(): WeatherDao
}
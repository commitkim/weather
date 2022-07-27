package com.gekim16.weather.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gekim16.weather.data.vo.WeatherResponse

@Dao
interface WeatherDao {
    @Query("SELECT * FROM weather WHERE lat=:lat AND lon=:lon AND today=:today")
    fun getWeather(lat: Double, lon: Double, today: String): WeatherResponse?

    @Query("SELECT * FROM weather")
    fun isCached(): WeatherResponse?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWeather(weatherResponse: WeatherResponse)
}
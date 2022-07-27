package com.gekim16.weather.di

import android.content.Context
import androidx.room.Room
import com.gekim16.weather.data.database.WeatherDatabase
import com.gekim16.weather.data.database.WeatherDao
import com.gekim16.weather.data.database.converter.DayWeatherTypeConverter
import com.gekim16.weather.data.database.converter.HourWeatherListTypeConverter
import com.gekim16.weather.data.database.converter.HourWeatherTypeConverter
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideGson(): Gson = Gson()

    @Singleton
    @Provides
    fun provideDataBase(@ApplicationContext context: Context, gson: Gson): WeatherDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            WeatherDatabase::class.java,
            DB_NAME
        ).addTypeConverter(HourWeatherListTypeConverter(gson))
            .addTypeConverter(DayWeatherTypeConverter(gson))
            .addTypeConverter(HourWeatherTypeConverter(gson))
            .build()
    }

    @Provides
    fun providePlantDao(appDatabase: WeatherDatabase): WeatherDao {
        return appDatabase.getWeatherDAO()
    }

    companion object {
        private const val DB_NAME = "database"
    }
}
package com.gekim16.weather.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gekim16.weather.data.repository.WeatherRepository
import com.gekim16.weather.data.vo.WeatherResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository
) : ViewModel() {
    var weatherLiveData = MutableLiveData<WeatherResponse?>()

    fun getWeatherData(lat: Double, lon: Double, today: String, isRefresh: Boolean) =
        viewModelScope.launch(Dispatchers.IO) {
            val result = if (isRefresh) {
                weatherRepository.getWeatherRemoteData(lat, lon, today)
            } else {
                weatherRepository.getWeather(lat, lon, today)
            }

            if (result != null) {
                weatherLiveData.postValue(result)
            }
        }
}
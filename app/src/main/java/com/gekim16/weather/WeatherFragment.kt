package com.gekim16.weather

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.gekim16.weather.adapter.DayWeatherAdapter
import com.gekim16.weather.adapter.HourWeatherAdapter
import com.gekim16.weather.databinding.FragmentWeatherBinding
import com.gekim16.weather.viewmodels.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.round

@AndroidEntryPoint
class WeatherFragment : Fragment() {

    private lateinit var binding: FragmentWeatherBinding
    private val viewModel: WeatherViewModel by viewModels()

    private val dayWeatherAdapter by lazy { DayWeatherAdapter() }
    private val hourWeatherAdapter by lazy { HourWeatherAdapter() }

    private val permissions = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )

    private val requestMultiplePermissions =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) {
            if (it.all { permission -> permission.value }) {
                bindData(false)
            } else {
                showToast("권한 거부")
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWeatherBinding.inflate(inflater, container, false)

        if (checkPermission(permissions)) {
            bindData(false)
        } else {
            requestMultiplePermissions.launch(permissions)
        }

        binding.refresh.setOnRefreshListener {
            bindData(true)
            binding.refresh.isRefreshing = false
        }

        return binding.root
    }

    private fun bindData(isRefresh: Boolean) {
        getLocation()?.let { location ->
            subscribeUI()
            setAddress(location.latitude, location.longitude)
            getWeatherResponse(location.latitude, location.longitude, isRefresh)
        }
    }

    private fun getWeatherResponse(lat: Double, lon: Double, isRefresh: Boolean) {
        viewModel.getWeatherData(
            round(lat * 100) / 100,
            round(lon * 100) / 100,
            SimpleDateFormat("yyyy-MM-dd").format(Date()),
            isRefresh
        )
    }

    private fun subscribeUI() {
        viewModel.weatherLiveData.observe(viewLifecycleOwner) { weatherResponse ->
            weatherResponse?.let {
                val hourWeather = weatherResponse.hourly
                val dayWeather = weatherResponse.daily

                val minTemp = dayWeather.minOf { it.temp.min }
                val maxTemp = dayWeather.maxOf { it.temp.max }

                binding.hourRecyclerView.apply {
                    adapter = hourWeatherAdapter.apply {
                        itemList = hourWeather
                    }
                    layoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                }

                binding.dayRecyclerView.apply {
                    adapter = dayWeatherAdapter.apply {
                        itemList = dayWeather
                        this.minTemp = minTemp
                        this.maxTemp = maxTemp
                    }
                    layoutManager = LinearLayoutManager(context)
                }

                binding.currentWeather = weatherResponse.current
            }
        }
    }

    private fun checkPermission(permissions: Array<String>): Boolean {
        return permissions.all {
            ContextCompat.checkSelfPermission(
                requireContext(),
                it
            ) == PackageManager.PERMISSION_GRANTED
        }
    }

    private fun getLocation(): Location? {
        if (checkPermission(permissions)) {
            val locationManager =
                requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager

            return locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                ?: locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
        }

        return null
    }

    private fun setAddress(lat: Double, lon: Double) {
        val geoCoder = Geocoder(requireContext().applicationContext, Locale.KOREAN)
        var resultList: List<Address>? = null
        try {
            resultList = geoCoder.getFromLocation(
                lat, lon, 1
            )
        } catch (e: IOException) {
            e.printStackTrace()
        }
        if (resultList != null) {
            if (resultList[0].locality.isNullOrEmpty()) {
                binding.currentLocation.text = resultList[0].adminArea
            } else {
                binding.currentLocation.text = resultList[0].locality
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        private const val PERMISSIONS_REQUEST_CODE = 100
    }

}
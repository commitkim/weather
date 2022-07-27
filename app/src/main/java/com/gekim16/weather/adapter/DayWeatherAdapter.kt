package com.gekim16.weather.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gekim16.weather.data.vo.DayWeather
import com.gekim16.weather.databinding.ItemWeatherDayBinding
import kotlin.math.roundToInt

class DayWeatherAdapter : RecyclerView.Adapter<DayWeatherAdapter.DayWeatherViewHolder>() {

    var itemList: List<DayWeather> = emptyList()
    var minTemp: Double = .0
        get() = field - 273.15
    var maxTemp: Double = .0
        get() = field - 273.15

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayWeatherViewHolder {
        val binding =
            ItemWeatherDayBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DayWeatherViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DayWeatherViewHolder, position: Int) {
        holder.bindData(itemList[position])
    }

    override fun getItemCount(): Int = itemList.size

    inner class DayWeatherViewHolder(
        private val binding: ItemWeatherDayBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bindData(dayWeather: DayWeather) {
            binding.dayWeather = dayWeather

            val min = (dayWeather.temp.min - 273.15).roundToInt()
            val max = (dayWeather.temp.max - 273.15).roundToInt()

            val unit =
                binding.dayTempBarBackground.layoutParams.width / (maxTemp.roundToInt() - minTemp.roundToInt())
            binding.dayTempBarColor.layoutParams.width = unit * (max - min)
            (binding.dayTempBarColor.layoutParams as ViewGroup.MarginLayoutParams).marginStart =
                unit * (min - minTemp.roundToInt())
        }
    }
}
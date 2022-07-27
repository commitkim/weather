package com.gekim16.weather.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gekim16.weather.data.vo.HourWeather
import com.gekim16.weather.databinding.ItemWeatherHourBinding

class HourWeatherAdapter : RecyclerView.Adapter<HourWeatherAdapter.HourWeatherViewHolder>() {

    var itemList: List<HourWeather> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourWeatherViewHolder {
        val binding =
            ItemWeatherHourBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HourWeatherViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HourWeatherViewHolder, position: Int) {
        holder.bindData(itemList[position])
    }

    override fun getItemCount(): Int = itemList.size

    class HourWeatherViewHolder(
        private val binding: ItemWeatherHourBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bindData(hourWeather: HourWeather) {
            binding.hourWeather = hourWeather
        }
    }
}
package com.gekim16.weather.adapter

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.gekim16.weather.BuildConfig
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

object BindingAdapter {

    @JvmStatic
    @BindingAdapter("icon")
    fun icon(
        imageView: ImageView,
        icon: String
    ) {
        val url = String.format(BuildConfig.ICON_URL, icon)
        Glide.with(imageView.context)
            .load(url)
            .into(imageView)
    }

    @JvmStatic
    @BindingAdapter("background")
    fun background(
        imageView: ImageView,
        icon: String?
    ) {
        if (icon == null) {
            return
        }

        val url = String.format(BuildConfig.BACKGROUND_URL, icon)
        Glide.with(imageView.context)
            .load(url)
            .into(imageView)
    }

    @JvmStatic
    @BindingAdapter("pop")
    fun pop(
        textView: TextView,
        pop: Double
    ) {
        textView.text = "${(pop * 100).roundToInt()}%"
    }

    @JvmStatic
    @BindingAdapter("temperature")
    fun temperature(
        textView: TextView,
        temp: Double
    ) {

        textView.text = "${(temp - 273.15).roundToInt()}º"
    }

    @JvmStatic
    @BindingAdapter("hour")
    fun hour(
        textView: TextView,
        dt: Int
    ) {
        val timeInDate = Date(dt.toLong() * 1000)
        val timeFormat = SimpleDateFormat("a h시", Locale.KOREA)
        textView.text = timeFormat.format(timeInDate)
    }

    @JvmStatic
    @BindingAdapter("day")
    fun day(
        textView: TextView,
        dt: Int
    ) {
        val timeInDate = Date(dt.toLong() * 1000)
        val timeFormat = SimpleDateFormat("MM/dd (E)", Locale.KOREA)
        textView.text = timeFormat.format(timeInDate)
    }
}
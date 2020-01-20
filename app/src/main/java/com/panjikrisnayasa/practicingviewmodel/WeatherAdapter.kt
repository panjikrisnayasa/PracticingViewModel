package com.panjikrisnayasa.practicingviewmodel

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_weather.view.*

class WeatherAdapter : RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder>() {

    private val mData = ArrayList<Weather>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_weather, parent, false)
        return WeatherViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        holder.bind(mData[position])
    }

    fun setData(items: ArrayList<Weather>) {
        mData.clear()
        mData.addAll(items)
        notifyDataSetChanged()
    }

    inner class WeatherViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(weather: Weather) {
            with(itemView) {
                text_item_weather_city_name.text = String.format(
                    resources.getString(R.string.item_weather_text_city),
                    weather.cityName
                )
                text_item_weather_current_weather.text = String.format(
                    resources.getString(R.string.item_weather_text_current_weather),
                    weather.currentWeather
                )
                text_item_weather_description.text = String.format(
                    resources.getString(R.string.item_weather_text_description),
                    weather.description
                )
                text_item_weather_temperature.text = String.format(
                    resources.getString(R.string.item_weather_text_temperature),
                    weather.temperature
                )
                text_item_weather_humidity.text = String.format(
                    resources.getString(R.string.item_weather_text_humidity),
                    weather.humidity
                )
            }
        }
    }
}
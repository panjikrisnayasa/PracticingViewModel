package com.panjikrisnayasa.practicingviewmodel

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var mWeatherAdapter: WeatherAdapter
    private lateinit var mMainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mWeatherAdapter = WeatherAdapter()
        mWeatherAdapter.notifyDataSetChanged()

        recycler_main_weather.layoutManager = LinearLayoutManager(this)
        recycler_main_weather.adapter = mWeatherAdapter

        mMainViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(MainViewModel::class.java)

        button_main_weather_forecast.setOnClickListener {
            val city = edit_main_city_id.text.toString().trim()
            if (city.isEmpty()) {
                Toast.makeText(this, "Input your city id", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            mMainViewModel.setWeather(city)
            showLoading(true)
        }

        mMainViewModel.getWeather().observe(this, Observer { weather ->
            if (weather.isEmpty()) {
                Log.d("quack", "weather is empty")
                mWeatherAdapter.setData(weather)
                showLoading(false)
                text_main_city_id_not_found.visibility = View.VISIBLE
                return@Observer
            }
            if (weather != null) {
                Log.d("quack", "weather is not null")
                mWeatherAdapter.setData(weather)
                showLoading(false)
                text_main_city_id_not_found.visibility = View.GONE
            }
        })
    }


    private fun showLoading(state: Boolean) {
        if (state) {
            progress_main_recycler_weather.visibility = View.VISIBLE
        } else {
            progress_main_recycler_weather.visibility = View.GONE
        }
    }
}

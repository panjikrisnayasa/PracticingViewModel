package com.panjikrisnayasa.practicingviewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject

class MainViewModel : ViewModel() {
    companion object {
        private const val API_KEY = "59296cca630adece3352f05275758cf5"
    }

    val weatherData = MutableLiveData<ArrayList<Weather>>()

    internal fun setWeather(city: String) {
        val client = AsyncHttpClient()
        val listItem = ArrayList<Weather>()
        val url =
            "https://api.openweathermap.org/data/2.5/group?id=$city&units=metric&appid=$API_KEY"

        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?
            ) {
                try {
                    val result = String(responseBody!!)
                    val responseObject = JSONObject(result)
                    val list = responseObject.getJSONArray("list")

                    for (i in 0 until list.length()) {
                        val listWeather = list.getJSONObject(i)
                        val weather = Weather()
                        weather.cityName = listWeather.getString("name")
                        weather.currentWeather =
                            listWeather.getJSONArray("weather").getJSONObject(0)
                                .getString("main")
                        weather.description =
                            listWeather.getJSONArray("weather").getJSONObject(0)
                                .getString("description")
                        weather.temperature =
                            listWeather.getJSONObject("main").getDouble("temp")
                        weather.humidity = listWeather.getJSONObject("main").getInt("humidity")
                        listItem.add(weather)
                    }
                    weatherData.postValue(listItem)
                } catch (e: Exception) {
                    e.printStackTrace()
                    Log.d("MainViewModelOnSuccess", e.message.toString())
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                error?.printStackTrace()
                weatherData.postValue(listItem)
                Log.d("MainViewModelOnFailure", error?.message.toString())
            }
        })
    }

    internal fun getWeather(): LiveData<ArrayList<Weather>> {
        return weatherData
    }
}
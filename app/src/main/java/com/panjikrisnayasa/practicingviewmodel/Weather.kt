package com.panjikrisnayasa.practicingviewmodel

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Weather(
    var cityName: String? = null,
    var currentWeather: String? = null,
    var description: String? = null,
    var temperature: Double? = null,
    var humidity: Int? = null
) : Parcelable
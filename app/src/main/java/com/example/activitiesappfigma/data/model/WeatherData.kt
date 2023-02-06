package com.example.activitiesappfigma.data.model

import com.squareup.moshi.Json

data class WeatherData (
    @Json(name = "timestamp")
    val timestamp: String,
    @Json(name = "temperature")
    val temp: String,
    @Json(name = "icon")
    val icon: String
)
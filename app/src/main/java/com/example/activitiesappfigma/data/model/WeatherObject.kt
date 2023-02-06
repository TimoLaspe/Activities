package com.example.activitiesappfigma.data.model

import com.squareup.moshi.Json

data class WeatherObject (
    @Json(name = "weather")
    val data: List<WeatherData>
)
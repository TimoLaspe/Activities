package com.example.activitiesappfigma.data.remote

import com.example.activitiesappfigma.data.model.ServerResponse
import com.example.activitiesappfigma.data.model.WeatherObject
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

const val BASE_URL = "https://api.brightsky.dev/"


private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()


interface ApiService {

    @GET("weather")
    suspend fun getWeather(@Query("lat") lat: Double, @Query("lon") lon: Double, @Query("date") date: String): WeatherObject

}

object WeatherApi{
    val retrofitService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}
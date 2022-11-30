package com.example.activitiesappfigma.data.remote

import com.example.activitiesappfigma.data.model.ServerResponse
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

const val BASE_URL = "https://itunes.apple.com/"


private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()


interface SongApiService {
    @GET("search")
    suspend fun getResult(@Query("term") term: String, @Query("media") media: String): ServerResponse
}

object SearchApi {
    val retrofitservice: SongApiService by lazy { retrofit.create(SongApiService::class.java) }
}
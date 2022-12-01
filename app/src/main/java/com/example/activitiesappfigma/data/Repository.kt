package com.example.activitiesappfigma.data

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import com.example.activitiesappfigma.R
import com.example.activitiesappfigma.data.model.Category
import com.example.activitiesappfigma.data.model.Event
import com.example.activitiesappfigma.data.model.WeatherData
import com.example.activitiesappfigma.data.remote.WeatherApi
import com.google.firebase.database.*

class Repository (private val api: WeatherApi) {


    fun loadCategory(): List<Category> {

        return listOf(
            Category(
                R.drawable.category_bike,
                "Fahrrad",
                false
            ),
            Category(
                R.drawable.category_cars,
                "Autos",
                false
            ),
            Category(
                R.drawable.category_animals,
                "Tiere",
                false
            ),
            Category(
                R.drawable.category_fashion,
                "Mode",
                false
            ),
            Category(
                R.drawable.category_food,
                "Essen",
                false
            ),
            Category(
                R.drawable.category_body_and_soul,
                "Körper & Geist",
                false
            ),
            Category(
                R.drawable.category_freetime,
                "Freizeit",
                false
            ),
            Category(
                R.drawable.category_gaming,
                "Gaming",
                false
            ),
            Category(
                R.drawable.category_health,
                "Gesundheit",
                false
            ),
            Category(
                R.drawable.category_hiking,
                "Wandern",
                false
            ),
            Category(
                R.drawable.category_lifestyle,
                "Lifestyle",
                false
            ),
            Category(
                R.drawable.category_literature,
                "Literatur",
                false
            ),
            Category(
                R.drawable.category_music,
                "Musik",
                false
            ),
            Category(
                R.drawable.category_nature,
                "Natur",
                false
            ),
            Category(
                R.drawable.category_sport,
                "Sport",
                false
            ),
            Category(
                R.drawable.category_tech,
                "Technik",
                false
            ),
            Category(
                R.drawable.category_travel,
                "Reisen",
                false
            ),
            Category(
                R.drawable.category_wellness,
                "Wellness",
                false
            )
        )
    }

    suspend fun getWeather(lat: Double, lon: Double, date: String) : List<WeatherData> {
        try {

            val result = api.retrofitService.getWeather(52.0, 7.6, "2022-12-10")
            return result.data

        }catch (e: Exception){
            Log.e(ContentValues.TAG, "Error loading weather from API: $e")
        }
        return listOf()
    }

}
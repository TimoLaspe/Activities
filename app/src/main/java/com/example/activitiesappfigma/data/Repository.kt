package com.example.activitiesappfigma.data

import android.content.ContentValues
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.activitiesappfigma.R
import com.example.activitiesappfigma.data.model.Category
import com.example.activitiesappfigma.data.model.Event
import com.example.activitiesappfigma.data.model.Photo
import com.example.activitiesappfigma.data.model.WeatherData
import com.example.activitiesappfigma.data.remote.WeatherApi
import com.google.firebase.database.*

class Repository(private val api: WeatherApi) {

    private val _events = MutableLiveData<List<Event>>()
    val events: LiveData<List<Event>>
        get() = _events


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

    fun loadPhoto(): List<Photo> {

        return listOf(
            Photo(
                R.drawable.photo_one
            ),
            Photo(
                R.drawable.photo_two
            ),
            Photo(
                R.drawable.photo_three
            ),
            Photo(
                R.drawable.photo_four
            ),
            Photo(
                R.drawable.photo_five
            )
        )
    }


    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getWeatherForEvents(events: List<Event>) {
        val updatedEvents = mutableListOf<Event>()
        for (event in events) {

            val returnWeather = getWeather(0.0, 0.0, event.dateAndTime)

            if (returnWeather.isEmpty()) {
                event.weather = "unknown"
            } else {
                event.weather = returnWeather.first().icon
            }

            updatedEvents.add(event)

        }
        _events.value = updatedEvents
    }

    suspend fun getWeather(lon: Double, lat: Double, date: String): List<WeatherData> {
        try {

            val result = api.retrofitService.getWeather(52.0, 7.6, date)
            return result.data

        } catch (e: Exception) {
            Log.e(ContentValues.TAG, "Error loading weather from API: $e")
        }
        return listOf()
    }

}
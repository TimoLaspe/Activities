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
    suspend fun getWeatherForEvents(events: List<Event>, lon: Double, lat: Double) {
        val updatedEvents = mutableListOf<Event>()
        for (event in events) {

            // TODO: Das richtige Datum des Events verwenden und nicht das Datum hier
            // TODO: Wie das Format aussehen muss siehst du ja hier:
            val date = event.dateAndTime
            val returnWeather = getWeather(lon, lat, date)

            if (returnWeather.isEmpty()) {
                event.weather = "unknown"
            } else {
                // TODO: nimmt aktuell ein Random Element aus der Liste, das muss noch geändert werden
                // TODO: Diese Liste enthält alle stündlichen Wetterdaten für das Datum
                // TODO: Also Item an Stelle 0 ist 00:00 Uhr, Item an Stelle 1 ist 01:00 Uhr usw.
                event.weather = returnWeather[10].icon
                event.temp = returnWeather[10].temp
            }
            updatedEvents.add(event)
        }
        _events.value = updatedEvents
        Log.e("Repository", "updated Events ${updatedEvents[0]}")
    }

    suspend fun getWeather(lon: Double, lat: Double, date: String) : List<WeatherData> {
        try {

            val result = api.retrofitService.getWeather(lat, lon, date)
            return result.data

        }catch (e: Exception){
            Log.e(ContentValues.TAG, "Error loading weather from API: $e")
        }
        return listOf()
    }

}
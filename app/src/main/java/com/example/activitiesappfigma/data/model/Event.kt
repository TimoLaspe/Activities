package com.example.activitiesappfigma.data.model

import android.widget.ImageView
import com.google.firebase.firestore.DocumentId

data class Event (
    @DocumentId
    var id: String = "",
    var image: String = "",
    var name: String = "",
    var info: String = "",
    var dateAndTime: String = "",
    var location: String = "",
    var routine: Boolean = false,
    var category: String = "",
    var weather: String = "",
    var temp: Double = 0.0,
    var weatherIcon: String = ""
//TODO: So wie hier mit weather könntest du es auch noch mit der Temperatur machen
    )
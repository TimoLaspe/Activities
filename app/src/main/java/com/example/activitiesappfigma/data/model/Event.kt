package com.example.activitiesappfigma.data.model

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
    var weather: String = ""
    )
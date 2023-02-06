package com.example.activitiesappfigma.data.model

import com.google.firebase.firestore.DocumentId

data class Profile (
    @DocumentId
    var id: String = "",
    var image: Int = 0,
    var name: String = "",
    var info: String = "",
    var location: String = ""
    )
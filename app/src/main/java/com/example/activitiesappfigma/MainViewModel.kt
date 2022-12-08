package com.example.activitiesappfigma

import android.app.Application
import android.content.ContentValues.TAG
import android.net.Uri
import android.os.Build
import android.provider.CalendarContract
import android.util.Log
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.activitiesappfigma.data.Repository
import com.example.activitiesappfigma.data.model.*
import com.example.activitiesappfigma.data.remote.WeatherApi
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import com.google.firebase.storage.ktx.storageMetadata
import kotlinx.coroutines.launch
import java.net.URI


class MainViewModel(application: Application) : AndroidViewModel(application) {


    // Wetterstandort (Berlin in diesem Fall)
    private var lat: Double = 52.0
    private var lon: Double = 13.0

    private val repository = Repository(WeatherApi)

    private val _photo = MutableLiveData<List<Photo>>()
    val photo: LiveData<List<Photo>>
        get() = _photo

    init {
        _photo.value = repository.loadPhoto()
    }

    // LiveData für Kategorien
    private val _category = MutableLiveData<List<Category>>()
    val category: LiveData<List<Category>>
        get() = _category

    init {
        _category.value = repository.loadCategory()
    }

    // Kommunikationspunkt mit der Firestore Datenbank
    private val db = FirebaseFirestore.getInstance()

    // Kommunikationspunkt mit der FirebaseAuth
    private val firebaseAuth = FirebaseAuth.getInstance()

    // currentuser ist null wenn niemand eingeloggt ist
    private val _currentUser = MutableLiveData<FirebaseUser?>(firebaseAuth.currentUser)
    val currentUser: LiveData<FirebaseUser?>
        get() = _currentUser

    // LiveData für Profil
    private val _profile = MutableLiveData<Profile>()
    val profile: LiveData<Profile>
        get() = _profile

    // LiveData für Events
    private val _event = MutableLiveData<Event>()
    val event: LiveData<Event>
        get() = _event

    val events = repository.events

    private val _toast = MutableLiveData<String?>()
    val toast: LiveData<String?>
        get() = _toast



    // Kommunikationspunkt mit Firebase Storage
    private val storage = FirebaseStorage.getInstance()
    private val storageRef = storage.reference


    // hier wird versucht einen User zu erstellen um diesen anschließend auch gleich
    // einzuloggen
    fun signUp(email: String, password: String, profile: Profile) {
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                    if (it.isSuccessful) {
                        _currentUser.value = firebaseAuth.currentUser
                        setProfile(profile)
                        _toast.value = "Welcome"
                        _toast.value = null
                    } else {
                        Log.e(TAG, "Login failed: ${it.exception}")
                        _toast.value = "Login failed\n${it.exception?.localizedMessage}"
                        _toast.value = null
                    }
                }
            } else {
                Log.e(TAG, "SignUp failed: ${it.exception?.message}")
                _toast.value = "SignUp failed\n${it.exception?.localizedMessage}"
                _toast.value = null
            }
        }
    }

    private fun setProfile(profile: Profile) {
        db.collection("users").document(currentUser.value!!.uid)
            .set(profile)
            .addOnFailureListener {
                Log.w(TAG, "Error writing document: $it")
                _toast.value = "error creating user\n${it.localizedMessage}"
                _toast.value = null
            }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun insertEvent(event: Event, uri: Uri?) {
        db.collection("events").add(event)
            .addOnFailureListener {
                Log.e(TAG, "Error writing document: $it")
                _toast.value = "error creating user\n${it.localizedMessage}"
                _toast.value = null
            }
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    _event.value = event
                    if (uri != null) {
                        uploadImage(uri, it.result.id)
                    }
                }
            }
    }

    fun setEvent(event: Event) {
        _event.value = event
    }

    fun login(email: String, password: String) {
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                _currentUser.value = firebaseAuth.currentUser
                _toast.value = "Welcome"
                _toast.value = null
            } else {
                Log.e(TAG, "Login failed: ${it.exception?.message}")
                _toast.value = it.exception?.message
                _toast.value = null
            }
        }
    }

    fun logout() {
        firebaseAuth.signOut()
        _currentUser.value = firebaseAuth.currentUser
    }


    //Hier werden die Profildaten aus dem Firestore geholt

    fun getProfileData() {
        db.collection("users").document(currentUser.value!!.uid)
            .get().addOnSuccessListener {
                _profile.value = it.toObject(Profile::class.java)
            }
            .addOnFailureListener {
                Log.e(TAG, "Error reading document: $it")
            }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun uploadImage(uri: Uri, id: String) {
        val imageRef = storageRef.child("images/${id}/eventspic")
        val uploadTask = imageRef.putFile(uri)

        uploadTask.addOnFailureListener {
            Log.e("MainViewModel", "upload failed: $it")
        }

        uploadTask.addOnSuccessListener {
            Log.e("MainViewModel", "upload worked")
        }

        uploadTask.addOnCompleteListener {
            imageRef.downloadUrl.addOnCompleteListener {
                if (it.isSuccessful) {
                    setImage(it.result, id)
                }
            }
        }
    }

    //Hier wird das Bild zum Event in den Firestore gesetzt

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setImage(uri: Uri, id: String) {
        db.collection("events").document(id)
            .update("image", uri.toString())
            .addOnFailureListener {
                Log.w(TAG, "Error writing document: $it")
            }
            .addOnCompleteListener {
                getEventDataWithWeater(lat, lon)
            }
    }

    // Hier werden die Eventdaten + Wetter geholt

    @RequiresApi(Build.VERSION_CODES.O)
    fun getEventDataWithWeater(lon: Double, lat: Double) {
        db.collection("events")
            .get()
            .addOnSuccessListener { result ->
                val out = mutableListOf<Event>()
                for (document in result) {
                    Log.d(TAG, "${document.id} => ${document.data}")
                    out.add(document.toObject(Event::class.java))
                }
                viewModelScope.launch {
                    repository.getWeatherForEvents(out, lon, lat)
                }


            }
    }
}
package com.example.activitiesappfigma

import android.app.Application
import android.content.ContentValues.TAG
import android.media.Image
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.activitiesappfigma.data.Repository
import com.example.activitiesappfigma.data.model.Category
import com.example.activitiesappfigma.data.model.Event
import com.example.activitiesappfigma.data.model.Profile
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore


class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = Repository()

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

    private val _toast = MutableLiveData<String?>()
    val toast: LiveData<String?>
        get() = _toast

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

    private fun setEvent(event: Event) {
        db.collection("events").document(currentUser.value!!.uid)
            .set(event)
            .addOnFailureListener {
                Log.e(TAG,"Error writing document: $it")
                _toast.value = "error creating user\n${it.localizedMessage}"
                _toast.value = null
            }
    }

    fun startEvent(image: Int, name: String, info: String, dateTime: String, routine: Boolean, event: Event) {
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

    fun getProfileData() {
        db.collection("users").document(currentUser.value!!.uid)
            .get().addOnSuccessListener {
                _profile.value = it.toObject(Profile::class.java)
            }
            .addOnFailureListener {
                Log.e(TAG, "Error reading document: $it")
            }
    }

}
package com.example.activitiesappfigma

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.activitiesappfigma.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bottomNav: BottomNavigationView = binding.bottomNavigation
        navController = findNavController(R.id.fragmentContainerView)

        bottomNav.setupWithNavController(navController)
    }

    fun hideBottomNav() {
        binding.bottomNavigation.visibility = View.GONE
    }

    fun showBottomNav() {
        binding.bottomNavigation.visibility = View.VISIBLE
    }
}
package com.example.activitiesappfigma.ui

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.activitiesappfigma.MainActivity
import com.example.activitiesappfigma.MainViewModel
import com.example.activitiesappfigma.R
import com.example.activitiesappfigma.adapter.EventAdapter
import com.example.activitiesappfigma.data.model.Event
import com.example.activitiesappfigma.databinding.FragmentEventlistBinding
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage


class EventListFragment: Fragment(){

    lateinit var binding: FragmentEventlistBinding
    private val viewModel: MainViewModel by activityViewModels()

    // Koordinaten für Wetter API (Berlin)
    private var lat: Double = 52.0
    private var lon: Double = 13.0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEventlistBinding.inflate(inflater)
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity?.lifecycleScope?.launchWhenCreated {
            (activity as MainActivity).showBottomNav()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val eventAdapter = EventAdapter(viewModel)

        binding.eventRecycler.adapter = eventAdapter

        // Events mit Wetterdaten holen
        viewModel.getEventDataWithWeater(lon, lat)

        viewModel.events.observe(
            viewLifecycleOwner,
            Observer {
                eventAdapter.submitList(it)
            Log.e("EventListFragment", "Observer got the Data")
            }
        )

        viewModel.currentUser.observe(
            viewLifecycleOwner,
            Observer {
                if (it == null) {
                    findNavController().navigate(R.id.loginFragment)
                }
            }
        )

    }
}
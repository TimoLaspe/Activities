package com.example.activitiesappfigma.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.activitiesappfigma.MainViewModel
import com.example.activitiesappfigma.databinding.FragmentEventeditBinding

class EventEditFragment: Fragment(){

    lateinit var binding: FragmentEventeditBinding
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEventeditBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var eventEditName = binding.eventeditTextInputName.text.toString()
        var eventEditInfo = binding.eventeditTextInputInfo.text.toString()
        var eventEditDateTime = binding.eventeditInputDateTime.text.toString()
        var eventEditLocation = binding.eventeditTextInputLocation.text.toString()
        var eventEditCategory = binding.categorySpinner.selectedItem.toString()
        var eventEditRoutine = binding.eventeditRoutineRadioBtn


    }

}
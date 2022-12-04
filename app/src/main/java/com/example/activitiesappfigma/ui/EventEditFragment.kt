package com.example.activitiesappfigma.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.activitiesappfigma.MainActivity
import com.example.activitiesappfigma.MainViewModel
import com.example.activitiesappfigma.R
import com.example.activitiesappfigma.data.model.Event
import com.example.activitiesappfigma.databinding.FragmentEventeditBinding

class EventEditFragment : Fragment() {

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

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity?.lifecycleScope?.launchWhenCreated {
            (activity as MainActivity).showBottomNav()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.eventeditImage.setImageResource(R.drawable.app_logo)

        binding.categorySpinner.setOnTouchListener { view, motionEvent ->
            val imm: InputMethodManager =
                requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }

        binding.btnCalendar.setOnClickListener {
            // create new instance of DatePickerFragment
            val datePickerFragment = DatePicker()
            val supportFragmentManager = requireActivity().supportFragmentManager

            // we have to implement setFragmentResultListener
            supportFragmentManager.setFragmentResultListener(
                "REQUEST_KEY",
                viewLifecycleOwner
            ) { resultKey, bundle ->
                if (resultKey == "REQUEST_KEY") {
                    val date = bundle.getString("SELECTED_DATE")
                    binding.dateTextTv.text = date
                }
            }

            datePickerFragment.show(supportFragmentManager, "DatePickerFragment")
        }

        binding.eventeditStartEventButton.setOnClickListener {
            startEvent()
            binding.eventeditTextInputName.text = null
            binding.eventeditTextInputInfo.text = null
            binding.dateTextTv.text = null
            binding.eventeditTextInputLocation.text = null
            findNavController().navigate(R.id.eventListFragment)
        }

    }


    private fun startEvent() {
        val eventEditName = binding.eventeditTextInputName.text.toString()
        val eventEditInfo = binding.eventeditTextInputInfo.text.toString()
        val eventEditDateTime = binding.dateTextTv.text.toString()
        val eventEditLocation = binding.eventeditTextInputLocation.text.toString()
        val eventEditCategory = binding.categorySpinner.selectedItem.toString()
        val eventEditRoutine = binding.routineSwitchButton

        var isRoutine = false

        eventEditRoutine.setOnCheckedChangeListener { compoundButton, onswitch ->
            if (onswitch) isRoutine = true
            else isRoutine = false
        }


        if (!eventEditName.isNullOrEmpty() && !eventEditInfo.isNullOrEmpty() && !eventEditDateTime
                .isNullOrEmpty() && !eventEditLocation.isNullOrEmpty()
        ) {
            val newEvent = Event(
                name = eventEditName,
                info = eventEditInfo,
                dateAndTime = eventEditDateTime,
                location = eventEditLocation,
                category = eventEditCategory,
                routine = isRoutine
            )
            viewModel.insertEvent(newEvent)
        }
    }
}